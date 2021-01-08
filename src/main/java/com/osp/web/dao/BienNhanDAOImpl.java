package com.osp.web.dao;

import com.osp.common.DateUtils;
import com.osp.common.PagingResult;
import com.osp.model.VtReceipt;
import com.osp.model.VtReceiptDetail;
import com.osp.model.view.VtReceiptView;
import java.math.BigInteger;
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
            List<Object[]> db = new ArrayList<>();
            List<VtReceiptView> list = new ArrayList<>();
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
            StringBuffer sqlBuffer = new StringBuffer("SELECT t.id, t.receipt_code, t.date_receipt, t.name_Stock, t.nha_xe, "
                    + " t.bien_so, t.employee, t.payment_type, t.tien_da_tra, t.status, "
                    + " b.FULL_NAME as ten_nguoi_gui, b.address as dia_chi_nguoi_gui, c.FULL_NAME as ten_nguoi_nhan, c.address as dia_chi_nguoi_nhan, c.MOBILE as mobile_nguoi_nhan, "
                    + " (select SUM(d.cost) FROM vt_receipt_detail d WHERE t.id = d.receipt_id) AS tong_tien, d.FULL_NAME, "
                    + " (select ma_phieu_thu from vt_in_phieu_thu where id  = (select max(id) from vt_in_phieu_thu ipt where  t.id = ipt.receipt_id) ) AS ma_phieu_thu,  "
                    + " (select SUM(d.numbers) FROM vt_receipt_detail d WHERE t.id = d.receipt_id) AS so_luong , d.so_hop_dong "
                    + " from vt_receipt t left join vt_partner b on t.delivery_partner_id = b.ID  left join vt_partner c on t.receive_partner_id = c.ID left join vt_partner d on t.nguoi_thanh_toan_id = d.ID  "
                    + " where 1=1 ");
            sqlBuffer.append(strWhere.toString());
            sqlBuffer.append(" order by t.GEN_DATE DESC");

            StringBuffer sqlBufferCount = new StringBuffer("SELECT count(t.id) "
                    + "from vt_receipt t left join vt_partner b on t.delivery_partner_id = b.ID   left join vt_partner c on t.receive_partner_id = c.ID "
                    + " where 1=1 ");
            sqlBufferCount.append(strWhere.toString());
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

                Query query = entityManager.createNativeQuery(sqlBuffer.toString());
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
                db = query.setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();

                db.stream().forEach((record) -> {
                    VtReceiptView row = new VtReceiptView();
                    row.setId(record[0] == null ? null : Long.valueOf(record[0].toString()));
                    row.setReceiptCode(record[1] == null ? null : (String) record[1]);
                    row.setDateReceipt(record[2] == null ? null : (Date) record[2]);
                    row.setNameStock(record[3] == null ? null : (String) record[3]);
                    row.setNhaXe(record[4] == null ? null : (String) record[4]);
                    
                    
                    row.setBienSo(record[5] == null ? null : (String) record[5]);                  
                    row.setEmployee(record[6] == null ? null : (String) record[6]);
                    row.setPaymentType(record[7] == null ? null : Integer.valueOf(record[7].toString()));
                    row.setTienDaTra(record[8] == null ? null : Long.valueOf(record[8].toString()));
                    row.setStatus(record[9] == null ? null : Long.valueOf(record[9].toString()));
                    
                    
                    row.setTenNguoiGui(record[10] == null ? null : (String) record[10]);
                    row.setDiaChiNguoiGui(record[11] == null ? null : (String) record[11]);
                    row.setTenNguoiNhan(record[12] == null ? null : (String) record[12]);
                    row.setDiaChiNguoiNhan(record[13] == null ? null : (String) record[13]);
                    row.setMobileNguoiNhan(record[14] == null ? null : (String) record[14]);
                    
                    
                    row.setTongTien(record[15] == null ? null : Long.valueOf(record[15].toString()));
                    row.setPayer(record[16] == null ? null : (String)record[16]);
                    row.setMaPhieuThu(record[17] == null ? null : (String) record[17]);
                    row.setSoLuong(record[18] == null ? null : Integer.valueOf(record[18].toString()));
                    row.setSoHopDong(record[19] == null ? null : (String) record[19]);
                    list.add(row);
                });

                if (list != null && list.size() > 0) {
                    page.setItems(list);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(page);
    }

    @Override
    public Optional<PagingResult> search(PagingResult page, String receiptCode, String nameStock,
            Date fromGenDate, Date toGenDate, String loaiXe, String bienSo, Integer status) {
        List<Object[]> db = new ArrayList<>();
        List<VtReceiptView> list = new ArrayList<>();
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
            if (status != null && status.compareTo(0) >= 0) {
                strWhere.append(" and t.status = :status");
            }

            StringBuffer sqlBuffer = new StringBuffer("SELECT t.id, t.receipt_code, t.date_receipt, t.name_Stock, t.nha_xe, "
                    + " t.bien_so, t.employee, t.payment_type, t.tien_da_tra, t.status, "
                    + " b.FULL_NAME as ten_nguoi_gui, b.address as dia_chi_nguoi_gui, c.FULL_NAME as ten_nguoi_nhan, c.address as dia_chi_nguoi_nhan, c.MOBILE as mobile_nguoi_nhan, "
                    + " t.payer,  "
                    + " (select SUM(d.cost) FROM vt_receipt_detail d WHERE t.id = d.receipt_id) AS tong_tien, "
                    + " (select ma_phieu_thu from vt_in_phieu_thu where id  = (select max(id) from vt_in_phieu_thu ipt where  t.id = ipt.receipt_id) ) AS ma_phieu_thu,  "
                    + " (select SUM(d.numbers) FROM vt_receipt_detail d WHERE t.id = d.receipt_id) AS so_luong , "
                    + " d.so_hop_dong, "
                    + " (select max(th.toa_hang_code) from vt_toa_hang th inner join vt_toa_hang_detail thd on th.id = thd.toa_hang_id where  thd.receipt_id = t.id) as ma_toa_hang "
                    + "from vt_receipt t left join vt_partner b  on t.delivery_partner_id = b.ID   left join vt_partner c on t.receive_partner_id = c.ID  left join vt_partner d on t.receive_partner_id = d.ID  "
                    + " where 1=1 ");
            sqlBuffer.append(strWhere.toString());
            sqlBuffer.append(" order by t.GEN_DATE DESC");

            Query query = entityManager.createNativeQuery(sqlBuffer.toString());
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
            if (status != null && status.compareTo(0) >= 0) {
                query.setParameter("status", status);
            }

            db = query.getResultList();

            db.stream().forEach((record) -> {
                VtReceiptView row = new VtReceiptView();
                row.setId(record[0] == null ? null : Long.valueOf(record[0].toString()));
                row.setReceiptCode(record[1] == null ? null : (String) record[1]);
                row.setDateReceipt(record[2] == null ? null : (Date) record[2]);
                row.setNameStock(record[3] == null ? null : (String) record[3]);
                row.setNhaXe(record[4] == null ? null : (String) record[4]);
                
                row.setBienSo(record[5] == null ? null : (String) record[5]);
                row.setEmployee(record[6] == null ? null : (String) record[6]);
                row.setPaymentType(record[7] == null ? null : Integer.valueOf(record[7].toString()));
                row.setTienDaTra(record[8] == null ? null : Long.valueOf(record[8].toString()));
                row.setStatus(record[9] == null ? null : Long.valueOf(record[9].toString()));
                
                row.setTenNguoiGui(record[10] == null ? null : (String) record[10]);
                row.setDiaChiNguoiGui(record[11] == null ? null : (String) record[11]);
                row.setTenNguoiNhan(record[12] == null ? null : (String) record[12]);
                row.setDiaChiNguoiNhan(record[13] == null ? null : (String) record[13]);
                row.setMobileNguoiNhan(record[14] == null ? null : (String) record[14]);
                
                row.setPayer(record[15] == null ? null : (String) record[15]);
                row.setTongTien(record[16] == null ? null : Long.valueOf(record[16].toString()));
                row.setMaPhieuThu(record[17] == null ? null : (String) record[17]);
                row.setSoLuong(record[18] == null ? null : Integer.valueOf(record[18].toString()));
                row.setSoHopDong(record[19] == null ? null : (String) record[19]);
                row.setMaToaHang(record[20] == null ? null : (String) record[20]);
                list.add(row);
            });

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
    public List<VtReceiptDetail> getListVtReceiptDetail(List<Integer> id, Integer status) {
        List<VtReceiptDetail> vtReceiptDetails = new ArrayList<>();
        try {
            // load status = 1: các phiếu nhận chưa lên toa
            Query queryAll = entityManager.createQuery("select r from VtReceiptDetail r where r.receiptId in (:receiptId) and r.status = :status ").setParameter("receiptId", id).setParameter("status", status);
            vtReceiptDetails = queryAll.getResultList();
            if (vtReceiptDetails != null && vtReceiptDetails.size() > 0) {
                for (VtReceiptDetail vtReceiptDetail : vtReceiptDetails) {
                    VtReceipt info = entityManager.find(VtReceipt.class, vtReceiptDetail.getReceiptId());
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
