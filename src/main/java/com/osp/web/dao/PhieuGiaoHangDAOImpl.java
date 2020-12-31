package com.osp.web.dao;

import com.osp.common.DateUtils;
import com.osp.common.PagingResult;
import com.osp.model.User;
import com.osp.model.VtPhieuGiaoHang;
import com.osp.model.VtPhieuGiaoHangDetail;
import com.osp.model.VtReceipt;
import com.osp.model.VtReceiptDetail;
import com.osp.model.VtToaHang;
import com.osp.model.VtToaHangDetail;
import com.osp.model.view.VTGoodsReceiptForm;
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
public class PhieuGiaoHangDAOImpl implements PhieuGiaoHangDAO {

    @PersistenceContext(unitName = "appAdmin")
    @Qualifier(value = "transactionManager")
    private EntityManager entityManager;

    @Override
    public Optional<PagingResult> search(VtPhieuGiaoHang vtPhieuGiaoHang, PagingResult page) {
        int offset = 0;
        if (page.getPageNumber() > 0) {
            offset = (page.getPageNumber() - 1) * page.getNumberPerPage();
        }
        try {
            String strWhere = "";
            if (vtPhieuGiaoHang.getMaPhieuGiao() != null && !vtPhieuGiaoHang.getMaPhieuGiao().trim().equals("")) {
                strWhere = strWhere + " and upper(r.maPhieuGiao) like :maPhieuGiao ";
            }
            if (vtPhieuGiaoHang.getBienSo() != null && !vtPhieuGiaoHang.getBienSo().trim().equals("")) {
                strWhere = strWhere + " and r.bienSo = :bienSo ";
            }
            if (vtPhieuGiaoHang.getFromGenDate() != null) {
                strWhere = strWhere + " and r.genDate >= :fromGenDate ";
            }
            if (vtPhieuGiaoHang.getToGenDate() != null) {
                strWhere = strWhere + " and r.genDate <= :toGenDate ";
            }
            Query queryCount = entityManager.createQuery(" select count(r.id) from VtPhieuGiaoHang r where 1=1 " + strWhere);
            if (vtPhieuGiaoHang.getMaPhieuGiao() != null && !vtPhieuGiaoHang.getMaPhieuGiao().trim().equals("")) {
                queryCount.setParameter("maPhieuGiao", "%" + vtPhieuGiaoHang.getMaPhieuGiao().trim().toUpperCase() + "%");
            }
            if (vtPhieuGiaoHang.getBienSo() != null && !vtPhieuGiaoHang.getBienSo().trim().equals("")) {
                queryCount.setParameter("bienSo", vtPhieuGiaoHang.getBienSo().trim());
            }
            if (vtPhieuGiaoHang.getFromGenDate() != null) {
                queryCount.setParameter("fromGenDate", vtPhieuGiaoHang.getFromGenDate());
            }
            if (vtPhieuGiaoHang.getToGenDate() != null) {
                queryCount.setParameter("toGenDate", DateUtils.addDays(vtPhieuGiaoHang.getToGenDate(), 1));
            }

            List resultList = queryCount.getResultList();
            if (resultList != null && resultList.size() > 0) {
                Long count = (Long) resultList.get(0);
                if (count != null && count.compareTo(0L) > 0) {
                    List<VtPhieuGiaoHang> list = new ArrayList<>();
                    Query queryAll = entityManager.createQuery("select r from VtPhieuGiaoHang r where 1=1 " + strWhere + " order by r.genDate desc ", VtPhieuGiaoHang.class);
                    if (vtPhieuGiaoHang.getMaPhieuGiao() != null && !vtPhieuGiaoHang.getMaPhieuGiao().trim().equals("")) {
                        queryCount.setParameter("maPhieuGiao", "%" + vtPhieuGiaoHang.getMaPhieuGiao().trim().toUpperCase() + "%");
                    }
                    if (vtPhieuGiaoHang.getBienSo() != null && !vtPhieuGiaoHang.getBienSo().trim().equals("")) {
                        queryCount.setParameter("bienSo", vtPhieuGiaoHang.getBienSo().trim());
                    }
                    if (vtPhieuGiaoHang.getFromGenDate() != null) {
                        queryCount.setParameter("fromGenDate", vtPhieuGiaoHang.getFromGenDate());
                    }
                    if (vtPhieuGiaoHang.getToGenDate() != null) {
                        queryCount.setParameter("toGenDate", DateUtils.addDays(vtPhieuGiaoHang.getToGenDate(), 1));
                    }
                    list = queryAll.setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
                    page.setItems(list);
                    page.setRowCount(count);
                }
            }
            return Optional.of(page);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean add(VTGoodsReceiptForm vTGoodsReceiptForm, User user) {
        try {
            VtPhieuGiaoHang vtPhieuGiaoHang = vTGoodsReceiptForm.getVtPhieuGiaoHang();
            vtPhieuGiaoHang.setUpdatedBy(user.getUsername());
            vtPhieuGiaoHang.setLastUpdate(new Date());
            if (vtPhieuGiaoHang.getId() != null) {
                Query query = entityManager.createQuery("delete from VtPhieuGiaoHangDetail a WHERE a.phieuGiaoHangId=:phieuGiaoHangId").setParameter("phieuGiaoHangId", vtPhieuGiaoHang.getId());
                query.executeUpdate();
                entityManager.merge(vtPhieuGiaoHang);
            } else {
                vtPhieuGiaoHang.setGenDate(new Date());
                vtPhieuGiaoHang.setCreatedBy(user.getUsername());
                entityManager.persist(vtPhieuGiaoHang);
            }
            List<VtReceiptView> vtReceiptViews = vTGoodsReceiptForm.getVtReceiptViews();
            for (VtReceiptView bo : vtReceiptViews) {
                VtPhieuGiaoHangDetail vtPhieuGiaoHangDetail = new VtPhieuGiaoHangDetail();
                vtPhieuGiaoHangDetail.setPhieuGiaoHangId(vtPhieuGiaoHang.getId());
                vtPhieuGiaoHangDetail.setCreatedBy(vtPhieuGiaoHang.getCreatedBy());
                vtPhieuGiaoHangDetail.setUpdatedBy(vtPhieuGiaoHang.getUpdatedBy());
                vtPhieuGiaoHangDetail.setLastUpdate(vtPhieuGiaoHang.getLastUpdate());
                vtPhieuGiaoHangDetail.setGenDate(vtPhieuGiaoHang.getGenDate());
                vtPhieuGiaoHangDetail.setReceiptId(bo.getId().intValue());
                Query queryUpdateHangHoa = entityManager.createQuery("update VtReceipt a set a.status =3 WHERE a.id=:id").setParameter("id", bo.getId().intValue());
                queryUpdateHangHoa.executeUpdate();
                entityManager.persist(vtPhieuGiaoHangDetail);
            }
            entityManager.flush();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            return false;
        }
        return true;
    }

    @Override
    public Boolean delete(Integer id, User user, String ip) {
        try {
            if (id != null) {
                Query querydetail = entityManager.createQuery("delete from VtPhieuGiaoHangDetail a WHERE a.phieuGiaoHangId=:phieuGiaoHangId").setParameter("phieuGiaoHangId", id);
                querydetail.executeUpdate();
                Query query = entityManager.createQuery("delete from VtPhieuGiaoHang a WHERE a.id=:id").setParameter("id", id);
                query.executeUpdate();
                entityManager.flush();
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            return false;
        }
        return true;
    }

    @Override
    public VTGoodsReceiptForm getVTGoodsReceiptFormById(Integer id) {
        VTGoodsReceiptForm vTGoodsReceiptForm = new VTGoodsReceiptForm();
        List<Object[]> db = new ArrayList<>();
        List<VtReceiptView> vtReceiptViews = new ArrayList<>();
        Integer soLuong = 0;
        Integer tongTien = 0;
        try {
            Query queryAll = entityManager.createQuery("select r from VtPhieuGiaoHang r where r.id = :id ");
            queryAll.setParameter("id", id);

            VtPhieuGiaoHang vtPhieuGiaoHang = (VtPhieuGiaoHang) queryAll.getSingleResult();
            String sqlBuffer = "SELECT t.ID,t.receipt_code,t.date_receipt,t.name_Stock,t.nha_xe,t.bien_so,t.employee,b.FULL_NAME as ten_nguoi_gui,b.address as dia_chi_nguoi_gui,c.FULL_NAME as ten_nguoi_nhan,c.address as dia_chi_nguoi_nhan, "
                    + " c.MOBILE as mobile_nguoi_nhan, t.payer, t.payment_type , t.tien_da_tra , (select SUM(d.cost) FROM vt_receipt_detail d WHERE t.id = d.receipt_id) AS tong_tien , (select SUM(d.numbers) FROM vt_receipt_detail d WHERE t.id = d.receipt_id) AS so_luong "
                    + " from Vt_Phieu_Giao_Hang_Detail thd inner join vt_receipt t on thd.receipt_Id = t.id left join vt_partner b on t.delivery_partner_id = b.ID   left join vt_partner c on t.receive_partner_id = c.ID   "
                    + " where thd.phieu_giao_hang_id = :phieuGiaoHangId ";

            Query queryDetail = entityManager.createNativeQuery(sqlBuffer);
            queryDetail.setParameter("phieuGiaoHangId", id);
            db = queryDetail.getResultList();

            db.stream().forEach((record) -> {
                VtReceiptView row = new VtReceiptView();
                row.setId(record[0] == null ? null : Long.valueOf(record[0].toString()));
                row.setReceiptCode(record[1] == null ? null : (String) record[1]);
                row.setDateReceipt(record[2] == null ? null : (Date) record[2]);
                row.setNameStock(record[3] == null ? null : (String) record[3]);
                row.setNhaXe(record[4] == null ? null : (String) record[4]);
                row.setBienSo(record[5] == null ? null : (String) record[5]);
                row.setEmployee(record[6] == null ? null : (String) record[6]);
                row.setTenNguoiGui(record[7] == null ? null : (String) record[7]);
                row.setDiaChiNguoiGui(record[8] == null ? null : (String) record[8]);
                row.setTenNguoiNhan(record[9] == null ? null : (String) record[9]);
                row.setDiaChiNguoiNhan(record[10] == null ? null : (String) record[10]);
                row.setMobileNguoiNhan(record[11] == null ? null : (String) record[11]);
                row.setPayer(record[12] == null ? null : (String) record[12]);
                row.setPaymentType(record[13] == null ? null : Integer.valueOf(record[13].toString()));
                row.setTienDaTra(record[14] == null ? null : Long.valueOf(record[14].toString()));
                row.setTongTien(record[15] == null ? null : Long.valueOf(record[15].toString()));
                row.setSoLuong(record[16] == null ? null : Integer.valueOf(record[16].toString()));
                vtReceiptViews.add(row);
            });
            for (VtReceiptView vtReceiptView : vtReceiptViews) {
                soLuong = soLuong + vtReceiptView.getSoLuong();
                tongTien = tongTien + ((vtReceiptView.getTongTien() != null ? vtReceiptView.getTongTien().intValue() : 0) - (vtReceiptView.getTienDaTra() != null ? vtReceiptView.getTienDaTra().intValue() : 0));
            }
            vtPhieuGiaoHang.setSoLuong(soLuong);
            vtPhieuGiaoHang.setTongTien(tongTien);
            vTGoodsReceiptForm.setVtPhieuGiaoHang(vtPhieuGiaoHang);
            vTGoodsReceiptForm.setVtReceiptViews(vtReceiptViews);
            return vTGoodsReceiptForm;
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            return null;
        }
    }
    
    
    @Override
    public VTGoodsReceiptForm getExportById(Integer id) {
        VTGoodsReceiptForm vTGoodsReceiptForm = new VTGoodsReceiptForm();
        List<Object[]> db = new ArrayList<>();
        List<VtReceiptView> vtReceiptViews = new ArrayList<>();
        Integer soLuong = 0;
        Integer tongTien = 0;
        try {
            Query queryAll = entityManager.createQuery("select r from VtPhieuGiaoHang r where r.id = :id ");
            queryAll.setParameter("id", id);

            VtPhieuGiaoHang vtPhieuGiaoHang = (VtPhieuGiaoHang) queryAll.getSingleResult();
            String sqlBuffer = " SELECT t.ID,t.receipt_code,t.date_receipt,t.name_Stock,t.nha_xe,t.bien_so,t.employee,b.FULL_NAME as ten_nguoi_gui,b.address as dia_chi_nguoi_gui,c.FULL_NAME as ten_nguoi_nhan,c.address as dia_chi_nguoi_nhan,  "
                    + " c.MOBILE as mobile_nguoi_nhan, t.payer, t.payment_type , t.tien_da_tra , rd.cost AS thanh_tien , rd.numbers AS so_luong , rd.name, rd.note  "
                    + "  from Vt_Phieu_Giao_Hang_Detail thd  "
                    + " inner join vt_receipt t on thd.receipt_Id = t.id  "
                    + " inner join vt_receipt_detail rd on t.id = rd.receipt_Id   "
                    + " left join vt_partner b on t.delivery_partner_id = b.ID   "
                    + " left join vt_partner c on t.receive_partner_id = c.ID  "
                    + " where thd.phieu_giao_hang_id = :phieuGiaoHangId ";

            Query queryDetail = entityManager.createNativeQuery(sqlBuffer);
            queryDetail.setParameter("phieuGiaoHangId", id);
            db = queryDetail.getResultList();

            db.stream().forEach((record) -> {
                VtReceiptView row = new VtReceiptView();
                row.setId(record[0] == null ? null : Long.valueOf(record[0].toString()));
                row.setReceiptCode(record[1] == null ? null : (String) record[1]);
                row.setDateReceipt(record[2] == null ? null : (Date) record[2]);
                row.setNameStock(record[3] == null ? null : (String) record[3]);
                row.setNhaXe(record[4] == null ? null : (String) record[4]);
                row.setBienSo(record[5] == null ? null : (String) record[5]);
                row.setEmployee(record[6] == null ? null : (String) record[6]);
                row.setTenNguoiGui(record[7] == null ? null : (String) record[7]);
                row.setDiaChiNguoiGui(record[8] == null ? null : (String) record[8]);
                row.setTenNguoiNhan(record[9] == null ? null : (String) record[9]);
                row.setDiaChiNguoiNhan(record[10] == null ? null : (String) record[10]);
                row.setMobileNguoiNhan(record[11] == null ? null : (String) record[11]);
                row.setPayer(record[12] == null ? null : (String) record[12]);
                row.setPaymentType(record[13] == null ? null : Integer.valueOf(record[13].toString()));
                row.setTienDaTra(record[14] == null ? null : Long.valueOf(record[14].toString()));
                row.setTongTien(record[15] == null ? null : Long.valueOf(record[15].toString()));
                row.setSoLuong(record[16] == null ? null : Integer.valueOf(record[16].toString()));
                row.setName(record[17] == null ? null : (String)record[17]);
                row.setNote(record[18] == null ? null : (String)record[18]);
                vtReceiptViews.add(row);
            });
            for (VtReceiptView vtReceiptView : vtReceiptViews) {
                soLuong = soLuong + vtReceiptView.getSoLuong();
                tongTien = tongTien + ((vtReceiptView.getTongTien() != null ? vtReceiptView.getTongTien().intValue() : 0) - (vtReceiptView.getTienDaTra() != null ? vtReceiptView.getTienDaTra().intValue() : 0));
            }
            vtPhieuGiaoHang.setSoLuong(soLuong);
            vtPhieuGiaoHang.setTongTien(tongTien);
            vTGoodsReceiptForm.setVtPhieuGiaoHang(vtPhieuGiaoHang);
            vTGoodsReceiptForm.setVtReceiptViews(vtReceiptViews);
            return vTGoodsReceiptForm;
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            return null;
        }
    }

    @Override
    public List<VtReceiptDetail> getPhieuNhanHang(Integer id) {
        List<Object[]> db = new ArrayList<>();
        List<VtReceiptDetail> items = new ArrayList<>();
        try {
            String sqlBuffer = " select t.id, t.receipt_code, b.FULL_NAME as ten_nguoi_gui,  c.FULL_NAME as ten_nguoi_nhan, "
                    + " c.address as dia_chi_nguoi_nhan, c.MOBILE as mobile_nguoi_nhan, td.name, td.numbers, td.cost , td.sizes, td.weight, td.note , t.payer, b.address as diaChiGui, b.MOBILE as sdtGui, DATE_FORMAT(t.gen_date, '%d-%m-%Y') as strGenDate, t.name_Stock "
                    + " from vt_receipt t inner join vt_receipt_detail td on t.id = td.receipt_id left join vt_partner b on t.delivery_partner_id = b.ID   left join vt_partner c on t.receive_partner_id = c.ID  "
                    + " where t.id = :id order by t.id, td.id ";
            Query queryDetail = entityManager.createNativeQuery(sqlBuffer);
            queryDetail.setParameter("id", id);
            db = queryDetail.getResultList();

            db.stream().forEach((record) -> {
                VtReceiptDetail row = new VtReceiptDetail();
                row.setId(record[0] == null ? null : Integer.valueOf(record[0].toString()));
                row.setReceiptCode(record[1] == null ? null : (String) record[1]);
                row.setTenNguoiGui(record[2] == null ? null : (String) record[2]);
                row.setTenNguoiNhan(record[3] == null ? null : (String) record[3]);
                row.setDiaChiNguoiNhan(record[4] == null ? null : (String) record[4]);
                row.setSdtNguoiNhan(record[5] == null ? null : (String) record[5]);
                row.setName(record[6] == null ? null : (String) record[6]);
                row.setNumbers(record[7] == null ? null : Integer.valueOf(record[7].toString()));
                row.setCost(record[8] == null ? null : Integer.valueOf(record[8].toString()));
                row.setSizes(record[9] == null ? null : (String)record[9]);
                row.setWeight(record[10] == null ? null : (String)record[10]);
                row.setNote(record[11] == null ? null : (String) record[11]);
                row.setNguoiThanhToan(record[12] == null ? null : (String) record[12]);
                row.setDiaChiNguoiGui(record[13] == null ? null : (String) record[13]);
                row.setSdtNguoiGui(record[14] == null ? null : (String) record[14]);
                row.setStrGenDate(record[15] == null ? null : (String) record[15]);
                row.setStockName(record[16] == null ? null : (String) record[16]);
                items.add(row);
            });
            return items;
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
            return null;
        }
    }

    @Override
    public Integer getMaxId() {
        Integer maxId = 0;
        try {
            Query query = entityManager.createQuery(" select max(r.id) from VtPhieuGiaoHang r ");
            maxId = (Integer) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maxId;
    }

}
