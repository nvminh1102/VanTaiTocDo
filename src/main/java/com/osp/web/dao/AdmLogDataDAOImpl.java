package com.osp.web.dao;

import com.osp.model.AdmLogData;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(value = "transactionManager")
public class AdmLogDataDAOImpl implements AdmLogDataDAO {

    @PersistenceContext(unitName = "appAdmin")
    @Qualifier(value = "transactionManager")
    private EntityManager entityManager;

    @Override
    public AdmLogData add(AdmLogData admLogData) {
        try {
            entityManager.persist(admLogData);
            entityManager.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return admLogData;
    }

}
