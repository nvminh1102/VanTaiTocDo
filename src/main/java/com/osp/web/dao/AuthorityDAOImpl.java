/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osp.web.dao;

import com.osp.common.PagingResult;
import com.osp.common.QueryBuilder;
import com.osp.model.Authority;
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
public class AuthorityDAOImpl implements AuthorityDAO {

    @PersistenceContext(unitName = "appAdmin")
    @Qualifier(value = "transactionManager")
    private EntityManager entityManager;
    private Logger logger = LogManager.getLogger(AuthorityDAOImpl.class);

    @Override
    public Optional<PagingResult> page(PagingResult page, String authKey) {
        int offset = 0;
        if (page.getPageNumber() > 0) {
            offset = (page.getPageNumber() - 1) * page.getNumberPerPage();
        }

        StringBuffer sqlBuffer = new StringBuffer("SELECT p.id,p.authKey,p.authority,p.description, p.orderId, p.fid ,p.genDate,p.lastUpdated,to_char(p.genDate,'DD/MM/YYYY hh24:mi:ss')"
                + "from Authority p ");
        StringBuffer sqlBufferCount = new StringBuffer("SELECT count(p.id) "
                + "from Authority p ");
        Query query = filterBuilderSingle(sqlBuffer, true, authKey);
        List<Object[]> list = (page.getPageNumber() == 0) ? query.getResultList() : query.setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
        if (list != null && list.size() > 0) {
            page.setItems(list);
        }
        Query queryCount = filterBuilderSingle(sqlBufferCount, false, authKey);
        Long rowCount = (Long) queryCount.getSingleResult();
        if (rowCount != null) {
            page.setRowCount(rowCount);
        }
        return Optional.ofNullable(page);
    }

    private Query filterBuilderSingle(StringBuffer stringBuffer, boolean order, String authKey) {
        Query result = null;
        try {
            QueryBuilder builder = new QueryBuilder(entityManager, stringBuffer);
            if (StringUtils.isNotBlank(authKey)) {
                builder.and(QueryBuilder.LIKE, "UPPER(p.authKey)", "%" + authKey.toUpperCase() + "%");
            }
            builder.addOrder("p.genDate", QueryBuilder.DESC);
            result = builder.initQuery(order);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean addAuthority(Authority authItem) {
        try {
            entityManager.persist(authItem);
            entityManager.flush();
            return true;
        } catch (Exception e) {
        }

        return false;
    }

    @Override
    public boolean isExits(Authority authItem) {
        boolean result = true;
        try {
            StringBuffer sqlBuffer = new StringBuffer("SELECT Count(o) FROM Authority o");
            QueryBuilder builder = new QueryBuilder(entityManager, sqlBuffer);
            builder.and(QueryBuilder.EQ, "UPPER(o.authKey)", authItem.getAuthKey().trim().toUpperCase());
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
    public boolean editAuthority(Authority authItem) {
        try {
            entityManager.merge(authItem);
            entityManager.flush();
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    @Override
    public Authority getAuthorityById(Long id) {
        Authority result = null;
        try {
            result = entityManager.find(Authority.class, id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean deleteAuthority(Authority authDel) {
        try {
            Query query = entityManager.createQuery("delete from Authority p where p.id=:id").setParameter("id", authDel.getId());
            query.executeUpdate();
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    @Override
    public boolean checkAuthorityAssigned(long authId) {
        boolean result = true;
        try {
            StringBuffer sqlBuffer = new StringBuffer("SELECT Count(o) FROM GroupAuthority o");
            QueryBuilder builder = new QueryBuilder(entityManager, sqlBuffer);
            builder.and(QueryBuilder.EQ, "UPPER(o.authority)", authId);
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
    public List<Authority> getListAuthParent(long authId) {
        List<Authority> authoritys = null;
        try {
            if (authId > 0L) {
                authoritys = entityManager.createQuery("select au from Authority au where au.fid = 0 and au.id <> :authId", Authority.class).setParameter("authId", authId).getResultList();
            } else {
                authoritys = entityManager.createQuery("select au from Authority au where au.fid = 0 ", Authority.class).getResultList();
            }
        } catch (Exception e) {
        }
        return authoritys;
    }

    @Override
    public List<Authority> getAuthorityChildrenById(long authId) {
        List<Authority> authoritys = null;
        try {
            authoritys = entityManager.createQuery("select au from Authority au where au.fid = :authId", Authority.class).setParameter("authId", authId).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return authoritys;
    }

}
