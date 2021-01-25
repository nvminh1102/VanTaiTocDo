package com.osp.web.dao;

import com.osp.common.PagingResult;
import com.osp.model.User;
import com.osp.model.VtToaHang;
import com.osp.model.view.VTGoodsReceiptForm;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

public interface ToaHangDAO {

    Optional<PagingResult> search(VtToaHang  vtToaHang, PagingResult page);
    Boolean add(VTGoodsReceiptForm vTGoodsReceiptForm, User user, HttpServletRequest request);
    Boolean delete(Integer id, User user, String ip, HttpServletRequest request);
    VTGoodsReceiptForm getVTGoodsReceiptFormById(Integer id);
    VTGoodsReceiptForm getListBienNhan(Integer id);
    Integer getMaxId();

}
