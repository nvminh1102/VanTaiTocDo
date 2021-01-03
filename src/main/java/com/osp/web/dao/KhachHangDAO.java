package com.osp.web.dao;

import com.osp.common.PagingResult;
import com.osp.model.VtPartner;
import java.util.List;
import java.util.Optional;

public interface KhachHangDAO {
  VtPartner add(VtPartner item);
  VtPartner getByCMND(String taxCode,int typePartner);
  VtPartner getById(Integer id);
  boolean edit(VtPartner item);
  Optional<PagingResult> page(PagingResult page, String fullName, String taxCode, String mobile, String address, Long typePartner);
  boolean delete(int id);
  public List<VtPartner>  getListByType(Integer typePartner);
  
}
