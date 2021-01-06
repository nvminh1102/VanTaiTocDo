package com.osp.web.dao;

import com.osp.common.DateUtils;
import com.osp.common.PagingResult;
import com.osp.model.User;
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
public class ToaHangDAOImpl implements ToaHangDAO {

    @PersistenceContext(unitName = "appAdmin")
    @Qualifier(value = "transactionManager")
    private EntityManager entityManager;

    @Override
    public Optional<PagingResult> search(VtToaHang vtToaHang, PagingResult page) {
        int offset = 0;
        if (page.getPageNumber() > 0) {
            offset = (page.getPageNumber() - 1) * page.getNumberPerPage();
        }
        try {
            String strWhere = "";
            if (vtToaHang.getToaHangCode() != null && !vtToaHang.getToaHangCode().trim().equals("")) {
                strWhere = strWhere + " and upper(r.toaHangCode) like :toaHangCode ";
            }
            if (vtToaHang.getBienSo() != null && !vtToaHang.getBienSo().trim().equals("")) {
                strWhere = strWhere + " and r.bienSo = :bienSo ";
            }
            if (vtToaHang.getNoiDen() != null && !vtToaHang.getNoiDen().trim().equals("")) {
                strWhere = strWhere + " and r.noiDen = :noiDen ";
            }
            if (vtToaHang.getNoiDi() != null && !vtToaHang.getNoiDi().trim().equals("")) {
                strWhere = strWhere + " and r.noiDi = :noiDi ";
            }
            if (vtToaHang.getFromGenDate() != null) {
                strWhere = strWhere + " and r.genDate >= :fromGenDate ";
            }
            if (vtToaHang.getToGenDate() != null) {
                strWhere = strWhere + " and r.genDate <= :toGenDate ";
            }
            Query queryCount = entityManager.createQuery(" select count(r.id) from VtToaHang r where 1=1 " + strWhere);
            if (vtToaHang.getToaHangCode() != null && !vtToaHang.getToaHangCode().trim().equals("")) {
                queryCount.setParameter("toaHangCode", "%" + vtToaHang.getToaHangCode().trim().toUpperCase() + "%");
            }
            if (vtToaHang.getBienSo() != null && !vtToaHang.getBienSo().trim().equals("")) {
                queryCount.setParameter("bienSo", vtToaHang.getBienSo().trim());
            }
            if (vtToaHang.getNoiDen() != null && !vtToaHang.getNoiDen().trim().equals("")) {
                queryCount.setParameter("noiDen", vtToaHang.getNoiDen().trim());
            }
            if (vtToaHang.getNoiDi() != null && !vtToaHang.getNoiDi().trim().equals("")) {
                queryCount.setParameter("noiDi", vtToaHang.getNoiDi().trim());
            }
            if (vtToaHang.getFromGenDate() != null) {
                queryCount.setParameter("fromGenDate", vtToaHang.getFromGenDate());
            }
            if (vtToaHang.getToGenDate() != null) {
                queryCount.setParameter("toGenDate", DateUtils.addDays(vtToaHang.getToGenDate(), 1));
            }

            List resultList = queryCount.getResultList();
            if (resultList != null && resultList.size() > 0) {
                Long count = (Long) resultList.get(0);
                if (count != null && count.compareTo(0L) > 0) {
                    List<VtToaHang> list = new ArrayList<>();
                    Query queryAll = entityManager.createQuery("select r from VtToaHang r where 1=1 " + strWhere + " order by r.genDate desc ", VtToaHang.class);
                    if (vtToaHang.getToaHangCode() != null && !vtToaHang.getToaHangCode().trim().equals("")) {
                        queryAll.setParameter("toaHangCode", "%" + vtToaHang.getToaHangCode().trim().toUpperCase() + "%");
                    }
                    if (vtToaHang.getBienSo() != null && !vtToaHang.getBienSo().trim().equals("")) {
                        queryAll.setParameter("bienSo", vtToaHang.getBienSo().trim());
                    }
                    if (vtToaHang.getNoiDen() != null && !vtToaHang.getNoiDen().trim().equals("")) {
                        queryAll.setParameter("noiDen", vtToaHang.getNoiDen().trim());
                    }
                    if (vtToaHang.getNoiDi() != null && !vtToaHang.getNoiDi().trim().equals("")) {
                        queryAll.setParameter("noiDi", vtToaHang.getNoiDi().trim());
                    }
                    if (vtToaHang.getFromGenDate() != null) {
                        queryAll.setParameter("fromGenDate", vtToaHang.getFromGenDate());
                    }
                    if (vtToaHang.getToGenDate() != null) {
                        queryAll.setParameter("toGenDate", DateUtils.addDays(vtToaHang.getToGenDate(), 1));
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
            VtToaHang vtToaHang = vTGoodsReceiptForm.getVtToaHang();
            vtToaHang.setUpdatedBy(user.getUsername());
            vtToaHang.setLastUpdate(new Date());
            if (vtToaHang != null && vtToaHang.getId() != null) {
                Query query = entityManager.createQuery("delete from VtToaHangDetail a WHERE a.toaHangId=:toaHangId").setParameter("toaHangId", vtToaHang.getId());
                query.executeUpdate();
                entityManager.merge(vtToaHang);
            } else {
                vtToaHang.setGenDate(new Date());
                vtToaHang.setCreatedBy(user.getUsername());
                entityManager.persist(vtToaHang);
            }
            List<VtReceiptDetail> vtReceiptDetails = vTGoodsReceiptForm.getVtReceiptDetail();
            for (VtReceiptDetail bo : vtReceiptDetails) {
                VtToaHangDetail vtToaHangDetail = new VtToaHangDetail();
                vtToaHangDetail.setToaHangId(vtToaHang.getId());
                vtToaHangDetail.setCreatedBy(vtToaHang.getCreatedBy());
                vtToaHangDetail.setUpdatedBy(vtToaHang.getUpdatedBy());
                vtToaHangDetail.setLastUpdate(vtToaHang.getLastUpdate());
                vtToaHangDetail.setGenDate(vtToaHang.getGenDate());
                vtToaHangDetail.setReceiptId(bo.getReceiptId());
                vtToaHangDetail.setVtReceiptDetailId(bo.getId());
                Query queryUpdateHangHoa = entityManager.createQuery("update VtReceiptDetail a set a.status =2 WHERE a.id=:id").setParameter("id", bo.getId());
                queryUpdateHangHoa.executeUpdate();
                entityManager.persist(vtToaHangDetail);
            }
            List<VtReceiptView> vtReceiptViews = vTGoodsReceiptForm.getVtReceiptViews();
            for (VtReceiptView vtReceiptView : vtReceiptViews) {
                // update các bản ghi VtReceipt trạng thái =2 với DK: status = 1 và ko có bản ghi VtReceiptDetail nào có trạng thái =1
                Query queryUpdateHangHoa = entityManager.createQuery("update VtReceipt a set a.status = 2 ,  a.createdBy = :createdBy ,  a.lastUpdate = CURRENT_TIMESTAMP() WHERE a.id=:receiptId and status = 1 "
                        + " and id not in (select receiptId from VtReceiptDetail where status = 1 and receiptId = :receiptId )").setParameter("receiptId", Integer.valueOf(vtReceiptView.getId().intValue())).setParameter("createdBy", user.getUsername());
                queryUpdateHangHoa.executeUpdate();
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
                Query querydetail = entityManager.createQuery("delete from VtToaHangDetail a WHERE a.toaHangId=:toaHangId").setParameter("toaHangId", id);
                querydetail.executeUpdate();
                Query query = entityManager.createQuery("delete from VtToaHang a WHERE a.id=:id").setParameter("id", id);
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
        try {
            Query queryAll = entityManager.createQuery("select r from VtToaHang r where r.id = :id ");
            queryAll.setParameter("id", id);

            VtToaHang vtToaHang = (VtToaHang) queryAll.getSingleResult();
            vTGoodsReceiptForm.setVtToaHang(vtToaHang);
            String sqlBuffer = "SELECT distinct t.ID,t.receipt_code,t.date_receipt,t.name_Stock,t.nha_xe,t.bien_so,t.employee,b.FULL_NAME as ten_nguoi_gui,b.address as dia_chi_nguoi_gui,c.FULL_NAME as ten_nguoi_nhan,c.address as dia_chi_nguoi_nhan, "
                    + " c.MOBILE as mobile_nguoi_nhan, t.payer, t.payment_type , t.tien_da_tra , (select SUM(d.cost) FROM vt_receipt_detail d WHERE t.id = d.receipt_id) AS tong_tien, "
                    + " (select ma_phieu_thu from vt_in_phieu_thu where id  = (select max(id) from vt_in_phieu_thu ipt where  t.id = ipt.receipt_id) ) AS ma_phieu_thu , d.so_hop_dong "
                    + " from vt_toa_hang_detail thd inner join vt_receipt t on thd.receipt_Id = t.id left join vt_partner b on t.delivery_partner_id = b.ID  left join vt_partner c on t.receive_partner_id = c.ID  left join vt_partner d on t.receive_partner_id = d.ID  "
                    + " where thd.toa_hang_id = :toahangid ";

            Query queryDetail = entityManager.createNativeQuery(sqlBuffer);
            queryDetail.setParameter("toahangid", id);
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
                row.setMaPhieuThu(record[16] == null ? null : (String)record[16]);
                row.setSoHopDong(record[17] == null ? null : (String)record[17]);
                vtReceiptViews.add(row);
            });

            vTGoodsReceiptForm.setVtReceiptViews(vtReceiptViews);
            return vTGoodsReceiptForm;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public VTGoodsReceiptForm getListBienNhan(Integer id) {
        VTGoodsReceiptForm vTGoodsReceiptForm = new VTGoodsReceiptForm();
        List<Object[]> db = new ArrayList<>();
        List<VtReceiptDetail> items = new ArrayList<>();
        Integer soLuong = 0;
        Integer tongTien = 0;
        try {
            Query queryAll = entityManager.createQuery("select r from VtToaHang r where r.id = :id ");
            queryAll.setParameter("id", id);

            VtToaHang vtToaHang = (VtToaHang) queryAll.getSingleResult();
            String sqlBuffer = " select t.id, t.receipt_code, b.FULL_NAME as ten_nguoi_gui, c.FULL_NAME as ten_nguoi_nhan,c.address as dia_chi_nguoi_nhan, c.MOBILE as mobile_nguoi_nhan, td.name, td.numbers, td.cost , d.so_hop_dong , t.payment_type, "
                    + " (select ma_phieu_thu from vt_in_phieu_thu where id  = (select max(id) from vt_in_phieu_thu ipt where  t.id = ipt.receipt_id) ) AS ma_phieu_thu, t.tien_da_tra  "
                    + " from vt_toa_hang_detail thd inner join vt_receipt t on thd.receipt_Id = t.id  inner join vt_receipt_detail td on thd.vt_receipt_detail_id = td.id left join vt_partner b on t.delivery_partner_id = b.ID   left join vt_partner c on t.receive_partner_id = c.ID  left join vt_partner d on t.nguoi_thanh_toan_id = d.ID   "
                    + " where thd.toa_hang_id = :toahangid order by thd.id, t.id, td.id ";
            Query queryDetail = entityManager.createNativeQuery(sqlBuffer);
            queryDetail.setParameter("toahangid", id);
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
                row.setSoHopDong(record[9] == null ? null : (String) record[9]);
                row.setPaymentType(record[10] == null ? null : Integer.valueOf(record[10].toString()));
                row.setMaPhieuThu(record[11] == null ? null : (String) record[11]);
                row.setTienDaTra(record[12] == null ? null : Integer.valueOf(record[12].toString()));
                items.add(row);
            });
            for (VtReceiptDetail vtReceiptDetail : items) {
                soLuong = soLuong + vtReceiptDetail.getNumbers();
//                tongTien = tongTien + vtReceiptDetail.getCost();
//                tongTien = tongTien + ((vtReceiptDetail.getCost() != null ? vtReceiptDetail.getCost() : 0) - (vtReceiptDetail.getTienDaTra() != null ? vtReceiptDetail.getTienDaTra()  : 0));
                if (vtReceiptDetail.getPaymentType() != null && vtReceiptDetail.getPaymentType() == 1) {
                    vtReceiptDetail.setSoTienPhaiThu("Đã thanh toán");
                } else if (vtReceiptDetail.getPaymentType() != null && vtReceiptDetail.getPaymentType() == 3) {
                    vtReceiptDetail.setSoTienPhaiThu("Công nợ");
                } else if (vtReceiptDetail.getPaymentType() != null && vtReceiptDetail.getPaymentType() == 2) {
                    if(vtReceiptDetail.getCost()!=null){
                        tongTien = tongTien + ((vtReceiptDetail.getCost() != null ? vtReceiptDetail.getCost() : 0) - (vtReceiptDetail.getTienDaTra() != null ? vtReceiptDetail.getTienDaTra()  : 0));
                        vtReceiptDetail.setSoTienPhaiThu(String.format("%,.0f", new Double(((vtReceiptDetail.getCost() != null ? vtReceiptDetail.getCost() : 0) - (vtReceiptDetail.getTienDaTra() != null ? vtReceiptDetail.getTienDaTra()  : 0)))));
                    }
                }
            }
            vtToaHang.setSoLuong(soLuong);
            vtToaHang.setTongTien(tongTien);
            vTGoodsReceiptForm.setVtToaHang(vtToaHang);
            vTGoodsReceiptForm.setVtReceiptDetail(items);
            return vTGoodsReceiptForm;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer getMaxId() {
        Integer maxId = 0;
        try {
            Query query = entityManager.createQuery(" select max(r.id) from VtToaHang r ");
            maxId = (Integer) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maxId;
    }

}
