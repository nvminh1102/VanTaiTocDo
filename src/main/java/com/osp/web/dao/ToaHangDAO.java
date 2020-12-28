package com.osp.web.dao;

import com.osp.common.PagingResult;
import com.osp.model.User;
import com.osp.model.VtToaHang;
import com.osp.model.view.VTGoodsReceiptForm;
import java.util.Optional;

public interface ToaHangDAO {

    Optional<PagingResult> search(VtToaHang  vtToaHang, PagingResult page);
    Boolean add(VTGoodsReceiptForm vTGoodsReceiptForm, User user);
    Boolean delete(Integer id, User user, String ip);
    VTGoodsReceiptForm getVTGoodsReceiptFormById(Integer id);
    Integer getMaxId();

}
