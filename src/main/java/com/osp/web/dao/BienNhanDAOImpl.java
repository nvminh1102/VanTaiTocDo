package com.osp.web.dao;

import com.osp.common.DateUtils;
import com.osp.common.PagingResult;
import com.osp.model.VtReceipt;
import com.osp.model.VtReceiptDetail;
import com.osp.model.view.VtReceiptView;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class BienNhanDAOImpl implements BienNhanDAO {

    @PersistenceContext(unitName = "appAdmin")
    @Qualifier(value = "transactionManager")
    private EntityManager entityManager;

    @Override
    public VtReceipt add(VtReceipt item) {
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
    public Optional<PagingResult> page(PagingResult page, String receiptCode, String nameStock,
            Date fromGenDate, Date toGenDate, String loaiXe, String bienSo) {
        try {
            int offset = 0;
            if (page.getPageNumber() > 0) {
                offset = (page.getPageNumber() - 1) * page.getNumberPerPage();
            }
            StringBuffer strWhere = new StringBuffer();
            if (receiptCode != null && !"".equals(receiptCode)) {
                strWhere.append(" and UPPER(t.receipt_code) = :receiptCode");
            }
            if (nameStock != null && !"".equals(nameStock)) {
                strWhere.append(" and t.name_Stock = :nameStock");
            }
            if (fromGenDate != null) {
                strWhere.append(" and t.date_receipt >= :fromGenDate");
            }
            if (toGenDate != null) {
                strWhere.append(" and t.date_receipt <= :toGenDate");
            }
            if (loaiXe != null && !loaiXe.trim().equals("")) {
                strWhere.append(" and upper(t.loai_xe) = :loaiXe");
            }
            if (bienSo != null && !bienSo.trim().equals("")) {
                strWhere.append(" and upper(t.bien_so) = :bienSo");
            }
            StringBuffer sqlBuffer = new StringBuffer("SELECT t.ID,t.receipt_code,t.date_receipt,t.name_Stock,t.nha_xe,t.bien_so,t.employee,t.payer,t.payment_type,t.tien_da_tra,b.FULL_NAME as ten_nguoi_gui,b.address as dia_chi_nguoi_gui,c.FULL_NAME as ten_nguoi_nhan,c.address as dia_chi_nguoi_nhan, "
                    + " c.MOBILE as mobile_nguoi_nhan,  "
                    + " (select SUM(d.cost) FROM vt_receipt_detail d WHERE t.id = d.receipt_id) AS tong_tien "
                    + "from vt_receipt t left join vt_partner b on t.delivery_partner_id = b.ID left join vt_partner c on t.receive_partner_id = c.ID left join vt_receipt_detail d on t.id = d.receipt_id "
                    + " where 1=1 ");
            sqlBuffer.append(strWhere.toString());
            sqlBuffer.append(" order by t.GEN_DATE DESC");

            StringBuffer sqlBufferCount = new StringBuffer("SELECT count(t.id) "
                    + "from vt_receipt t left join vt_partner b on t.delivery_partner_id = b.ID left join vt_partner c on t.receive_partner_id = c.ID left join vt_receipt_detail d on t.id = d.receipt_id "
                    + " where 1=1 ");
            sqlBufferCount.append(strWhere.toString());

            Query query = entityManager.createNativeQuery(sqlBuffer.toString(), VtReceiptView.class);
            if (receiptCode != null && !"".equals(receiptCode)) {
                query.setParameter("receiptCode", receiptCode.trim().toUpperCase());
            }
            if (nameStock != null && !"".equals(nameStock)) {
                query.setParameter("nameStock", nameStock.trim().toUpperCase());
            }
            if (fromGenDate != null) {
                query.setParameter("fromGenDate", fromGenDate);
            }
            if (toGenDate != null) {
                query.setParameter("toGenDate", DateUtils.addDays(toGenDate, 1));
            }

            if (loaiXe != null && !loaiXe.trim().equals("")) {
                query.setParameter("loaiXe", loaiXe.trim().toUpperCase());
            }
            if (bienSo != null && !bienSo.trim().equals("")) {
                query.setParameter("bienSo", bienSo.trim().toUpperCase());
            }

            List<VtReceiptView> list = query.setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
            if (list != null && list.size() > 0) {
                page.setItems(list);
            }
            Query queryCount = entityManager.createNativeQuery(sqlBufferCount.toString());
            if (receiptCode != null && !"".equals(receiptCode)) {
                queryCount.setParameter("receiptCode", receiptCode.trim().toUpperCase());
            }
            if (nameStock != null && !"".equals(nameStock)) {
                queryCount.setParameter("nameStock", nameStock.trim().toUpperCase());
            }
            if (fromGenDate != null) {
                queryCount.setParameter("fromGenDate", fromGenDate);
            }
            if (toGenDate != null) {
                queryCount.setParameter("toGenDate", DateUtils.addDays(toGenDate, 1));
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
    public Optional<PagingResult> search(PagingResult page, String receiptCode, String nameStock,
            Date fromGenDate, Date toGenDate, String loaiXe, String bienSo) {
        try {
            StringBuffer strWhere = new StringBuffer();
            if (receiptCode != null && !"".equals(receiptCode)) {
                strWhere.append(" and UPPER(t.receipt_code) = :receiptCode");
            }
            if (nameStock != null && !"".equals(nameStock)) {
                strWhere.append(" and t.name_Stock = :nameStock");
            }
            if (fromGenDate != null) {
                strWhere.append(" and t.date_receipt >= :fromGenDate");
            }
            if (toGenDate != null) {
                strWhere.append(" and t.date_receipt <= :toGenDate");
            }
            if (loaiXe != null && !loaiXe.trim().equals("")) {
                strWhere.append(" and upper(t.loai_xe) = :loaiXe");
            }
            if (bienSo != null && !bienSo.trim().equals("")) {
                strWhere.append(" and upper(t.bien_so) = :bienSo");
            }

            StringBuffer sqlBuffer = new StringBuffer("SELECT t.ID,t.receipt_code,t.date_receipt,t.name_Stock,t.nha_xe,t.bien_so,t.employee,t.payment_type,t.tien_da_tra,b.FULL_NAME as ten_nguoi_gui,b.address as dia_chi_nguoi_gui,c.FULL_NAME as ten_nguoi_nhan,c.address as dia_chi_nguoi_nhan, "
                    + " c.MOBILE as mobile_nguoi_nhan, t.payer,  "
                    + " (select SUM(d.cost) FROM vt_receipt_detail d WHERE t.id = d.receipt_id) AS tong_tien "
                    + "from vt_receipt t left join vt_partner b on t.delivery_partner_id = b.ID left join vt_partner c on t.receive_partner_id = c.ID left join vt_receipt_detail d on t.id = d.receipt_id "
                    + " where t.status  = 1 ");
            sqlBuffer.append(strWhere.toString());
            sqlBuffer.append(" order by t.GEN_DATE DESC");

            Query query = entityManager.createNativeQuery(sqlBuffer.toString(), VtReceiptView.class);
            if (receiptCode != null && !"".equals(receiptCode)) {
                query.setParameter("receiptCode", receiptCode.trim().toUpperCase());
            }
            if (nameStock != null && !"".equals(nameStock)) {
                query.setParameter("nameStock", nameStock.trim().toUpperCase());
            }
            if (fromGenDate != null) {
                query.setParameter("fromGenDate", fromGenDate);
            }
            if (toGenDate != null) {
                query.setParameter("toGenDate", DateUtils.addDays(toGenDate, 1));
            }

            if (loaiXe != null && !loaiXe.trim().equals("")) {
                query.setParameter("loaiXe", loaiXe.trim().toUpperCase());
            }
            if (bienSo != null && !bienSo.trim().equals("")) {
                query.setParameter("bienSo", bienSo.trim().toUpperCase());
            }

            List<VtReceiptView> list = query.getResultList();
            if (list != null && list.size() > 0) {
                page.setItems(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(page);
    }

    @Override
    public boolean delete(int id) {
        try {
            String sql = "delete from vt_receipt where ID = :id";
            Query query = entityManager.createNativeQuery(sql).setParameter("id", id);
            query.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public VtReceipt getById(Integer id) {
        VtReceipt info = new VtReceipt();
        try {
            info = entityManager.find(VtReceipt.class, id);
            return info;
        } catch (Exception e) {
            e.printStackTrace();
            return info;
        }
    }

    @Override
    public boolean edit(VtReceipt item) {
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
    public List<VtReceiptDetail> getListVtReceiptDetail(Integer id) {
        List<VtReceiptDetail> vtReceiptDetails = new ArrayList<>();
        try {
            // load status = 1: các phiếu nhận chưa lên toa
            Query queryAll = entityManager.createQuery("select r from VtReceiptDetail r where r.receiptId = :receiptId and r.status = 1 ");
            queryAll.setParameter("receiptId", id);
            vtReceiptDetails = queryAll.getResultList();
            if (vtReceiptDetails != null && vtReceiptDetails.size() > 0) {
                for (VtReceiptDetail vtReceiptDetail : vtReceiptDetails) {
                    VtReceipt info = entityManager.find(VtReceipt.class, id);
                    vtReceiptDetail.setReceiptCode(info.getReceiptCode());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vtReceiptDetails;
    }

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
