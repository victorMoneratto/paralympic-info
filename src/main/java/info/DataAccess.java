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
        return select(entityName, clazz);
    }

    /***
     * Selects all tuples from table
     */
    public <T> List<T> select(String table, Class<T> clazz) {
        Query query = createNativeSelect("SELECT * FROM " + table, clazz);

        // Since we're selecting from a single table, it should be safe to
        // cast the resulting list to the generic form.
        @SuppressWarnings({"unchecked"})
        List<T> list = query.getResultList();
        return list;
    }

    /**
     * Builds and executes a DELETE query.
     * The WHERE condition is built to match the primary key, so only this object is changed.
     */
    public <T> Query makeDelete(T entity, String table, Class<T> clazz) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ").append(table);

        // Find the primary key columns and their values
        List<Entry<String, Object>> columns = whereForPK(entity, clazz);
        if (columns.isEmpty()) {
            // We wont makeDelete all tuples if no condition was found, just fail
            return null;
        }

        buildWhere(sb, columns);

        // The generated SQL should be of type:
        // DELETE FROM table WHERE pk1 = ? AND pk2 = ?
        String sql = sb.toString();
        Query query = createNativeUpdate(sql);

        // Set the parameters values for query
        int counter = 1;
        for (Entry<String, Object> entry : columns) {
            query.setParameter(counter, entry.getValue());
            counter++;
        }

        return query;
    }

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

    private <T> Query makeUpdate(T entity, String table, Class<T> clazz) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ").append(table);

        List<Entry<String, Object>> whereColumns = whereForPK(entity, clazz);

        // eclipselink doesn't support update on primary keys, filter them for now
        List<Entry<String, Object>> dataColumns =
                getFields(entity, clazz, field -> field.getAnnotation(Id.class) == null);

        if (whereColumns.isEmpty() || dataColumns.isEmpty()) {
            return null;
        }

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

        buildWhere(sb, whereColumns);

        String sql = sb.toString();
        Query query = createNativeUpdate(sql);
        int param = 1;
        for (Entry<String, Object> dataColumn : dataColumns) {
            query.setParameter(param, dataColumn.getValue());
            param++;
        }
        for (Entry<String, Object> whereColumn : whereColumns) {
            query.setParameter(param, whereColumn.getValue());
            param++;
        }

        return query;
    }

    private <T> Query makeInsert(T entity, String table, Class<T> clazz) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ").append(table);

        List<Entry<String, Object>> columns = getFields(entity, clazz);
        if (columns.isEmpty()) {
            // If we got no column, there's nothing to makeInsert
            return null;
        }

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

        for (int i = 0; i < columns.size(); i++) {
            if (i == 0) {
                sb.append("(?");
            } else {
                sb.append(", ?");
            }
        }
        sb.append(")");

        // build makeInsert query
        String sql = sb.toString();
        Query query = createNativeUpdate(sql);

        // set value for column parameters
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
