package com.osp.web.dao;

import com.osp.common.PagingResult;
import com.osp.model.view.ThongKeGiaoNhanView;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(value = "transactionManager")
public class ThongKeGiaoNhanHangDAOImpl implements ThongKeGiaoNhanHangDAO {

    @PersistenceContext(unitName = "appAdmin")
    @Qualifier(value = "transactionManager")
    private EntityManager entityManager;


    @Override
    public Optional<PagingResult> page(PagingResult page, String bienSo, String loaiXe, String hinhThucVanChuyen, Date fromDate, Date toDate, boolean isExport) {
        List<ThongKeGiaoNhanView> resultList = new ArrayList<>();
        try {
            int offset = 0;
            if (page.getPageNumber() > 0) {
                offset = (page.getPageNumber() - 1) * page.getNumberPerPage();
            }
            StringBuffer strWhere = new StringBuffer();
            strWhere.append(" where 1=1 ");
            if (bienSo != null && !"".equals(bienSo)) {
                strWhere.append(" and UPPER(bien_so) = :bienSo");
            }
            if (loaiXe != null && !"".equals(loaiXe)) {
                strWhere.append(" and UPPER(loai_xe) like :loaiXe");
            }
            if (hinhThucVanChuyen != null && !"".equals(hinhThucVanChuyen)) {
                strWhere.append(" and UPPER(hinh_thuc_van_chuyen) = :hinhThucVanChuyen");
            }
            if (fromDate != null) {
                strWhere.append(" and gen_date >= :fromGenDate");
            }
            if (toDate != null) {
                strWhere.append(" and gen_date <= :toGenDate");
            }
            StringBuffer sqlBuffer = new StringBuffer(" select * from (SELECT c.receipt_code,a.bien_so, a.nha_xe, a.loai_xe, c.ID AS id_phieu_nhan_hang, '3' AS hinh_thuc_van_chuyen, e.address AS dia_chi_nhan, f.address AS dia_chi_giao,a.gen_date, " +
                    " (SELECT sum(a.numbers) FROM vt_receipt_detail a WHERE a.receipt_id = c.id) AS so_luong "
                    + " FROM vt_phieu_giao_hang a "
                    + " INNER JOIN vt_phieu_giao_hang_detail b ON a.ID = b.phieu_giao_hang_id "
                    + " INNER JOIN vt_receipt c ON c.ID = b.receipt_id "
                    + " INNER JOIN vt_receipt_detail g ON g.receipt_id = c.id "
                    + " INNER JOIN vt_nha_xe d ON c.bien_so = d.bien_so "
                    + " INNER JOIN vt_partner e ON e.id = c.delivery_partner_id "
                    + " INNER JOIN vt_partner f ON f.id = c.receive_partner_id "
                    + " GROUP BY b.receipt_id "
                    + " UNION ALL "
                    + " SELECT c.receipt_code,a.bien_so, a.nha_xe, a.loai_xe, c.id AS id_phieu_nhan_hang, '2' AS hinh_thuc_van_chuyen, a.noi_di AS dia_chi_nhan, a.noi_den AS dia_chi_giao,a.gen_date, "
                    + " (SELECT sum(a.numbers) FROM vt_receipt_detail a WHERE a.receipt_id = c.id) AS so_luong "
                    + " FROM vt_toa_hang a "
                    + " INNER JOIN vt_toa_hang_detail b ON a.id = b.toa_hang_id "
                    + " INNER JOIN vt_receipt c ON c.id = b.receipt_id "
                    + " INNER JOIN vt_receipt_detail d ON d.receipt_id = c.id "
                    + " GROUP BY c.receipt_code "
                    + " UNION ALL "
                    + " SELECT c.receipt_code,a.bien_so, a.nha_xe, a.loai_xe, c.id AS id_phieu_nhan_hang, '1' AS hinh_thuc_van_chuyen, e.address AS dia_chi_nhan, f.address AS dia_chi_giao,a.gen_date, "
                    + " (SELECT sum(a.numbers) FROM vt_receipt_detail a WHERE a.receipt_id = c.id) AS so_luong "
                    + " FROM vt_gom_don_nhan a "
                    + " INNER JOIN vt_gom_don_nhan_detail b ON a.id = b.vt_gom_don_nhan_id "
                    + " INNER JOIN vt_receipt c ON c.id = b.receipt_id "
                    + " INNER JOIN vt_receipt_detail d ON d.receipt_id = c.id "
                    + " INNER JOIN vt_partner e ON e.id = c.delivery_partner_id "
                    + " INNER JOIN vt_partner f ON f.id = c.receive_partner_id "
                    + " GROUP BY c.receipt_code "
            );
            sqlBuffer.append(" order by GEN_DATE DESC ) as info ");
            StringBuffer sqlBufferCount = new StringBuffer("SELECT count(1) from ( ");
            sqlBufferCount.append(sqlBuffer.toString());
            sqlBufferCount.append(" ) as count");
            sqlBufferCount.append(strWhere.toString());
            sqlBuffer.append(strWhere.toString());

            Query query = entityManager.createNativeQuery(sqlBuffer.toString());

            if (page.getPageNumber() > 0) {
                offset = (page.getPageNumber() - 1) * page.getNumberPerPage();
            }

            if (bienSo != null && !"".equals(bienSo)) {
                query.setParameter("bienSo", bienSo.trim().toUpperCase());
            }
            if (loaiXe != null && !"".equals(loaiXe)) {
                query.setParameter("loaiXe", "%" + loaiXe.trim().toUpperCase() + "%");
            }
            if (hinhThucVanChuyen != null && !"".equals(hinhThucVanChuyen)) {
                query.setParameter("hinhThucVanChuyen", hinhThucVanChuyen.trim().toUpperCase());
            }
            if (fromDate != null) {
                query.setParameter("fromGenDate", fromDate);
            }
            if (toDate != null) {
                query.setParameter("toGenDate", toDate);
            }

            List<Object[]> list = new ArrayList<>();
            if (isExport) {
                list = query.getResultList();
            } else {
                list = query.setFirstResult(offset).setMaxResults(page.getNumberPerPage()).getResultList();
            }

            if (list != null && list.size() > 0) {
                list.stream().forEach((record) -> {
                    ThongKeGiaoNhanView row = new ThongKeGiaoNhanView();
                    row.setReceiptCode(record[0] == null ? null : record[0].toString());
                    row.setBienSo(record[1] == null ? null : (String) record[1]);
                    row.setNhaXe(record[2] == null ? null :  (String) record[2]);
                    row.setLoaiXe(record[3] == null ? null : (String) record[3]);
                    row.setIdPhieuNhanHang(record[4] == null ? null : (Integer) record[4]);
                    row.setHinhThucVanChuyen(record[5] == null ? null : (String) record[5]);
                    row.setDiaChiNhan(record[6] == null ? null : (String) record[6]);
                    row.setDiaChiGiao(record[7] == null ? null : (String) record[7]);
                    row.setGenDate(record[8] == null ? null : (Date) record[8]);
                    row.setSoLuong(record[9] == null ? null : Integer.valueOf(record[9].toString()));
                    resultList.add(row);
                });
                page.setItems(resultList);
            }
            Query queryCount = entityManager.createNativeQuery(sqlBufferCount.toString());
            if (bienSo != null && !"".equals(bienSo)) {
                queryCount.setParameter("bienSo", bienSo.trim().toUpperCase());
            }
            if (loaiXe != null && !"".equals(loaiXe)) {
                queryCount.setParameter("loaiXe", "%" + loaiXe.trim().toUpperCase() + "%");
            }
            if (hinhThucVanChuyen != null && !"".equals(hinhThucVanChuyen)) {
                queryCount.setParameter("hinhThucVanChuyen", hinhThucVanChuyen.trim().toUpperCase());
            }
            if (fromDate != null) {
                queryCount.setParameter("fromGenDate", fromDate);
            }
            if (toDate != null) {
                queryCount.setParameter("toGenDate", toDate);
            }
            List resultList2 = queryCount.getResultList();
            if (resultList.size() > 0) {
                BigInteger count = (BigInteger) resultList2.get(0);
                page.setRowCount(count.longValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(page);
    }
}
