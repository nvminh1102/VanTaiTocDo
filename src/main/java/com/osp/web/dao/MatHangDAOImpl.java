package com.osp.web.dao;

import com.osp.model.VtReceiptDetail;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(value = "transactionManager")
public class MatHangDAOImpl implements MatHangDAO {
  @PersistenceContext(unitName = "appAdmin")
  @Qualifier(value = "transactionManager")
  private EntityManager entityManager;

  @Override
  public VtReceiptDetail add(VtReceiptDetail item) {
    try {
      entityManager.persist(item);
      entityManager.flush();
      return item;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
