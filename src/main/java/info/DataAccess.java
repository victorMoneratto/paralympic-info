package info;

import io.datafx.controller.injection.scopes.ApplicationScoped;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import java.util.List;

@ApplicationScoped
public class DataAccess {

    public <T> List<T> selectAll(Class<T> clazz) {
        String entityName = tableNameFor(clazz);
        return selectAll(entityName, clazz);
    }

    public <T> List<T> selectAll(String table, Class<T> clazz)  {
        Query query = entityManager.createNativeQuery("SELECT * FROM " + table, clazz);
        // Oh boy, if we're selecting from the table which is mapped to the class, the result must be
        // a list with objects from said class or just empty (or null), than it should be safe to do
        // the unchecked cast to the generic list.
        @SuppressWarnings({"unchecked"})
        List<T> list = query.getResultList();
        return list;
    }

    public <T> String tableNameFor(Class<T> clazz) {
        Table annotation = clazz.getAnnotation(Table.class);
        return annotation != null? annotation.name() : clazz.getSimpleName();
    }

    public static final String PARA_INFO_PERSISTENCE_UNIT_NAME = "para-info";

    public DataAccess() {
        init();
    }

    @PostConstruct
    public void init(){
        if (entityManager == null) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory(PARA_INFO_PERSISTENCE_UNIT_NAME);
            entityManager = emf.createEntityManager();
        }
    }

    private EntityManager entityManager;

    public Query createNativeQuery(String sql, Class clazz) {
        return entityManager.createNativeQuery(sql, clazz);
    }

//    // Commented out so we're sort of forced to use native queries with the method above
//    public EntityManager getEntityManager() {
//        return entityManager;
//    }

}
