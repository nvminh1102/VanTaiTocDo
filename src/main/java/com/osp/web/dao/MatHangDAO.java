package com.osp.web.dao;

import com.osp.model.VtReceiptDetail;

import java.util.List;

public interface MatHangDAO {
  VtReceiptDetail add(VtReceiptDetail item);
  List<VtReceiptDetail> getDsMatHang(Integer receiptId);
  boolean delete(int id);
  boolean edit(VtReceiptDetail item);
  VtReceiptDetail getById(Integer id);
}
