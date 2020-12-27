package com.osp.web.dao;

import com.osp.common.PagingResult;
import com.osp.model.User;
import com.osp.model.view.VTGoodsReceiptForm;
import com.osp.model.view.VtPhieuThuView;
import java.util.Optional;

public interface PhieuThuDAO {
    Optional<PagingResult> search(VtPhieuThuView item, PagingResult page);
    Boolean add(VTGoodsReceiptForm vTGoodsReceiptForm, User user);
    Boolean delete(Integer id, User user, String ip);
    VTGoodsReceiptForm getVtPhieuThuFormById(Integer id);
    Integer getMaxId();

}
