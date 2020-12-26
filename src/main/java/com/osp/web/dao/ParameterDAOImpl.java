/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osp.web.dao;

import com.osp.common.PagingResult;
import com.osp.common.QueryBuilder;
import com.osp.model.Parameter;
import com.osp.model.VtPartner;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Vu Van Lich
 */
@Repository
@Transactional(value = "transactionManager")
public class ParameterDAOImpl implements ParameterDAO {

    @PersistenceContext(unitName = "appAdmin")
    @Qualifier(value = "transactionManager")
    private EntityManager entityManager;

    @Override
    public Optional<PagingResult> page(PagingResult page, String paramKey) {
        int offset = 0;
        if (page.getPageNumber() > 0) {
            offset = (page.getPageNumber() - 1) * page.getNumberPerPage();
        }

        StringBuffer sqlBuffer = new StringBuffer("SELECT p.id,p.key,p.value,p.description,p.genDate,p.lastUpdate,to_char(p.genDate,'DD/MM/YYYY hh24:mi:ss') "
                + "from Parameter p ");
        StringBuffer sqlBufferCount = new StringBuffer("SELECT count(p.id) "
                + "from Parameter p ");
        Query query = filterBuilderSingle(sqlBuffer, true, paramKey);
        List<Object[]> list = (page.getPageNumber() == 0) ? query.getResultList() : query.setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
        if (list != null && list.size() > 0) {
            page.setItems(list);
        }
        Query queryCount = filterBuilderSingle(sqlBufferCount, false, paramKey);
        Long rowCount = (Long) queryCount.getSingleResult();
        if (rowCount != null) {
            page.setRowCount(rowCount.longValue());
        }
        return Optional.ofNullable(page);
    }

    private Query filterBuilderSingle(StringBuffer stringBuffer, boolean order, String paramKey) {
        Query result = null;
        try {
            QueryBuilder builder = new QueryBuilder(entityManager, stringBuffer);
            if (StringUtils.isNotBlank(paramKey)) {
                builder.and(QueryBuilder.LIKE, "p.key", "%" + paramKey + "%");
            }
            builder.addOrder("p.genDate", QueryBuilder.DESC);
            result = builder.initQuery(order);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean addParam(Parameter paramItem) {
        try {
            entityManager.persist(paramItem);
            entityManager.flush();
            return true;
        } catch (Exception e) {
        }

        return false;
    }

    @Override
    public boolean isExits(Parameter paramItem) {
        boolean result = true;
        try {
            StringBuffer sqlBuffer = new StringBuffer("SELECT Count(o) FROM Parameter o");
            QueryBuilder builder = new QueryBuilder(entityManager, sqlBuffer);
            builder.and(QueryBuilder.EQ, "UPPER(o.key)", paramItem.getKey().trim().toUpperCase());
            Query query = builder.initQuery(false);
            List list = query.getResultList();
            int count = ((Long) list.get(0)).intValue();
            if (count == 0) {
                result = false;
            }

        } catch (Exception e) {
        }
        return result;
    }

    @Override
    public boolean editParam(Parameter paramItem) {
        try {
            entityManager.merge(paramItem);
            entityManager.flush();
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    @Override
    public Parameter getParamById(Long id) {
        Parameter result = null;
        try {
            result = entityManager.find(Parameter.class, id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean deleteParam(Parameter parameterDel) {
        try {
            Query query = entityManager.createQuery("delete from Parameter p where p.id=:id").setParameter("id", parameterDel.getId());
            query.executeUpdate();
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    @Override
    public List<VtPartner> getListByType(Integer typePartner) {
        List<VtPartner> vtPartners = new ArrayList<>();
        try {
            Query queryAll = entityManager.createQuery("select p from VtPartner p where typePartner = :typePartner ");
            queryAll.setParameter("typePartner", typePartner);
            vtPartners = queryAll.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vtPartners;
    }

}
