package com.osp.web.dao;

import com.osp.common.PagingResult;
import com.osp.model.view.ThongKeGiaoNhanView;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ThongKeGiaoNhanHangDAO {
    Optional<PagingResult> page(PagingResult page, String bienSo, String loaiXe, String hinhThucVanChuyen, Date fromDate, Date toDate, boolean isExport);
    Optional<ThongKeGiaoNhanView> thongKe(Date fromGenDate, Date toGenDate);
    Optional<List<ThongKeGiaoNhanView>> thongKeChiTiet(Date fromGenDate, Date toGenDate);
}
