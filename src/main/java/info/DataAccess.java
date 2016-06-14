package info;

import io.datafx.controller.injection.scopes.ApplicationScoped;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import java.util.List;

@ApplicationScoped
public class DataAccess {

    public <T> List<T> selectAll(Class<T> clazz) {
        return selectAll(clazz.getAnnotation(Table.class).name(), clazz);
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

    public javax.persistence.EntityManager getEntityManager() {
        return entityManager;
    }

}
