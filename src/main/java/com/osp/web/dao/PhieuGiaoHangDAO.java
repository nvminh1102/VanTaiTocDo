package com.osp.web.dao;

import com.osp.common.PagingResult;
import com.osp.model.User;
import com.osp.model.VtPhieuGiaoHang;
import com.osp.model.view.VTGoodsReceiptForm;
import java.util.Optional;

public interface PhieuGiaoHangDAO {

    Optional<PagingResult> search(VtPhieuGiaoHang vtPhieuGiaoHang, PagingResult page);
    Boolean add(VTGoodsReceiptForm vTGoodsReceiptForm, User user);
    Boolean delete(Integer id, User user, String ip);
    VTGoodsReceiptForm getVTGoodsReceiptFormById(Integer id);
    VTGoodsReceiptForm getListBienNhan(Integer id);
    Integer getMaxId();

}
