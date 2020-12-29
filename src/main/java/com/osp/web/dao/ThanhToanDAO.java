package com.osp.web.dao;

import com.osp.common.PagingResult;
import java.util.Date;
import java.util.Optional;

public interface ThanhToanDAO {
  Optional<PagingResult> page(PagingResult page, String soPhieuNhan, String nguoiGui,Long typePayment, Long isPayment,  Date fromGenDate, Date toGenDate);
  Integer getMaxId();
}
