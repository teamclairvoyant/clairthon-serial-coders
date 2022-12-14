package com.cv.sc.storage.impl;

import com.cv.sc.storage.EntityManagerProvider;
import com.cv.sc.storage.StorageService;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.List;

@Slf4j
public class DBStorageServiceImpl implements StorageService {

    private final EntityManager entityManager;

    private static DBStorageServiceImpl instance;

    public static DBStorageServiceImpl getInstance() {
        if(instance == null) {
            instance = new DBStorageServiceImpl();
        }
        return instance;
    }

    private DBStorageServiceImpl() {
         entityManager = EntityManagerProvider.getInstance().getEntityManager();
    }

    public Object save(Object searchParent) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(searchParent);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }

        return searchParent;
    }

    @Override
    public Object fetch(Class entityClass, Object id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    public Object delete(Class entityClass, Object pk) throws IOException {
        return null;
    }

    @Override
    public Object update(Object value) throws IOException {

        try {
            entityManager.getTransaction().begin();
            value = entityManager.merge(value);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
        return value;
    }

    public List findAll(Class entityClass) {
        return entityManager.createQuery("Select t from " + entityClass.getSimpleName() + " t").getResultList();
    }

    public List getScheduledConfig () {
        return entityManager.createQuery("Select c from Config c where isScheduled = true ").getResultList();
    }

    @Override
    public List fetchWithPredicate(Class entityClass, String paramName, String value){
        return entityManager.createQuery("Select t from " + entityClass.getSimpleName() + " t where "+paramName +"="+value).getResultList();
    }
}
