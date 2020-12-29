package com.osp.web.dao;

import com.osp.common.PagingResult;
import com.osp.model.view.VtReceiptView;
import java.math.BigInteger;
import java.util.Date;
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
public class ThanhToanDAOImpl implements  ThanhToanDAO{
  @PersistenceContext(unitName = "appAdmin")
  @Qualifier(value = "transactionManager")
  private EntityManager entityManager;

  @Override
  public Optional<PagingResult> page(PagingResult page, String soPhieuNhan, String nguoiGui,
      Long typePayment, Long isPayment, Date fromGenDate, Date toGenDate) {
    try {
      int offset = 0;
      if (page.getPageNumber() > 0) {
        offset = (page.getPageNumber() - 1) * page.getNumberPerPage();
      }
      StringBuffer strWhere = new StringBuffer();
      if (soPhieuNhan != null && !"".equals(soPhieuNhan)) {
        strWhere.append(" and UPPER(t.receipt_code) = :soPhieuNhan");
      }
      if (nguoiGui != null && !"".equals(nguoiGui)) {
        strWhere.append(" and UPPER(b.FULL_NAME) like :nguoiGui");
      }
      if (typePayment != null) {
        strWhere.append(" and t.payment_type = :typePayment");
      }
      if (isPayment != null && isPayment == 1L) {
        strWhere.append(" and t.tien_da_tra is not null ");
      }
      if (isPayment != null && isPayment == 2L) {
        strWhere.append(" and t.tien_da_tra is null ");
      }
      if (fromGenDate != null) {
        strWhere.append(" and t.date_receipt >= :fromGenDate");
      }
      if (toGenDate != null) {
        strWhere.append(" and t.date_receipt <= :toGenDate");
      }
      StringBuffer sqlBuffer = new StringBuffer("SELECT t.ID,t.receipt_code,t.date_receipt,t.name_Stock,t.nha_xe,t.bien_so,t.employee,t.payer,t.payment_type,t.tien_da_tra,b.FULL_NAME as ten_nguoi_gui,b.address as dia_chi_nguoi_gui,c.FULL_NAME as ten_nguoi_nhan,c.address as dia_chi_nguoi_nhan, "
          + " c.MOBILE as mobile_nguoi_nhan,  "
              + " (select SUM(d.cost) FROM vt_receipt_detail d WHERE t.id = d.receipt_id) AS tong_tien "
          + "from vt_receipt t left join vt_partner b on t.delivery_partner_id = b.ID left join vt_partner c on t.receive_partner_id = c.ID "
          + " where 1=1 ");
      sqlBuffer.append(strWhere.toString());
      sqlBuffer.append(" order by t.GEN_DATE DESC");

      StringBuffer sqlBufferCount = new StringBuffer("SELECT count(t.id) "
          + "from vt_receipt t left join vt_partner b on t.delivery_partner_id = b.ID left join vt_partner c on t.receive_partner_id = c.ID"
          + " where 1=1 ");
      sqlBufferCount.append(strWhere.toString());

      Query query = entityManager.createNativeQuery(sqlBuffer.toString(), VtReceiptView.class);
      if (soPhieuNhan != null && !"".equals(soPhieuNhan)) {
        query.setParameter("receiptCode", soPhieuNhan.trim().toUpperCase());
      }
      if (nguoiGui != null && !"".equals(nguoiGui)) {
        query.setParameter("nguoiGui", "%" + nguoiGui.trim().toUpperCase() + "%");
      }
      if (typePayment != null) {
        query.setParameter("typePayment", typePayment);
      }
      if (fromGenDate != null) {
        query.setParameter("fromGenDate", fromGenDate);
      }
      if (toGenDate != null) {
        query.setParameter("toGenDate", toGenDate);
      }

      List<VtReceiptView> list = query.setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
      if (list != null && list.size() > 0) {
        page.setItems(list);
      }
      Query queryCount = entityManager.createNativeQuery(sqlBufferCount.toString());
      if (soPhieuNhan != null && !"".equals(soPhieuNhan)) {
        queryCount.setParameter("receiptCode", soPhieuNhan.trim().toUpperCase());
      }
      if (nguoiGui != null && !"".equals(nguoiGui)) {
        queryCount.setParameter("nguoiGui", "%" + nguoiGui.trim().toUpperCase() + "%");
      }
      if (typePayment != null) {
        queryCount.setParameter("typePayment", typePayment);
      }
      if (fromGenDate != null) {
        queryCount.setParameter("fromGenDate", fromGenDate);
      }
      if (toGenDate != null) {
        queryCount.setParameter("toGenDate", toGenDate);
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
  public Integer getMaxId() {
    Integer maxId = 0;
    try {
      Query query = entityManager.createQuery(" select max(r.id) from VtReceipt r ");
      maxId = (Integer) query.getSingleResult();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return maxId;
  }
}
