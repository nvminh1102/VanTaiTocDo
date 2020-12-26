package com.osp.web.dao;

import com.osp.model.VtReceiptDetail;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

  @Override
  public List<VtReceiptDetail> getDsMatHang(Integer receiptId) {
    List<VtReceiptDetail> result = new ArrayList<>();
    try {
      StringBuffer sqlBuffer = new StringBuffer("SELECT * "
              + " from vt_receipt_detail t "
              + " where t.receipt_id = :receiptId");

      sqlBuffer.append(" order by t.GEN_DATE DESC");

      Query query = entityManager.createNativeQuery(sqlBuffer.toString(), VtReceiptDetail.class);
      if (receiptId != null) {
        query.setParameter("receiptId", receiptId);
      }
      result = query.getResultList();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public boolean delete(int id) {
    try {
      String sql = "delete from vt_receipt_detail where ID = :id";
      Query query = entityManager.createNativeQuery(sql).setParameter("id", id);
      query.executeUpdate();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public boolean edit(VtReceiptDetail item) {
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
  public VtReceiptDetail getById(Integer id) {
    VtReceiptDetail info = new VtReceiptDetail();
    try {
      info = entityManager.find(VtReceiptDetail.class, id);
      return info;
    } catch (Exception e) {
      e.printStackTrace();
      return info;
    }
  }
}
