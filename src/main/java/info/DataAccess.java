package info;

import io.datafx.controller.injection.scopes.ApplicationScoped;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@ApplicationScoped
public class DataAccess {

    public Query createNativeSelect(String sql, Class clazz) {
        return entityManager.createNativeQuery(sql, clazz);
    }

    public Query createNativeUpdate(String sql) {
        return entityManager.createNativeQuery(sql);
    }

    public EntityTransaction getTransaction() {
        return entityManager.getTransaction();
    }

    public <T> List<T> select(Class<T> clazz) {
        String entityName = tableNameFor(clazz);
        return select(entityName, clazz, "");
    }

    public <T> List<T> select(Class<T> clazz, String where) {
        String entityName = tableNameFor(clazz);
        return select(entityName, clazz, where);
    }

    /***
     * <code>select</code> builds, executes and a SELECT query
     *
     * @param table     Table or view name
     * @param clazz     Class of the return object
     * @param condition Where condition for query
     * @return List of type T with selected the tuples
     */
    public <T> List<T> select(String table, Class<T> clazz, String condition) {
        StringBuilder sb = new StringBuilder();

        // start with "SELECT * FROM $table$"
        sb.append("SELECT * FROM ").append(table);

        // if we have a WHERE condition, append it last
        if (condition != null && !condition.isEmpty()) {
            sb.append(" WHERE ").append(condition);
        }

        // The generated SQL should be of type:
        // SELECT FROM $table$ <WHERE $cond$>
        String sql = sb.toString();
        Query query = createNativeSelect(sql, clazz);

        // Run query and build result objects
        @SuppressWarnings({"unchecked"})
        List<T> list = query.getResultList();
        return list;
    }

    /**
     * <code>makeDelete</code> builds and executes a DELETE query.
     * The WHERE condition is built to match the primary key of
     * the given object, so only this one is changed.
     *
     * @param entity The object to delete
     * @param table  Table or view name
     * @param clazz  Class of the object to delete
     * @return Object to execute the delete query
     */
    public <T> Query makeDelete(T entity, String table, Class<T> clazz) {
        StringBuilder sb = new StringBuilder();

        // Start with "DELETE FROM $table$"
        sb.append("DELETE FROM ").append(table);

        // Find the primary key columns and their values
        List<Entry<String, Object>> columns = whereForPK(entity, clazz);
        if (columns.isEmpty()) {
            // We wont delete all tuples if no condition was found,
            // just return null
            return null;
        }

        buildWhere(sb, columns);

        // The generated SQL should be of type:
        // DELETE FROM $table$ WHERE $pk1$ = ? <AND $pk2$ = ?>
        String sql = sb.toString();
        Query query = createNativeUpdate(sql);

        // Set the parameters values for the where clauses
        int param = 1;
        for (Entry<String, Object> entry : columns) {
            query.setParameter(param, entry.getValue());
            param++;
        }

        // return the built query
        return query;
    }

    /**
     * <code>makeUpdate</code> builds and executes a UPDATE query.
     * The WHERE condition is built to match the primary key of
     * the given object, so only this one is changed.
     *
     * @param entity The object to update
     * @param table  Table or view name
     * @param clazz  Class of the object to update
     * @return Object to execute the update query
     */
    private <T> Query makeUpdate(T entity, String table, Class<T> clazz) {
        StringBuilder sb = new StringBuilder();

        // Start with "UPDATE $table$"
        sb.append("UPDATE ").append(table);

        // Find the primary key columns and their values
        List<Entry<String, Object>> whereColumns = whereForPK(entity, clazz);

        // Find all columns and their values to change
        List<Entry<String, Object>> dataColumns =
                // NOTE: eclipselink doesn't support update on primary keys :(
                // we have to filter them from the changed data
                getFields(entity, clazz, field -> field.getAnnotation(Id.class) == null);

        if (whereColumns.isEmpty() || dataColumns.isEmpty()) {
            // We wont update all tuples if no condition was found,
            // just return null
            return null;
        }

        // Build the SET $column1$ = ? <, $column2$ = ?>
        boolean firstColumn = true;
        for (Entry<String, Object> column : dataColumns) {
            if (firstColumn) {
                sb.append(" SET ");
                firstColumn = false;
            } else {
                sb.append(", ");
            }
            sb.append(column.getKey())
                    .append(" = ?");
        }

        // Build the where condition
        buildWhere(sb, whereColumns);

        // The built sql should be of type:
        // "UPDATE $table$
        // SET $column1$=? <,$column2$=?>
        // WHERE $pk1$= ? <AND $pk$=?>"
        String sql = sb.toString();
        Query query = createNativeUpdate(sql);

        // Set values for the "SET col=?" parameters
        int param = 1;
        for (Entry<String, Object> dataColumn : dataColumns) {
            query.setParameter(param, dataColumn.getValue());
            param++;
        }

        // Set values for the "WHERE col=?" parameters
        for (Entry<String, Object> whereColumn : whereColumns) {
            query.setParameter(param, whereColumn.getValue());
            param++;
        }

        return query;
    }

    private <T> Query makeInsert(T entity, String table, Class<T> clazz) {
        StringBuilder sb = new StringBuilder();

        // Start with "INSERT INTO $table"
        sb.append("INSERT INTO ").append(table);

        // Find all columns and their values to insert
        List<Entry<String, Object>> columns = getFields(entity, clazz,
                // If the column is auto-generated, we shouldn't pass it explicit data
                field -> field.getAnnotation(GeneratedValue.class) == null);
        if (columns.isEmpty()) {
            // If we got no column, there's nothing to insert
            return null;
        }

        // Build the "($column1$ <,$column2$>) VALUES"
        boolean firstColumn = true;
        for (Entry<String, Object> column : columns) {
            if (firstColumn) {
                sb.append(" (");
                firstColumn = false;
            } else {
                sb.append(", ");
            }
            sb.append(column.getKey());
        }
        sb.append(") VALUES ");

        // Build the "(? <, ?>)"
        for (int i = 0; i < columns.size(); i++) {
            if (i == 0) {
                sb.append("(?");
            } else {
                sb.append(", ?");
            }
        }
        sb.append(")");

        // The built sql should be of type:
        // "INSERT INTO $table$($column1$ <, $column2$>)
        // VALUES (? <, ?>)"
        String sql = sb.toString();
        Query query = createNativeUpdate(sql);

        // Set values for the "(? <, ?>)" parameters
        for (int i = 0; i < columns.size(); i++) {
            query.setParameter(i + 1, columns.get(i).getValue());
        }

        return query;
    }

    public <T> Query makeDelete(T entity, Class<T> clazz) {
        return makeDelete(entity, tableNameFor(clazz), clazz);
    }

    public <T> Query makeUpdate(T entity, Class<T> clazz) {
        return makeUpdate(entity, tableNameFor(clazz), clazz);
    }

    public <T> Query makeInsert(T entity, Class<T> clazz) {
        return makeInsert(entity, tableNameFor(clazz), clazz);
    }

    private <T> List<Entry<String, Object>> getFields(T entity, Class<T> clazz) {
        return getFields(entity, clazz, field -> true);
    }

    public <T> List<Entry<String, Object>> getFields(T entity, Class<T> clazz, Predicate<? super Field> predicate) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(predicate)
                .map(field -> {
                    try {
                        return new AbstractMap.SimpleEntry<>(
                                fieldNameFor(field),
                                field.get(entity));
                    } catch (IllegalAccessException e) {
                        return null;
                    }
                })
                .filter(entry -> entry != null)
                .collect(Collectors.toList());
    }

    public <T> List<Entry<String, Object>> whereForPK(T entity, Class<T> clazz) {
        return getFields(entity, clazz, field -> field.getAnnotation(Id.class) != null);
    }

    /**
     * Build where condition for "column = value" case
     */
    private void buildWhere(StringBuilder sb, List<Entry<String, Object>> columns) {
        boolean firstClause = true;
        for (Entry<String, Object> entry : columns) {
            if (firstClause) {
                sb.append(" WHERE ");
                firstClause = false;
            } else {
                sb.append(" AND ");
            }

            sb.append(entry.getKey());
            sb.append(" = ?");
        }
    }

    private String fieldNameFor(Field field) {
        Column column = field.getAnnotation(Column.class);
        return column != null && !column.name().isEmpty() ?
                column.name() : field.getName();
    }

    public <T> String tableNameFor(Class<T> clazz) {
        Table annotation = clazz.getAnnotation(Table.class);
        return annotation != null ? annotation.name() : clazz.getSimpleName();
    }

    public static final String ORACLE_PERSISTENCE_UNIT_NAME = "para-info-oracle";
    public static final String POSTGRES_PERSISTENCE_UNIT_NAME = "para-info-postgres";

    public DataAccess() {
        init();
    }

    private EntityManager entityManager;

    @PostConstruct
    public void init() {
        if (entityManager == null) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory(POSTGRES_PERSISTENCE_UNIT_NAME);
            entityManager = emf.createEntityManager();
        }
    }

//    // Commented out so we're forced to use native queries
//    // with the other methods this class provides
//    public EntityManager getEntityManager() {
//        return entityManager;
//    }

}
