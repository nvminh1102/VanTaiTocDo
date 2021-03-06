package com.osp.web.dao;

import com.osp.common.Constants;
import com.osp.common.DateUtils;
import com.osp.common.PagingResult;
import com.osp.model.AdmLogData;
import com.osp.model.User;
import com.osp.model.VtArea;
import com.osp.model.VtGomDonNhan;
import com.osp.model.VtGomDonNhanDetail;
import com.osp.model.VtReceiptDetail;
import com.osp.model.view.VTGoodsReceiptForm;
import com.osp.model.view.VtReceiptView;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(value = "transactionManager")
public class GomDonHangDAOImpl implements GomDonHangDAO {

    @PersistenceContext(unitName = "appAdmin")
    @Qualifier(value = "transactionManager")
    private EntityManager entityManager;

    @Override
    public Optional<PagingResult> search(String bienSo, Date fromGenDate, Date toGenDate, PagingResult page) {
        int offset = 0;
        if (page.getPageNumber() > 0) {
            offset = (page.getPageNumber() - 1) * page.getNumberPerPage();
        }
        try {
            String strWhere = "";
            if (bienSo != null && !bienSo.trim().equals("")) {
                strWhere = strWhere + " and r.bienSo = :bienSo ";
            }
            if (fromGenDate != null) {
                strWhere = strWhere + " and r.genDate >= :fromGenDate ";
            }
            if (toGenDate != null) {
                strWhere = strWhere + " and r.genDate <= :toGenDate ";
            }
            Query queryCount = entityManager.createQuery(" select count(r.id) from VtGomDonNhan r where 1=1 " + strWhere);
            if (bienSo != null && !bienSo.trim().equals("")) {
                queryCount.setParameter("bienSo", bienSo.trim());
            }
            if (fromGenDate != null) {
                queryCount.setParameter("fromGenDate", fromGenDate);
            }
            if (toGenDate != null) {
                queryCount.setParameter("toGenDate", DateUtils.addDays(toGenDate, 1));
            }

            List resultList = queryCount.getResultList();
            if (resultList != null && resultList.size() > 0) {
                Long count = (Long) resultList.get(0);
                if (count != null && count.compareTo(0L) > 0) {
                    List<VtGomDonNhan> list = new ArrayList<>();
                    Query queryAll = entityManager.createQuery("select r from VtGomDonNhan r where 1=1 " + strWhere + " order by r.genDate desc ", VtGomDonNhan.class);
                    if (bienSo != null && !bienSo.trim().equals("")) {
                        queryAll.setParameter("bienSo", bienSo.trim());
                    }
                    if (fromGenDate != null) {
                        queryAll.setParameter("fromGenDate", fromGenDate);
                    }
                    if (toGenDate != null) {
                        queryAll.setParameter("toGenDate", DateUtils.addDays(toGenDate, 1));
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
    public Boolean add(VTGoodsReceiptForm vTGoodsReceiptForm, User user, HttpServletRequest request) {
        try {
            VtGomDonNhan vtGomDonNhan = vTGoodsReceiptForm.getVtGomDonNhan();
            vtGomDonNhan.setUpdatedBy(user.getUsername());
            vtGomDonNhan.setLastUpdate(new Date());
            if (vtGomDonNhan.getId() != null) {
                List<VtGomDonNhanDetail> vtGomDonNhanDetail = new ArrayList<>();

                Query queryDetail = entityManager.createQuery("SELECT a from VtGomDonNhanDetail a where a.vtGomDonNhanId=:vtGomDonNhanId ").setParameter("vtGomDonNhanId", vtGomDonNhan.getId());
                vtGomDonNhanDetail = queryDetail.getResultList();

                Query queryAll = entityManager.createQuery("select r from VtGomDonNhan r where r.id = :id ").setParameter("id", vtGomDonNhan.getId());
                VtGomDonNhan oldData = (VtGomDonNhan) queryAll.getSingleResult();

                Query query = entityManager.createQuery("delete from VtGomDonNhanDetail a WHERE a.vtGomDonNhanId=:vtGomDonNhanId").setParameter("vtGomDonNhanId", vtGomDonNhan.getId());
                query.executeUpdate();
                entityManager.merge(vtGomDonNhan);

                // insert log data
                AdmLogData admLogData = new AdmLogData(vtGomDonNhanDetail, null, user.getUsername(), request, "/managerVanTai/gom-don-hang/add", Constants.action.DELETE);
                entityManager.persist(admLogData);
                // insert log data
                AdmLogData admLogData2 = new AdmLogData(oldData, vtGomDonNhan, user.getUsername(), request, "/managerVanTai/gom-don-hang/add", Constants.action.UPDATE);
                entityManager.persist(admLogData2);

            } else {
                vtGomDonNhan.setGenDate(new Date());
                vtGomDonNhan.setCreatedBy(user.getUsername());
                entityManager.persist(vtGomDonNhan);
                // insert log data
                AdmLogData admLogData2 = new AdmLogData(null, vtGomDonNhan, user.getUsername(), request, "/managerVanTai/gom-don-hang/add", Constants.action.INSERT);
                entityManager.persist(admLogData2);
            }
            List<VtReceiptView> vtReceiptViews = vTGoodsReceiptForm.getVtReceiptViews();
            for (VtReceiptView bo : vtReceiptViews) {
                VtGomDonNhanDetail vtGomDonNhanDetail = new VtGomDonNhanDetail();
                vtGomDonNhanDetail.setVtGomDonNhanId(vtGomDonNhan.getId());
                vtGomDonNhanDetail.setCreatedBy(vtGomDonNhan.getCreatedBy());
                vtGomDonNhanDetail.setUpdatedBy(vtGomDonNhan.getUpdatedBy());
                vtGomDonNhanDetail.setLastUpdate(vtGomDonNhan.getLastUpdate());
                vtGomDonNhanDetail.setGenDate(vtGomDonNhan.getGenDate());
                vtGomDonNhanDetail.setReceiptId(bo.getId().intValue());
//                Query queryUpdateHangHoa = entityManager.createQuery("update VtReceipt a set a.status =3 WHERE a.id=:id").setParameter("id", bo.getId().intValue());
//                queryUpdateHangHoa.executeUpdate();
                entityManager.persist(vtGomDonNhanDetail);
                // insert log data
                AdmLogData admLogData2 = new AdmLogData(null, vtGomDonNhanDetail, user.getUsername(), request, "/managerVanTai/gom-don-hang/add", Constants.action.INSERT);
                entityManager.persist(admLogData2);
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
    public Boolean delete(Integer id, User user, String ip, HttpServletRequest request) {
        try {
            if (id != null) {
                List<VtGomDonNhanDetail> vtGomDonNhanDetail = new ArrayList<>();

                Query queryDetail = entityManager.createQuery("SELECT a from VtGomDonNhanDetail a where a.vtGomDonNhanId=:vtGomDonNhanId ").setParameter("vtGomDonNhanId", id);
                vtGomDonNhanDetail = queryDetail.getResultList();
                
                Query queryAll = entityManager.createQuery("select r from VtGomDonNhan r where r.id = :id ").setParameter("id", id);
                VtGomDonNhan oldData = (VtGomDonNhan) queryAll.getSingleResult();
                
                Query querydetail = entityManager.createQuery("delete from VtGomDonNhanDetail a WHERE a.vtGomDonNhanId=:vtGomDonNhanId").setParameter("vtGomDonNhanId", id);
                querydetail.executeUpdate();
                Query query = entityManager.createQuery("delete from VtGomDonNhan a WHERE a.id=:id").setParameter("id", id);
                query.executeUpdate();
                entityManager.flush();
                // insert log data
                AdmLogData admLogData2 = new AdmLogData(null, vtGomDonNhanDetail, user.getUsername(), request, "/managerVanTai/gom-don-hang/add", Constants.action.DELETE);
                entityManager.persist(admLogData2);
                // insert log data
                AdmLogData admLogData = new AdmLogData(null, oldData, user.getUsername(), request, "/managerVanTai/gom-don-hang/add", Constants.action.DELETE);
                entityManager.persist(admLogData);
                
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
            Query queryAll = entityManager.createQuery("select r from VtGomDonNhan r where r.id = :id ");
            queryAll.setParameter("id", id);

            VtGomDonNhan vtGomDonNhan = (VtGomDonNhan) queryAll.getSingleResult();
            String sqlBuffer = "SELECT t.ID,t.receipt_code,t.date_receipt,t.name_Stock,t.nha_xe,t.bien_so,t.employee,b.FULL_NAME as ten_nguoi_gui,b.address as dia_chi_nguoi_gui,c.FULL_NAME as ten_nguoi_nhan,c.address as dia_chi_nguoi_nhan, "
                    + " c.MOBILE as mobile_nguoi_nhan, t.payer, t.payment_type , t.tien_da_tra , (select SUM(d.cost) FROM vt_receipt_detail d WHERE t.id = d.receipt_id) AS tong_tien , (select SUM(d.numbers) FROM vt_receipt_detail d WHERE t.id = d.receipt_id) AS so_luong "
                    + " from vt_gom_don_nhan_detail thd inner join vt_receipt t on thd.receipt_Id = t.id left join vt_partner b on t.delivery_partner_id = b.ID   left join vt_partner c on t.receive_partner_id = c.ID   "
                    + " where thd.vt_gom_don_nhan_id = :vtGomDonNhanId ";

            Query queryDetail = entityManager.createNativeQuery(sqlBuffer);
            queryDetail.setParameter("vtGomDonNhanId", id);
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
            vtGomDonNhan.setSoLuong(soLuong);
            vtGomDonNhan.setTongTien(tongTien);
            vTGoodsReceiptForm.setVtGomDonNhan(vtGomDonNhan);
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
            Query queryAll = entityManager.createQuery("select r from VtGomDonNhan r where r.id = :id ");
            queryAll.setParameter("id", id);

            VtGomDonNhan vtGomDonNhan = (VtGomDonNhan) queryAll.getSingleResult();
            String sqlBuffer = " SELECT t.ID,t.receipt_code,t.date_receipt,t.name_Stock,t.nha_xe,t.bien_so,t.employee,b.FULL_NAME as ten_nguoi_gui,b.address as dia_chi_nguoi_gui,c.FULL_NAME as ten_nguoi_nhan,c.address as dia_chi_nguoi_nhan,  "
                    + " c.MOBILE as mobile_nguoi_nhan, t.payer, t.payment_type , t.tien_da_tra , rd.cost AS thanh_tien , rd.numbers AS so_luong , rd.name, rd.note , d.so_hop_dong , "
                    + " (select ma_phieu_thu from vt_in_phieu_thu where id  = (select max(id) from vt_in_phieu_thu ipt where  t.id = ipt.receipt_id) ) AS ma_phieu_thu "
                    + "  from vt_phieu_giao_hang_detail thd  "
                    + " inner join vt_receipt t on thd.receipt_Id = t.id  "
                    + " inner join vt_receipt_detail rd on t.id = rd.receipt_Id   "
                    + " left join vt_partner b on t.delivery_partner_id = b.ID   "
                    + " left join vt_partner c on t.receive_partner_id = c.ID  "
                    + " left join vt_partner d on t.nguoi_thanh_toan_id = d.ID "
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
                row.setName(record[17] == null ? null : (String) record[17]);
                row.setNote(record[18] == null ? null : (String) record[18]);
                row.setSoHopDong(record[19] == null ? null : (String) record[19]);
                row.setMaPhieuThu(record[20] == null ? null : (String) record[20]);
                vtReceiptViews.add(row);
            });
            for (VtReceiptView vtReceiptView : vtReceiptViews) {
                soLuong = soLuong + vtReceiptView.getSoLuong();
                tongTien = tongTien + ((vtReceiptView.getTongTien() != null ? vtReceiptView.getTongTien().intValue() : 0) - (vtReceiptView.getTienDaTra() != null ? vtReceiptView.getTienDaTra().intValue() : 0));
                if (vtReceiptView.getPaymentType() == 1) {
                    vtReceiptView.setSoTienPhaiThu("Đã thanh toán");
                } else if (vtReceiptView.getPaymentType() == 3) {
                    vtReceiptView.setSoTienPhaiThu("Công nợ");
                } else if (vtReceiptView.getPaymentType() == 2) {
                    if (vtReceiptView.getTongTien() != null) {
                        vtReceiptView.setSoTienPhaiThu(String.format("%,.0f", new Double(vtReceiptView.getTongTien())));
                    }
                }
            }
            vtGomDonNhan.setSoLuong(soLuong);
            vtGomDonNhan.setTongTien(tongTien);
            vTGoodsReceiptForm.setVtGomDonNhan(vtGomDonNhan);
            vTGoodsReceiptForm.setVtReceiptViews(vtReceiptViews);
            return vTGoodsReceiptForm;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<VtReceiptDetail> getPhieuNhanHang(Integer id) {
        List<Object[]> db = new ArrayList<>();
        List<VtReceiptDetail> items = new ArrayList<>();
        try {
            String sqlBuffer = " select t.id, t.receipt_code, b.FULL_NAME as ten_nguoi_gui,  c.FULL_NAME as ten_nguoi_nhan,  c.address as dia_chi_nguoi_nhan, "
                    + " c.MOBILE as mobile_nguoi_nhan, td.name, td.numbers, td.cost , td.sizes, "
                    + " td.weight, td.note , t.payer, b.address as diaChiGui, b.MOBILE as sdtGui, "
                    + " DATE_FORMAT(t.gen_date, '%d-%m-%Y') as strGenDate, t.name_Stock, case when t.payment_type = 1 then 'Trả trước' when t.payment_type = 2 then 'Trả sau' when t.payment_type = 3 then 'Công nợ' else '' end as hinhthucthanhtoan "
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
                row.setSizes(record[9] == null ? null : (String) record[9]);

                row.setWeight(record[10] == null ? null : (String) record[10]);
                row.setNote(record[11] == null ? null : (String) record[11]);
                row.setNguoiThanhToan(record[12] == null ? null : (String) record[12]);
                row.setDiaChiNguoiGui(record[13] == null ? null : (String) record[13]);
                row.setSdtNguoiGui(record[14] == null ? null : (String) record[14]);

                row.setStrGenDate(record[15] == null ? null : (String) record[15]);
                row.setStockName(record[16] == null ? null : (String) record[16]);
                row.setSoTienPhaiThu(record[17] == null ? null : (String) record[17]);
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
            Query query = entityManager.createQuery(" select max(r.id) from VtGomDonNhan r ");
            maxId = (Integer) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maxId;
    }

}
