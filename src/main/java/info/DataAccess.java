package info;

import io.datafx.controller.injection.scopes.ApplicationScoped;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

@ApplicationScoped
public class DataAccess {

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

    public <T> boolean delete(T entity, Class<T> clazz) throws IllegalAccessException {
        return delete(entity, tableNameFor(clazz), clazz);
    }

    /**
     * Builds and executes a DELETE query.
     * The WHERE condition is built to match the primary key, so only this object is changed.
     */
    public <T> boolean delete(T entity, String table, Class<T> clazz) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ").append(table);

        // Find the primary key columns and their values
        Set<Entry<String, Object>> entries = conditionFor(entity, clazz);
        if (entries.isEmpty()) {
            // We wont delete all tuples if no condition was found, just fail
            return false;
        }

        boolean firstClause = true;
        for (Entry<String, Object> entry : entries) {
            if (firstClause) {
                sb.append(" WHERE ");
                firstClause = false;
            } else {
                sb.append(" AND ");
            }

            sb.append(entry.getKey());
            sb.append(" = ?");
        }

        // The generated SQL should be of type:
        // DELETE FROM table WHERE pk1 = ? AND pk2 = ?
        String sql = sb.toString();
        Query query = createNativeUpdate(sql);

        // Set the parameters values for query
        int counter = 1;
        for (Entry<String, Object> entry : entries) {
            query.setParameter(counter, entry.getValue());
            counter++;
        }

        // execute delete query on transaction
        getTransaction().begin();
        int updated = query.executeUpdate();
        getTransaction().commit();

        // returns true if we modified any row
        return updated > 0;
    }

    public <T> Set<Entry<String, Object>> conditionFor(T entity, Class<T> clazz) throws IllegalAccessException {
        Map<String, Object> primaries = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getAnnotation(Id.class) != null) {
                Column column = field.getAnnotation(Column.class);
                String fieldName = column != null && !column.name().isEmpty() ?
                        column.name() : field.getName();

                primaries.put(fieldName, field.get(entity));
            }
        }

        return primaries.entrySet();
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

    public Query createNativeSelect(String sql, Class clazz) {
        return entityManager.createNativeQuery(sql, clazz);
    }

    public Query createNativeUpdate(String sql) {
        return entityManager.createNativeQuery(sql);
    }

    public EntityTransaction getTransaction() {
        return entityManager.getTransaction();
    }

//    // Commented out so we're forced to use native queries
//    // with the other methods this class provides
//    public EntityManager getEntityManager() {
//        return entityManager;
//    }

}
