package com.osp.web.dao;

import com.osp.common.PagingResult;

import java.util.Date;
import java.util.Optional;

public interface ThongKeGiaoNhanHangDAO {
    Optional<PagingResult> page(PagingResult page, String bienSo, String loaiXe, String hinhThucVanChuyen, Date fromDate, Date toDate, boolean isExport);
}
