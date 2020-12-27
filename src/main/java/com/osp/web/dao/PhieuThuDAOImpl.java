package com.osp.web.dao;

import com.osp.common.DateUtils;
import com.osp.common.PagingResult;
import com.osp.model.User;
import com.osp.model.VtGoodsReceipt;
import com.osp.model.VtGoodsReceiptDetail;
import com.osp.model.VtPhieuThu;
import com.osp.model.VtPhieuThuDetail;
import com.osp.model.VtReceipt;
import com.osp.model.view.VTGoodsReceiptForm;
import com.osp.model.view.VtGoodsReceiptBO;
import com.osp.model.view.VtGoodsReceiptView;
import com.osp.model.view.VtPhieuThuView;
import com.osp.model.view.VtReceiptView;
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
public class PhieuThuDAOImpl implements PhieuThuDAO {

    @PersistenceContext(unitName = "appAdmin")
    @Qualifier(value = "transactionManager")
    private EntityManager entityManager;

    @Override
    public Optional<PagingResult> search(VtPhieuThuView item, PagingResult page) {
        int offset = 0;
        if (page.getPageNumber() > 0) {
            offset = (page.getPageNumber() - 1) * page.getNumberPerPage();
        }
        try {
            String strWhere = "";
            if (item.getReceiptCode() != null && !item.getReceiptCode().trim().equals("")) {
                strWhere = strWhere + " and upper(pt.receipt_code) like :receiptCode ";
            }
            if (item.getFromPushStock() != null) {
                strWhere = strWhere + " and pt.date_push_Stock >= :fromPushStock ";
            }
            if (item.getToPushStock() != null) {
                strWhere = strWhere + " and pt.date_push_Stock <= :toPushStock ";
            }
            if (item.getNameStock() != null && !item.getNameStock().trim().equals("")) {
                strWhere = strWhere + " and upper(pt.name_Stock) = :nameStock ";
            }
            Query queryCount = entityManager.createNativeQuery(" select count(pt.id) from vt_phieu_thu pt where 1=1 " + strWhere);
            if (item.getReceiptCode() != null && !item.getReceiptCode().trim().equals("")) {
                queryCount.setParameter("receiptCode", "%" + item.getReceiptCode().trim().toUpperCase() + "%");
            }
            if (item.getFromPushStock() != null) {
                queryCount.setParameter("fromPushStock", item.getFromPushStock());
            }
            if (item.getToPushStock() != null) {
                queryCount.setParameter("toPushStock", DateUtils.addDays(item.getToPushStock(), 1));
            }
            if (item.getNameStock() != null && !item.getNameStock().trim().equals("")) {
                queryCount.setParameter("nameStock", item.getNameStock());
            }
            List resultList = queryCount.getResultList();
            if (resultList != null && resultList.size() > 0) {
                BigInteger count = (BigInteger) resultList.get(0);
                if (count != null && count.longValue() > 0) {
                    List<VtGoodsReceiptView> list = new ArrayList<>();
                    Query queryAll = entityManager.createNativeQuery(" select pt.id, pt.receipt_code, pt.delivery_partner_id, pt.receive_partner_id, pt.name_Stock, pt.date_receipt, "
                            + " pt.date_push_Stock, pt.payer, pt.employee, pt.status, pt.gen_date, pt.created_by, pt.last_update, pt.updated_by,"
                            + " b.USER_NAME as ten_nguoi_gui, b.MOBILE as mobile_nguoi_Gui, b.address as dia_chi_nguoi_gui, "
                            + " c.USER_NAME as ten_nguoi_nhan, c.MOBILE as mobile_nguoi_nhan, c.address as dia_chi_nguoi_nhan "
                            + " from vt_phieu_thu pt left join vt_partner b on pt.delivery_partner_id = b.ID left join vt_partner c on pt.receive_partner_id = c.ID " + strWhere + " order by pt.date_push_Stock desc ", VtPhieuThuView.class);
                    if (item.getReceiptCode() != null && !item.getReceiptCode().trim().equals("")) {
                        queryAll.setParameter("receiptCode", "%" + item.getReceiptCode().trim().toUpperCase() + "%");
                    }
                    if (item.getReceiptCode() != null && !item.getReceiptCode().trim().equals("")) {
                        queryCount.setParameter("receiptCode", "%" + item.getReceiptCode().trim().toUpperCase() + "%");
                    }
                    if (item.getFromPushStock() != null) {
                        queryCount.setParameter("fromPushStock", item.getFromPushStock());
                    }
                    if (item.getToPushStock() != null) {
                        queryCount.setParameter("toPushStock", DateUtils.addDays(item.getToPushStock(), 1));
                    }
                    if (item.getNameStock() != null && !item.getNameStock().trim().equals("")) {
                        queryCount.setParameter("nameStock", item.getNameStock());
                    }
                    list = queryAll.setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
                    page.setItems(list);
                    page.setRowCount(count.longValue());
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
            VtPhieuThuView vtPhieuThuView = vTGoodsReceiptForm.getVtPhieuThuView();
            VtPhieuThu vtPhieuThu = new VtPhieuThu(vtPhieuThuView);
            if (vtPhieuThu != null && vtPhieuThu.getId() != null) {
                Query query = entityManager.createQuery("delete from VtPhieuThuDetail a WHERE a.phieuThuId=:phieuThuId").setParameter("phieuThuId", vtPhieuThu.getId());
                query.executeUpdate();
                vtPhieuThu.setUpdatedBy(user.getUsername());
                vtPhieuThu.setLastUpdate(new Date());
                entityManager.merge(vtPhieuThu);
                List<VtReceiptView> vtReceiptViews = vTGoodsReceiptForm.getVtReceiptViews();
                for (VtReceiptView bo : vtReceiptViews) {
                    VtPhieuThuDetail vtPhieuThuDetail = new VtPhieuThuDetail();
                    vtPhieuThuDetail.setPhieuThuId(vtPhieuThu.getId());
                    vtPhieuThuDetail.setCreatedBy(vtPhieuThu.getCreatedBy());
                    vtPhieuThuDetail.setUpdatedBy(vtPhieuThu.getUpdatedBy());
                    vtPhieuThuDetail.setReceiptId(bo.getId().intValue());
                    entityManager.persist(vtPhieuThuDetail);
                }
                entityManager.flush();
            } else {
                vtPhieuThu.setGenDate(new Date());
                vtPhieuThu.setLastUpdate(new Date());
                vtPhieuThu.setCreatedBy(user.getUsername());
                vtPhieuThu.setUpdatedBy(user.getUsername());
                entityManager.persist(vtPhieuThu);
                List<VtReceiptView> vtReceiptViews = vTGoodsReceiptForm.getVtReceiptViews();
                for (VtReceiptView bo : vtReceiptViews) {
                    VtPhieuThuDetail vtPhieuThuDetail = new VtPhieuThuDetail();
                    vtPhieuThuDetail.setPhieuThuId(vtPhieuThu.getId());
                    vtPhieuThuDetail.setCreatedBy(vtPhieuThu.getCreatedBy());
                    vtPhieuThuDetail.setUpdatedBy(vtPhieuThu.getUpdatedBy());
                    vtPhieuThuDetail.setReceiptId(bo.getId().intValue());
                    entityManager.persist(vtPhieuThuDetail);
                }
                entityManager.flush();
            }
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
                Query querydetail = entityManager.createQuery("delete from VtPhieuThuDetail a WHERE a.phieuThuId=:phieuThuId").setParameter("phieuThuId", id);
                querydetail.executeUpdate();
                Query query = entityManager.createQuery("delete from VtPhieuThu a WHERE a.id=:id").setParameter("id", id);
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
    public VTGoodsReceiptForm getVtPhieuThuFormById(Integer id) {
        VTGoodsReceiptForm vTGoodsReceiptForm = new VTGoodsReceiptForm();
        try {
            Query queryAll = entityManager.createNativeQuery(" select pt.receipt_code, pt.delivery_partner_id, pt.receive_partner_id, pt.name_Stock, pt.date_receipt, "
                            + " pt.date_push_Stock, pt.payer, pt.employee, pt.status, pt.gen_date, pt.created_by, pt.last_update, pt.updated_by,"
                            + " b.USER_NAME as ten_nguoi_gui, b.MOBILE as mobile_nguoi_Gui, b.address as dia_chi_nguoi_gui, "
                            + " c.USER_NAME as ten_nguoi_nhan, c.MOBILE as mobile_nguoi_nhan, c.address as dia_chi_nguoi_nhan "
                            + " from vt_phieu_thu pt left join vt_partner b on pt.delivery_partner_id = b.ID left join vt_partner c on pt.receive_partner_id = c.ID and pt.id = :id  ", VtPhieuThuView.class);
            queryAll.setParameter("id", id);

            VtPhieuThuView vtPhieuThuView = (VtPhieuThuView) queryAll.getSingleResult();
            vTGoodsReceiptForm.setVtPhieuThuView(vtPhieuThuView);
            StringBuffer sqlBuffer = new StringBuffer("SELECT t.ID,t.receipt_code,t.date_receipt,t.name_Stock,t.nha_xe,t.bien_so,t.employee,b.FULL_NAME as ten_nguoi_gui,b.address as dia_chi_nguoi_gui,c.FULL_NAME as ten_nguoi_nhan,c.address as dia_chi_nguoi_nhan, "
                    + " c.MOBILE as mobile_nguoi_nhan  "
                    + "from Vt_Goods_Receipt_Detail grd inner join vt_receipt t on grd.receipt_Id = t.id left join vt_partner b on t.delivery_partner_id = b.ID left join vt_partner c on t.receive_partner_id = c.ID "
                    + " where grd.goods_receipt_id = :goodsreceiptid ");

            Query queryDetail = entityManager.createNativeQuery(sqlBuffer.toString(), VtReceiptView.class);
            queryDetail.setParameter("goodsreceiptid", id);
            List<VtReceiptView> vtReceiptViews = queryDetail.getResultList();
            vTGoodsReceiptForm.setVtReceiptViews(vtReceiptViews);
            return vTGoodsReceiptForm;
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
            Query query = entityManager.createQuery(" select max(r.id) from VtPhieuThu r ");
            maxId = (Integer) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maxId;
    }

}
