package com.osp.web.dao;

import com.osp.model.VtPartner;

public interface KhachHangDAO {
  VtPartner add(VtPartner item);
  VtPartner getByCMND(String taxCode,int typePartner);
  VtPartner getById(Integer id);
  boolean edit(VtPartner item);
}
