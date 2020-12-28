package com.osp.web.dao;

import com.osp.common.PagingResult;
import com.osp.model.NhaXe;
import java.util.Optional;

public interface NhaXeDAO {
  NhaXe add(NhaXe item);
  NhaXe getById(Integer id);
  boolean edit(NhaXe item);
  Optional<PagingResult> page(PagingResult page, String nhaXe, String loaiXe, String bienSo, String tenLaiXe);
  boolean delete(int id);
}
