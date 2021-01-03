package com.osp.web.dao;

import com.osp.common.PagingResult;
import com.osp.model.VtPartner;
import com.osp.model.view.VtReceiptView;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(value = "transactionManager")
public class KhachHangDAOImpl implements KhachHangDAO {
  @PersistenceContext(unitName = "appAdmin")
  @Qualifier(value = "transactionManager")
  private EntityManager entityManager;

  @Override
  public VtPartner add(VtPartner item) {
    try {
      entityManager.persist(item);
      entityManager.flush();
      return item;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
  @Override
  public VtPartner getByCMND(String taxCode,int typePartner) {
    try {
      String sql = " select p from VtPartner p where upper(p.taxCode)=:taxCode and p.typePartner =:typePartner";
      Query query = entityManager.createQuery(sql, VtPartner.class);
      query.setParameter("taxCode", taxCode.trim().toUpperCase());
      query.setParameter("typePartner", typePartner);
      List<VtPartner> partners = query.getResultList();
      if (partners.size() > 0) {
        return partners.get(0);
      } else {
        return new VtPartner();
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public VtPartner getById(Integer id) {
    VtPartner info = new VtPartner();
    try {
      info = entityManager.find(VtPartner.class, id);
      return info;
    } catch (Exception e) {
      e.printStackTrace();
      return info;
    }
  }

  @Override
  public boolean edit(VtPartner item) {
    try {
      entityManager.merge(item);
      entityManager.flush();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override
  public Optional<PagingResult> page(PagingResult page, String fullName, String taxCode,
      String mobile, String address, Long typePartner) {
    try {
      int offset = 0;
      if (page.getPageNumber() > 0) {
        offset = (page.getPageNumber() - 1) * page.getNumberPerPage();
      }
      StringBuffer strWhere = new StringBuffer();
      if (fullName != null && !"".equals(fullName)) {
        strWhere.append(" and UPPER(t.fullName) like :fullName");
      }
      if (taxCode != null && !"".equals(taxCode)) {
        strWhere.append(" and UPPER(t.taxCode) like :taxCode");
      }
      if (mobile != null && !"".equals(mobile)) {
        strWhere.append(" and UPPER(t.mobile) like :mobile");
      }
      if (address != null && !"".equals(address)) {
        strWhere.append(" and UPPER(t.address) like :address");
      }
      if (typePartner != null) {
        strWhere.append(" and t.typePartner = :typePartner");
      }

      StringBuffer sqlBuffer = new StringBuffer("SELECT t "
          + " from VtPartner t "
          + " where 1=1 ");
      sqlBuffer.append(strWhere.toString());
      sqlBuffer.append(" order by t.genDate DESC");

      StringBuffer sqlBufferCount = new StringBuffer("SELECT count(t.id) "
          + "from VtPartner t "
          + " where 1=1 ");
      sqlBufferCount.append(strWhere.toString());

      Query query = entityManager.createQuery(sqlBuffer.toString(), VtPartner.class);
      if (fullName != null && !"".equals(fullName)) {
        query.setParameter("fullName", "%" +fullName.trim().toUpperCase()+ "%");
      }
      if (taxCode != null && !"".equals(taxCode)) {
        query.setParameter("taxCode", "%" +taxCode.trim().toUpperCase()+ "%");
      }
      if (mobile != null && !"".equals(mobile)) {
        query.setParameter("mobile", "%" +mobile.trim().toUpperCase()+ "%");
      }
      if (address != null && !"".equals(address)) {
        query.setParameter("address", "%" +address.trim().toUpperCase()+ "%");
      }
      if (typePartner != null) {
        query.setParameter("typePartner", typePartner);
      }

      List<VtReceiptView> list = query.setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
      if (list != null && list.size() > 0) {
        page.setItems(list);
      }
      Query queryCount = entityManager.createQuery(sqlBufferCount.toString());
      if (fullName != null && !"".equals(fullName)) {
        queryCount.setParameter("fullName", "%" +fullName.trim().toUpperCase()+ "%");
      }
      if (taxCode != null && !"".equals(taxCode)) {
        queryCount.setParameter("taxCode", "%" +taxCode.trim().toUpperCase()+ "%");
      }
      if (mobile != null && !"".equals(mobile)) {
        queryCount.setParameter("mobile", "%" +mobile.trim().toUpperCase()+ "%");
      }
      if (address != null && !"".equals(address)) {
        queryCount.setParameter("address", "%" +address.trim().toUpperCase()+ "%");
      }
      if (typePartner != null) {
        queryCount.setParameter("typePartner", typePartner);
      }
      List resultList = queryCount.getResultList();
      if (resultList.size() > 0) {
        BigInteger count = (BigInteger) resultList.get(0);
        page.setRowCount(count.longValue());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Optional.ofNullable(page);
  }

  @Override
  public boolean delete(int id) {
    try {
      String sql = "delete from VtPartner where ID = :id";
      Query query = entityManager.createQuery(sql).setParameter("id", id);
      query.executeUpdate();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }
  
  
  @Override
    public List<VtPartner> getListByType(Integer typePartner) {
        List<VtPartner> vtPartners = new ArrayList<>();
        try {
            String sql = "select p from VtPartner p ";
            if(typePartner!=null && typePartner.intValue()>0){
                sql = sql + "  where typePartner = :typePartner  ";
            }
            Query queryAll = entityManager.createQuery(sql);
            if(typePartner!=null && typePartner.intValue()>0){
                queryAll.setParameter("typePartner", typePartner);
            }
            vtPartners = queryAll.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vtPartners;
    }

}
