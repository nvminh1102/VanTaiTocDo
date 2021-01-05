package com.osp.web.dao;

import com.osp.common.DateUtils;
import com.osp.common.PagingResult;
import com.osp.model.NhaXe;
import com.osp.model.User;
import com.osp.model.VtGoodsReceipt;
import com.osp.model.VtGoodsReceiptDetail;
import com.osp.model.VtInPhieuThu;
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
public class InPhieuThuDAOImpl implements InPhieuThuDAO {

    @PersistenceContext(unitName = "appAdmin")
    @Qualifier(value = "transactionManager")
    private EntityManager entityManager;

    @Override
    public Optional<PagingResult> search(VtInPhieuThu item, PagingResult page) {
        int offset = 0;
        if (page.getPageNumber() > 0) {
            offset = (page.getPageNumber() - 1) * page.getNumberPerPage();
        }
        try {
//            String strWhere = "";
//            if (item.getReceiptCode() != null && !item.getReceiptCode().trim().equals("")) {
//                strWhere = strWhere + " and upper(pt.receipt_code) like :receiptCode ";
//            }
//            if (item.getFromPushStock() != null) {
//                strWhere = strWhere + " and pt.date_push_Stock >= :fromPushStock ";
//            }
//            if (item.getToPushStock() != null) {
//                strWhere = strWhere + " and pt.date_push_Stock <= :toPushStock ";
//            }
//            if (item.getNameStock() != null && !item.getNameStock().trim().equals("")) {
//                strWhere = strWhere + " and upper(pt.name_Stock) = :nameStock ";
//            }
//            Query queryCount = entityManager.createNativeQuery(" select count(pt.id) from vt_phieu_thu pt where 1=1 " + strWhere);
//            if (item.getReceiptCode() != null && !item.getReceiptCode().trim().equals("")) {
//                queryCount.setParameter("receiptCode", "%" + item.getReceiptCode().trim().toUpperCase() + "%");
//            }
//            if (item.getFromPushStock() != null) {
//                queryCount.setParameter("fromPushStock", item.getFromPushStock());
//            }
//            if (item.getToPushStock() != null) {
//                queryCount.setParameter("toPushStock", DateUtils.addDays(item.getToPushStock(), 1));
//            }
//            if (item.getNameStock() != null && !item.getNameStock().trim().equals("")) {
//                queryCount.setParameter("nameStock", item.getNameStock());
//            }
//            List resultList = queryCount.getResultList();
//            if (resultList != null && resultList.size() > 0) {
//                BigInteger count = (BigInteger) resultList.get(0);
//                if (count != null && count.longValue() > 0) {
//                    List<VtGoodsReceiptView> list = new ArrayList<>();
//                    Query queryAll = entityManager.createNativeQuery(" select pt.id, pt.receipt_code, pt.delivery_partner_id, pt.receive_partner_id, pt.name_Stock, pt.date_receipt, "
//                            + " pt.date_push_Stock, pt.payer, pt.employee, pt.status, pt.gen_date, pt.created_by, pt.last_update, pt.updated_by,"
//                            + " b.USER_NAME as ten_nguoi_gui, b.MOBILE as mobile_nguoi_Gui, b.address as dia_chi_nguoi_gui, "
//                            + " c.USER_NAME as ten_nguoi_nhan, c.MOBILE as mobile_nguoi_nhan, c.address as dia_chi_nguoi_nhan "
//                            + " from vt_phieu_thu pt left join vt_partner b on pt.delivery_partner_id = b.ID  left join vt_partner c on pt.receive_partner_id = c.ID  " + strWhere + " order by pt.date_push_Stock desc ", VtPhieuThuView.class);
//                    if (item.getReceiptCode() != null && !item.getReceiptCode().trim().equals("")) {
//                        queryAll.setParameter("receiptCode", "%" + item.getReceiptCode().trim().toUpperCase() + "%");
//                    }
//                    if (item.getReceiptCode() != null && !item.getReceiptCode().trim().equals("")) {
//                        queryCount.setParameter("receiptCode", "%" + item.getReceiptCode().trim().toUpperCase() + "%");
//                    }
//                    if (item.getFromPushStock() != null) {
//                        queryCount.setParameter("fromPushStock", item.getFromPushStock());
//                    }
//                    if (item.getToPushStock() != null) {
//                        queryCount.setParameter("toPushStock", DateUtils.addDays(item.getToPushStock(), 1));
//                    }
//                    if (item.getNameStock() != null && !item.getNameStock().trim().equals("")) {
//                        queryCount.setParameter("nameStock", item.getNameStock());
//                    }
//                    list = queryAll.setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
//                    page.setItems(list);
//                    page.setRowCount(count.longValue());
//                }
//            }
            return Optional.of(page);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean add(VtInPhieuThu vtInPhieuThu, User user) {
        try {
            if (vtInPhieuThu != null && vtInPhieuThu.getId() != null) {
                vtInPhieuThu.setUpdatedBy(user.getUsername());
                vtInPhieuThu.setLastUpdate(new Date());
                entityManager.merge(vtInPhieuThu);
                entityManager.flush();
            } else {
                vtInPhieuThu.setGenDate(new Date());
                vtInPhieuThu.setLastUpdate(new Date());
                vtInPhieuThu.setCreatedBy(user.getUsername());
                vtInPhieuThu.setUpdatedBy(user.getUsername());
                entityManager.persist(vtInPhieuThu);
                entityManager.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Boolean delete(Integer id, User user, String ip) {
        try {
            if (id != null) {
                Query query = entityManager.createQuery("delete from VtInPhieuThu a WHERE a.id=:id").setParameter("id", id);
                query.executeUpdate();
                entityManager.flush();
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public VtInPhieuThu findById(Integer id) {
        VtInPhieuThu vtInPhieuThu = new VtInPhieuThu();
        try {
            vtInPhieuThu = entityManager.find(VtInPhieuThu.class, id);
            return vtInPhieuThu;
        } catch (Exception e) {
            e.printStackTrace();
            return vtInPhieuThu;
        }
    }

    @Override
    public Integer getMaxId() {
        Integer maxId = 0;
        try {
            Query query = entityManager.createQuery(" select max(r.id) from VtInPhieuThu r ");
            maxId = (Integer) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maxId;
    }

}
