package com.osp.web.dao;

import com.osp.common.PagingResult;
import com.osp.model.User;
import com.osp.model.VtGoodsReceipt;
import com.osp.model.VtGoodsReceiptDetail;
import com.osp.model.VtReceipt;
import com.osp.model.view.VTGoodsReceiptForm;
import com.osp.model.view.VtGoodsReceiptBO;
import com.osp.model.view.VtGoodsReceiptView;
import com.osp.model.view.VtReceiptView;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class PhieuNhanHangDAOImpl implements PhieuNhanHangDAO {

    @PersistenceContext(unitName = "appAdmin")
    @Qualifier(value = "transactionManager")
    private EntityManager entityManager;

    @Override
    public VtGoodsReceipt add(VtGoodsReceipt item) {
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
    public Optional<PagingResult> search(VtGoodsReceipt item, PagingResult page) {
        int offset = 0;
        if (page.getPageNumber() > 0) {
            offset = (page.getPageNumber() - 1) * page.getNumberPerPage();
        }
        try {
            String strWhere = "";
            if (item.getReceiptCode() != null && !item.getReceiptCode().trim().equals("")) {
                strWhere = strWhere + " and upper(r.receipt_code) like :receiptCode ";
            }
            if (item.getFromDelivery() != null) {
                strWhere = strWhere + " and r.date_delivery >= :fromDelivery ";
            }
            if (item.getToDelivery() != null) {
                strWhere = strWhere + " and r.date_delivery <= :toDelivery ";
            }
            if (item.getFromReceive() != null) {
                strWhere = strWhere + " and r.date_receive >= :fromReceipt ";
            }
            if (item.getToReceive() != null) {
                strWhere = strWhere + " and r.date_receive <= :toReceipt ";
            }
            if (item.getLoaiXe() != null && !item.getLoaiXe().trim().equals("")) {
                strWhere = strWhere + " and r.loai_xe = :loaiXe ";
            }
            if (item.getBienSo() != null && !item.getBienSo().trim().equals("")) {
                strWhere = strWhere + " and r.bien_so = :bienSo ";
            }
            Query queryCount = entityManager.createNativeQuery(" select count(r.id) from vt_goods_receipt r where 1=1 " + strWhere);
            if (item.getReceiptCode() != null && !item.getReceiptCode().trim().equals("")) {
                queryCount.setParameter("receiptCode", "%" + item.getReceiptCode().trim().toUpperCase() + "%");
            }
            if (item.getFromDelivery() != null) {
                queryCount.setParameter("fromDelivery", item.getFromDelivery());
            }
            if (item.getToDelivery() != null) {
                queryCount.setParameter("toDelivery", item.getToDelivery());
            }
            if (item.getFromReceive() != null) {
                queryCount.setParameter("fromReceipt", item.getFromReceive());
            }
            if (item.getToReceive() != null) {
                queryCount.setParameter("toReceipt", item.getToReceive());
            }
            if (item.getLoaiXe() != null && !item.getLoaiXe().trim().equals("")) {
                queryCount.setParameter("loaiXe", item.getLoaiXe());
            }
            if (item.getBienSo() != null && !item.getBienSo().trim().equals("")) {
                queryCount.setParameter("bienSo", item.getBienSo());
            }
            List resultList = queryCount.getResultList();
            if(resultList!=null && resultList.size()>0){
                BigInteger count = (BigInteger) resultList.get(0);
            if (count!=null && count.longValue()>0) {
                List<VtGoodsReceiptView> list = new ArrayList<>();
                Query queryAll = entityManager.createNativeQuery("select r.id, r.receipt_code, r.truck_partner_id, r.bien_so, r.loai_xe, r.date_delivery, r.date_receive, r.gen_date, r.created_by, r.last_update, r.updated_by, "
                        + " (select FULL_NAME from vt_partner where id = r.truck_partner_id) as truckPartnerName from vt_goods_receipt r where 1=1 " + strWhere + " order by r.date_receive desc ", VtGoodsReceiptView.class);
                if (item.getReceiptCode() != null && !item.getReceiptCode().trim().equals("")) {
                    queryAll.setParameter("receiptCode", "%" + item.getReceiptCode().trim().toUpperCase() + "%");
                }
                if (item.getFromDelivery() != null) {
                    queryAll.setParameter("fromDelivery", item.getFromDelivery());
                }
                if (item.getToDelivery() != null) {
                    queryAll.setParameter("toDelivery", item.getToDelivery());
                }
                if (item.getFromReceive() != null) {
                    queryAll.setParameter("fromReceipt", item.getFromReceive());
                }
                if (item.getToReceive() != null) {
                    queryAll.setParameter("toReceipt", item.getToReceive());
                }
                if (item.getLoaiXe() != null && !item.getLoaiXe().trim().equals("")) {
                    queryAll.setParameter("loaiXe", item.getLoaiXe());
                }
                if (item.getBienSo() != null && !item.getBienSo().trim().equals("")) {
                    queryAll.setParameter("bienSo", item.getBienSo());
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
            VtGoodsReceiptBO vtGoodsReceiptBO = vTGoodsReceiptForm.getVtGoodsReceiptBO();
            VtGoodsReceipt vtGoodsReceipt = new VtGoodsReceipt(vtGoodsReceiptBO);
            if (vtGoodsReceipt != null && vtGoodsReceipt.getId() != null) {
                Query query = entityManager.createQuery("delete from VtGoodsReceiptDetail a WHERE a.goodsreceiptid=:goodsreceiptid").setParameter("goodsreceiptid", vtGoodsReceipt.getId());
                query.executeUpdate();
                vtGoodsReceipt.setUpdatedBy(user.getUsername());
                entityManager.merge(vtGoodsReceipt);
                List<VtReceiptView> vtReceiptViews = vTGoodsReceiptForm.getVtReceiptViews();
                for (VtReceiptView bo : vtReceiptViews) {
                    VtGoodsReceiptDetail vtGoodsReceiptDetail = new VtGoodsReceiptDetail();
                    vtGoodsReceiptDetail.setGoodsreceiptid(vtGoodsReceipt.getId());
                    vtGoodsReceiptDetail.setCreatedBy(vtGoodsReceipt.getCreatedBy());
                    vtGoodsReceiptDetail.setUpdatedBy(vtGoodsReceipt.getUpdatedBy());
                    vtGoodsReceiptDetail.setReceiptId(bo.getId().intValue());
                    entityManager.persist(vtGoodsReceiptDetail);
                }
                entityManager.flush();
            } else {
                vtGoodsReceipt.setCreatedBy(user.getUsername());
                vtGoodsReceipt.setUpdatedBy(user.getUsername());
                List<VtReceiptView> vtReceiptViews = vTGoodsReceiptForm.getVtReceiptViews();
                entityManager.persist(vtGoodsReceipt);
                for (VtReceiptView bo : vtReceiptViews) {
                    VtGoodsReceiptDetail vtGoodsReceiptDetail = new VtGoodsReceiptDetail();
                    vtGoodsReceiptDetail.setGoodsreceiptid(vtGoodsReceipt.getId());
                    vtGoodsReceiptDetail.setCreatedBy(vtGoodsReceipt.getCreatedBy());
                    vtGoodsReceiptDetail.setUpdatedBy(vtGoodsReceipt.getUpdatedBy());
                    vtGoodsReceiptDetail.setReceiptId(bo.getId().intValue());
                    entityManager.persist(vtGoodsReceiptDetail);
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
    public VTGoodsReceiptForm getVTGoodsReceiptFormById(Integer id) {
        VTGoodsReceiptForm vTGoodsReceiptForm = new VTGoodsReceiptForm();
        try {
            Query queryAll = entityManager.createQuery("select r from VtGoodsReceipt r where r.id = :id ");
            queryAll.setParameter("id", id);

            VtGoodsReceipt vtGoodsReceipt = (VtGoodsReceipt) queryAll.getSingleResult();
            VtGoodsReceiptBO vtGoodsReceiptBO = new VtGoodsReceiptBO(vtGoodsReceipt);
            vTGoodsReceiptForm.setVtGoodsReceiptBO(vtGoodsReceiptBO);
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
            Query query = entityManager.createQuery(" select max(r.id) from VtGoodsReceipt r ");
            maxId = (Integer) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maxId;
    }

}
