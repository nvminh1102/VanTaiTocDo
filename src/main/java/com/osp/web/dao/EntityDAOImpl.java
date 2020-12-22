/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osp.web.dao;


import com.osp.common.PagingResult;
import com.osp.model.Group;
import java.util.List;
import java.util.Optional;
import static javafx.scene.input.KeyCode.T;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author admin
 */
@Repository
@Transactional(value = "transactionManager")
public class EntityDAOImpl{
    @PersistenceContext(unitName = "appAdmin")
    @Qualifier(value = "transactionManager")
    private EntityManager entityManager;
    private Logger logger = LogManager.getLogger(EntityDAOImpl.class);
    //thêm mới cho toàn bộ đối tượng
    public <T> void save(final T o){
       entityManager.persist(o);
    }
    // view cho toàn bộ đối tượng
//    @Transactional(value = "transactionManager")
//    public <T> List<T> getAll(Class<T> clazz,int page,int maxResult){
//    List<T> lts= (List<T>) entityManager.createQuery("SELECT t from "+clazz.getName()+" t").setFirstResult(page!=0?page:0).setMaxResults(maxResult!=0?maxResult:10).getResultList();
//    return lts;
//    
//    }
     public <T>  Optional<PagingResult> getAll(Class<T> clazz, PagingResult page) {
        int offset = 0;
        if (page.getPageNumber() > 0) {
            offset = (page.getPageNumber() - 1) * page.getNumberPerPage();
        }
        Long count = (Long) entityManager.createQuery("SELECT count(t) from "+clazz.getName()+" t").getSingleResult();
        List<T> lts= (List<T>) entityManager.createQuery("SELECT t from "+clazz.getName()+" t").setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
        if (lts != null && count > 0) {
            page.setItems(lts);
            page.setRowCount(count.longValue());
        }
        return Optional.of(page);
    }
    //tìm kiếm theo id cho từng đối tượng
    public <T> T  findById(Class<T> type,long id){
    
    return entityManager.find(type,id);
    
    }
    //remove đối tượng
    public <T> void remove(Class<T> clazz,long id){
     Query query = entityManager.createQuery("delete from "+clazz.getName()+" t where t.id=:id").setParameter("id", id);
     query.executeUpdate();
    }
    //update một đối tượng
    public <T> void update(final T o){
    entityManager.merge(o);
    }
    public <T> void changeStatus(Class<T> clazz,long id,int status){
    Query query = entityManager.createQuery("update  "+clazz.getName()+" t set t.status=:status where t.id=:id").setParameter("id", id).setParameter("status",status);
     query.executeUpdate();
    }
}
