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
            if (hinhThucVanChuyen != null && !"".equals(hinhThucVanChuyen)) {
                if (hinhThucVanChuyen.trim().equals("1")) {
                    hinhThucVanChuyen = "NHẬN HÀNG";
                } else if (hinhThucVanChuyen.trim().equals("2")) {
                    hinhThucVanChuyen = "VẬN CHUYỂN";
                } else if (hinhThucVanChuyen.trim().equals("3")) {
                    hinhThucVanChuyen = "GIAO HÀNG";
                }
            }
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
            StringBuffer sqlBuffer = new StringBuffer();

            sqlBuffer.append(" select * from ( SELECT c.receipt_code  as maCode, a.nha_xe, a.loai_xe, a.bien_so, 'Giao Hàng' AS hinh_thuc_van_chuyen,  ");
            sqlBuffer.append("                      sum(g.numbers) AS so_luong, e.address AS dia_chi_nhan, f.address AS dia_chi_giao ,a.gen_date,  DATE_FORMAT(a.gen_date, '%d-%m-%Y') as strGenDate ");
            sqlBuffer.append("                     FROM vt_phieu_giao_hang a  ");
            sqlBuffer.append("                     INNER JOIN vt_phieu_giao_hang_detail b ON a.ID = b.phieu_giao_hang_id  ");
            sqlBuffer.append("                     INNER JOIN vt_receipt c ON c.ID = b.receipt_id  ");
            sqlBuffer.append("                     INNER JOIN vt_receipt_detail g ON g.receipt_id = c.id  ");
            sqlBuffer.append("                     INNER JOIN vt_nha_xe d ON a.bien_so = d.bien_so  ");
            sqlBuffer.append("                     INNER JOIN vt_partner e ON e.id = c.delivery_partner_id  ");
            sqlBuffer.append("                     INNER JOIN vt_partner f ON f.id = c.receive_partner_id  ");
            sqlBuffer.append("                     GROUP BY c.receipt_code,  a.nha_xe, a.loai_xe, a.bien_so , e.address , f.address, a.gen_date ");
            sqlBuffer.append(" UNION ALL  ");
            sqlBuffer.append("  SELECT  a.toa_hang_code as maCode , a.nha_xe as nha_xe, a.loai_xe as loai_xe, a.bien_so as bien_so, 'Vận chuyển' AS hinh_thuc_van_chuyen ,  ");
            sqlBuffer.append("                     sum(d.numbers) AS so_luong , a.noi_di AS dia_chi_nhan, a.noi_den AS dia_chi_giao, a.gen_date, DATE_FORMAT(a.gen_date, '%d-%m-%Y') as strGenDate  ");
            sqlBuffer.append("                     FROM vt_toa_hang a  ");
            sqlBuffer.append("                     INNER JOIN vt_toa_hang_detail b ON a.id = b.toa_hang_id  ");
            sqlBuffer.append("                     INNER JOIN vt_receipt_detail d ON d.id = b.vt_receipt_detail_id ");
            sqlBuffer.append("                     GROUP BY a.toa_hang_code, a.nha_xe, a.loai_xe, a.bien_so, a.noi_di, a.noi_den, a.gen_date ");
            sqlBuffer.append(" UNION ALL       ");
            sqlBuffer.append(" SELECT c.receipt_code  as maCode, a.nha_xe, a.loai_xe, a.bien_so, 'Nhận hàng' AS hinh_thuc_van_chuyen,  ");
            sqlBuffer.append("                      sum(g.numbers) AS so_luong, e.address AS dia_chi_nhan, f.address AS dia_chi_giao , a.gen_date, DATE_FORMAT(a.gen_date, '%d-%m-%Y') as strGenDate ");
            sqlBuffer.append("                     FROM vt_gom_don_nhan a  ");
            sqlBuffer.append("                     INNER JOIN vt_gom_don_nhan_detail b ON a.ID = b.vt_gom_don_nhan_id  ");
            sqlBuffer.append("                     INNER JOIN vt_receipt c ON c.ID = b.receipt_id  ");
            sqlBuffer.append("                     INNER JOIN vt_receipt_detail g ON g.receipt_id = c.id  ");
            sqlBuffer.append("                     INNER JOIN vt_nha_xe d ON a.bien_so = d.bien_so  ");
            sqlBuffer.append("                     INNER JOIN vt_partner e ON e.id = c.delivery_partner_id  ");
            sqlBuffer.append("                     INNER JOIN vt_partner f ON f.id = c.receive_partner_id  ");
            sqlBuffer.append("                     GROUP BY c.receipt_code,  a.nha_xe, a.loai_xe, a.bien_so , e.address , f.address, a.gen_date ");
            sqlBuffer.append(" ) as info ");
            StringBuffer sqlBufferCount = new StringBuffer("SELECT count(1) from ( ");
            sqlBufferCount.append(sqlBuffer.toString());
            sqlBufferCount.append(" ) as count");
            sqlBufferCount.append(strWhere.toString());
            sqlBuffer.append(strWhere.toString());
            sqlBuffer.append(" order by nha_xe, loai_xe, bien_so, GEN_DATE DESC ");

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
                    row.setNhaXe(record[1] == null ? null : (String) record[1]);
                    row.setLoaiXe(record[2] == null ? null : (String) record[2]);
                    row.setBienSo(record[3] == null ? null : (String) record[3]);
                    row.setHinhThucVanChuyen(record[4] == null ? null : (String) record[4]);
                    row.setSoLuong(record[5] == null ? null : Integer.valueOf(record[5].toString()));
                    row.setDiaChiNhan(record[6] == null ? null : (String) record[6]);
                    row.setDiaChiGiao(record[7] == null ? null : (String) record[7]);
                    row.setGenDate(record[8] == null ? null : (Date) record[8]);
                    row.setStrGenDate(record[9] == null ? null : (String) record[9]);
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

    @Override
    public Optional<ThongKeGiaoNhanView> thongKe(Date fromGenDate, Date toGenDate) {
        ThongKeGiaoNhanView thongKeGiaoNhanView = new ThongKeGiaoNhanView();
        try {
            StringBuffer sqlBuffer = new StringBuffer();
            /*
            sqlBuffer.append(" select count(aaa.receipt_code) as soDonNhan, ");
            sqlBuffer.append(" sum(case when aaa.toa_hang_code is null then 0 else 1 end) as soDonVanChuyen, ");
            sqlBuffer.append(" sum(case when aaa.ma_phieu_giao is null then 0 else 1 end) as soDonGiao, ");
            sqlBuffer.append(" sum(case when aaa.payment_type = 1 then tong_tien else 0 end) as traTruoc, ");
            sqlBuffer.append(" sum(case when aaa.payment_type = 2 then tong_tien else 0 end) as traSau, ");
            sqlBuffer.append(" sum(case when aaa.payment_type = 3 then tong_tien else 0 end) as congNo, ");
            sqlBuffer.append(" sum(aaa.tien_da_tra) as tienDaTra  ");
            sqlBuffer.append(" from ( ");
            sqlBuffer.append("     select r.receipt_code, toaHang.toa_hang_code , giaoHang.ma_phieu_giao, r.payment_type, r.tien_da_tra,  ");
            sqlBuffer.append("     (select sum(cost) from vt_receipt_detail where receipt_id = r.id) as tong_tien ");
            sqlBuffer.append("     from vt_receipt r ");
            sqlBuffer.append("     left join ");
            sqlBuffer.append(" ( ");
            sqlBuffer.append(" select gdn.ma_gom_don, gdn.nha_xe, gdn.loai_xe, gdn.bien_so, gdnd.receipt_id  ");
            sqlBuffer.append(" from vt_gom_don_nhan gdn inner join vt_gom_don_nhan_detail gdnd on gdn.id = gdnd.vt_gom_don_nhan_id ");
            sqlBuffer.append("         where gdn.gen_date > :fromGenDate and gdn.gen_date < :toGenDate ");
            sqlBuffer.append(" ) gomDon on r.id = gomDon.receipt_id  ");
            sqlBuffer.append(" left join  ");
            sqlBuffer.append(" ( ");
            sqlBuffer.append(" select distinct th.toa_hang_code, th.nha_xe, th.loai_xe, th.bien_so, thd.receipt_id  ");
            sqlBuffer.append("         from vt_toa_hang th inner join vt_toa_hang_detail thd on th.id = thd.toa_hang_id ");
            sqlBuffer.append("         where th.gen_date > :fromGenDate and th.gen_date < :toGenDate ");
            sqlBuffer.append(" ) toaHang on r.id = toaHang.receipt_id ");
            sqlBuffer.append(" left join  ");
            sqlBuffer.append(" ( ");
            sqlBuffer.append(" select pgh.ma_phieu_giao, pgh.nha_xe, pgh.loai_xe, pgh.bien_so, pghd.receipt_id  ");
            sqlBuffer.append("         from vt_phieu_giao_hang pgh inner join vt_phieu_giao_hang_detail pghd on pgh.id = pghd.phieu_giao_hang_id ");
            sqlBuffer.append("         where pgh.gen_date > :fromGenDate and pgh.gen_date < :toGenDate ");
            sqlBuffer.append(" ) giaoHang on r.id = giaohang.receipt_id ");
            sqlBuffer.append("     where r.gen_date > :fromGenDate and r.gen_date < :toGenDate ");
            sqlBuffer.append(" ) aaa ");
             */

            sqlBuffer.append(" select ");
            sqlBuffer.append(" (select count(mgd.ma_gom_don) from vt_gom_don_nhan mgd ");
            sqlBuffer.append(" where mgd.gen_date > :fromDate and mgd.gen_date < :toDate ) as soPhieuNhan, ");
            sqlBuffer.append(" (select count(th.toa_hang_code) from vt_toa_hang th ");
            sqlBuffer.append(" where th.gen_date > :fromDate and th.gen_date < :toDate ) as soToaHang, ");
            sqlBuffer.append(" (select count(pgh.ma_phieu_giao) from vt_phieu_giao_hang pgh ");
            sqlBuffer.append(" where pgh.gen_date > :fromDate and pgh.gen_date < :toDate ) as soPhieuGiao, ");
            sqlBuffer.append(" sum(case when r.payment_type = 1 then (select sum(cost) from vt_receipt_detail where receipt_id = r.id) else 0 end) as traTruoc , ");
            sqlBuffer.append(" sum(case when r.payment_type = 2 then (select sum(cost) from vt_receipt_detail where receipt_id = r.id) else 0 end) as traSau , ");
            sqlBuffer.append(" sum(case when r.payment_type = 3 then (select sum(cost) from vt_receipt_detail where receipt_id = r.id) else 0 end) as CongNo , ");
            sqlBuffer.append(" r.tien_da_tra, count(r.receipt_code) as soPhieuLap ");
            sqlBuffer.append(" from vt_receipt r ");
            sqlBuffer.append(" where r.gen_date > :fromDate and r.gen_date < :toDate ");

            Query query = entityManager.createNativeQuery(sqlBuffer.toString()).setParameter("fromDate", fromGenDate).setParameter("toDate", toGenDate);
            Object[] objects = (Object[]) query.getSingleResult();
            if (objects != null && objects.length > 0) {
                thongKeGiaoNhanView.setSoDonNhan(objects[0] == null ? "0" : String.format("%,.0f", Double.valueOf(objects[0].toString())));
                thongKeGiaoNhanView.setSoDonVanChuyen(objects[1] == null ? "0" : String.format("%,.0f", Double.valueOf(objects[1].toString())));
                thongKeGiaoNhanView.setSoDonGiao(objects[2] == null ? "0" : String.format("%,.0f", Double.valueOf(objects[2].toString())));
                thongKeGiaoNhanView.setTraTruoc(objects[3] == null ? "0" : String.format("%,.0f", Double.valueOf(objects[3].toString())));
                thongKeGiaoNhanView.setTraSau(objects[4] == null ? "0" : String.format("%,.0f", Double.valueOf(objects[4].toString())));
                thongKeGiaoNhanView.setCongNo(objects[5] == null ? "0" : String.format("%,.0f", Double.valueOf(objects[5].toString())));
                thongKeGiaoNhanView.setTienDaTra(objects[6] == null ? "0" : String.format("%,.0f", Double.valueOf(objects[6].toString())));
                thongKeGiaoNhanView.setSoPhieuLap(objects[7] == null ? "0" : String.format("%,.0f", Double.valueOf(objects[7].toString())));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(thongKeGiaoNhanView);
    }

    @Override
    public Optional<List<ThongKeGiaoNhanView>> thongKeChiTiet(Date fromGenDate, Date toGenDate) {
        List<ThongKeGiaoNhanView> resultList = new ArrayList<>();
        try {
            StringBuffer sqlBuffer = new StringBuffer();
            sqlBuffer.append(" select distinct tbl.receipt_code, tbl.status, tbl.toa_hang_code, tbl.payment_type, tbl.tien_da_tra, tbl.tong_tien, tbl.xe_nhan_hang, tbl.xe_van_chuyen, tbl.xe_giao_hang, ");
            sqlBuffer.append(" DATE_FORMAT(tbl.ngayLapPhieu, '%d/%m/%Y'),DATE_FORMAT(tbl.ngayGomDon, '%d/%m/%Y'),  DATE_FORMAT(tbl.ngayLenToa, '%d/%m/%Y'), DATE_FORMAT(tbl.ngayGiaoHang, '%d/%m/%Y') from ( ");
            sqlBuffer.append(" select r.receipt_code, r.status, toaHang.toa_hang_code, r.payment_type, r.tien_da_tra,  ");
            sqlBuffer.append(" (select sum(cost) from vt_receipt_detail where receipt_id = r.id) as tong_tien,  ");
            sqlBuffer.append(" CONCAT(gomDon.nha_xe, ' - ', gomDon.loai_xe, ' - ', gomDon.bien_so) as xe_nhan_hang, ");
            sqlBuffer.append(" CONCAT(toaHang.nha_xe, ' - ', toaHang.loai_xe, ' - ', toaHang.bien_so) as xe_van_chuyen, ");
            sqlBuffer.append(" CONCAT(giaoHang.nha_xe, ' - ', giaoHang.loai_xe, ' - ', giaoHang.bien_so) as xe_giao_hang, ");
            sqlBuffer.append(" gomDon.gen_date as ngayGomDon, toaHang.gen_date as ngayLenToa, giaoHang.gen_date as ngayGiaoHang , r.gen_date as ngayLapPhieu ");
            sqlBuffer.append(" from vt_receipt r ");
            sqlBuffer.append(" left join ");
            sqlBuffer.append(" ( ");
            sqlBuffer.append(" select gdn.ma_gom_don, gdn.nha_xe, gdn.loai_xe, gdn.bien_so, gdnd.receipt_id, gdn.gen_date from vt_gom_don_nhan gdn inner join vt_gom_don_nhan_detail gdnd on gdn.id = gdnd.vt_gom_don_nhan_id ");
//            sqlBuffer.append(" where gdn.gen_date > :fromGenDate and gdn.gen_date < :toGenDate  ");
            sqlBuffer.append(" ) gomDon on r.id = gomDon.receipt_id  ");
            sqlBuffer.append(" left join  ");
            sqlBuffer.append(" ( ");
            sqlBuffer.append(" select distinct th.toa_hang_code, th.nha_xe, th.loai_xe, th.bien_so, thd.receipt_id, th.gen_date from vt_toa_hang th inner join vt_toa_hang_detail thd on th.id = thd.toa_hang_id ");
//            sqlBuffer.append(" where th.gen_date > :fromGenDate and th.gen_date < :toGenDate ");
            sqlBuffer.append(" ) toaHang on r.id = toaHang.receipt_id ");
            sqlBuffer.append(" left join  ");
            sqlBuffer.append(" ( ");
            sqlBuffer.append(" select pgh.ma_phieu_giao, pgh.nha_xe, pgh.loai_xe, pgh.bien_so, pghd.receipt_id, pgh.gen_date from vt_phieu_giao_hang pgh inner join vt_phieu_giao_hang_detail pghd on pgh.id = pghd.phieu_giao_hang_id ");
//            sqlBuffer.append(" where pgh.gen_date > :fromGenDate and pgh.gen_date < :toGenDate ");
            sqlBuffer.append(" ) giaoHang on r.id = giaohang.receipt_id ");
//            sqlBuffer.append(" where r.gen_date > :fromGenDate and r.gen_date < :toGenDate ");
            sqlBuffer.append(" ) tbl    where  (tbl.ngayGomDon is not null and tbl.ngayGomDon > :fromDate and tbl.ngayGomDon < :toDate ) or ");
            sqlBuffer.append(" (tbl.ngayLenToa is not null and tbl.ngayLenToa > :fromDate and tbl.ngayLenToa < :toDate ) or ");
            sqlBuffer.append(" (tbl.ngayGiaoHang is not null and tbl.ngayGiaoHang > :fromDate and tbl.ngayGiaoHang < :toDate ) ");
            sqlBuffer.append("  order by tbl.ngayGiaoHang desc, tbl.ngayLenToa desc, tbl.ngayGomDon desc , tbl.ngayLapPhieu desc ");
            Query query = entityManager.createNativeQuery(sqlBuffer.toString()).setParameter("fromDate", fromGenDate).setParameter("toDate", toGenDate);
            List<Object[]> list = query.getResultList();
            if (list != null && list.size() > 0) {
                list.stream().forEach((record) -> {
                    ThongKeGiaoNhanView row = new ThongKeGiaoNhanView();
                    row.setReceiptCode(record[0] == null ? null : record[0].toString());
                    row.setStatus(record[1] == null ? null : Integer.valueOf(record[1].toString()));
                    row.setToaHang(record[2] == null ? null : (String) record[2]);
                    row.setPaymentType(record[3] == null ? null : Integer.valueOf(record[3].toString()));
                    row.setTienDaTra(record[4] == null ? null : String.format("%,.0f", Double.valueOf(record[4].toString())));
                    row.setTongTien(record[5] == null ? null : String.format("%,.0f", Double.valueOf(record[5].toString())));
                    row.setXeNhanHang(record[6] == null ? null : (String) record[6]);
                    row.setXeVanChuyen(record[7] == null ? null : (String) record[7]);
                    row.setXeGiaoHang(record[8] == null ? null : (String) record[8]);
                    row.setStrNgayLapDon(record[9] == null ? null : (String) record[9]);
                    row.setStrNgayNhanHang(record[10] == null ? null : (String) record[10]);
                    row.setStrNgayLenToa(record[11] == null ? null : (String) record[11]);
                    row.setStrNgayGiaoHang(record[12] == null ? null : (String) record[12]);
                    if (record[5] != null && Integer.valueOf(record[5].toString()) > 0) {
                        row.setChuaThuTien("");
                    } else {
                        row.setChuaThuTien("Chưa thu tiền");
                    }
                    resultList.add(row);
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(resultList);
    }
}
