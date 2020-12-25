package com.osp.web.dao;

import com.osp.common.PagingResult;
import com.osp.model.VtGoodsReceipt;
import com.osp.model.VtReceipt;
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
                strWhere = strWhere + " and upper(r.receiptCode) = :receiptCode ";
            }
            if (item.getFromDelivery() != null) {
                strWhere = strWhere + " and r.dateDelivery >= :fromDelivery ";
            }
            if (item.getToDelivery() != null) {
                strWhere = strWhere + " and r.dateDelivery <= :toDelivery ";
            }
            if (item.getFromReceive() != null) {
                strWhere = strWhere + " and r.dateReceive >= :fromReceipt ";
            }
            if (item.getToReceive() != null) {
                strWhere = strWhere + " and r.dateReceive <= :toReceipt ";
            }
            if (item.getLoaiXe() != null && !item.getLoaiXe().trim().equals("")) {
                strWhere = strWhere + " and r.loaiXe = :loaiXe ";
            }
            if (item.getBienSo() != null && !item.getBienSo().trim().equals("")) {
                strWhere = strWhere + " and r.bienSo = :bienSo ";
            }
            Long count = 0L;
            Query query = entityManager.createQuery(" select count(r.id) from VtGoodsReceipt r where 1=1 " + strWhere);
            if (item.getReceiptCode() != null && !item.getReceiptCode().trim().equals("")) {
                query.setParameter("receiptCode", "%" + item.getReceiptCode().trim() + "%");
            }
            if (item.getFromDelivery() != null) {
                query.setParameter("fromDelivery", item.getFromDelivery());
            }
            if (item.getToDelivery() != null) {
                query.setParameter("toDelivery", item.getToDelivery());
            }
            if (item.getFromReceive() != null) {
                query.setParameter("fromReceipt", item.getFromReceive());
            }
            if (item.getToReceive() != null) {
                query.setParameter("toReceipt", item.getToReceive());
            }
            if (item.getLoaiXe() != null && !item.getLoaiXe().trim().equals("")) {
                query.setParameter("loaiXe", item.getLoaiXe());
            }
            if (item.getBienSo() != null && !item.getBienSo().trim().equals("")) {
                query.setParameter("bienSo", item.getBienSo());
            }
            count = (Long) query.getSingleResult();
            if (count > 0) {
                List<VtGoodsReceipt> list = new ArrayList<>();
                Query queryAll = entityManager.createQuery("select r from VtGoodsReceipt r where 1=1 " + strWhere);
                if (item.getReceiptCode() != null && !item.getReceiptCode().trim().equals("")) {
                    queryAll.setParameter("receiptCode", "%" + item.getReceiptCode().trim() + "%");
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
            return Optional.of(page);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
