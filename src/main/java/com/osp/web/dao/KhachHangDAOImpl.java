package com.osp.web.dao;

import com.osp.model.VtPartner;
import com.osp.model.VtReceipt;
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
      String sql = " select * from vt_partner where tax_code=:taxCode and type_partner =:typePartner";
      Query query = entityManager.createNativeQuery(sql, VtPartner.class);
      query.setParameter("taxCode", taxCode.trim());
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
}
