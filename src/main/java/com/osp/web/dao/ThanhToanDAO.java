package com.osp.web.dao;

import com.osp.common.PagingResult;
import com.osp.model.VtCongNo;
import com.osp.model.VtCongNoDetail;

import java.util.Date;
import java.util.Optional;

public interface ThanhToanDAO {
  Optional<PagingResult> page(PagingResult page, String soPhieuNhan, String nguoiGui,Long typePayment, Long isPayment,  Date fromGenDate, Date toGenDate, boolean isThanhToan);
  Integer getMaxId();
  VtCongNo addCongNo(VtCongNo item);
  VtCongNoDetail addCongNoDetail(VtCongNoDetail item);
}
