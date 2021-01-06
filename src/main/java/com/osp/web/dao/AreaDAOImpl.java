package com.osp.web.dao;

import com.osp.model.VtArea;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(value = "transactionManager")
public class AreaDAOImpl implements AreaDAO {

    @PersistenceContext(unitName = "appAdmin")
    @Qualifier(value = "transactionManager")
    private EntityManager entityManager;

    @Override
    public List<VtArea> getAllArea() {
        List<VtArea> lst = new ArrayList<>();
        try {
            Query query = entityManager.createQuery("SELECT a from VtArea a");
            lst = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lst;
    }

    @Override
    public VtArea getVtAreaById(Integer id) {
        try {
            Query query = entityManager.createQuery("SELECT a from VtArea a where id = :id ").setParameter("id", id);;
            List<Object> objects = query.getResultList();
            if (objects != null && objects.size() > 0) {
                return (VtArea) objects.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
