package com.osp.web.dao;

import com.osp.common.PagingResult;
import com.osp.model.User;
import com.osp.model.view.VTGoodsReceiptForm;
import com.osp.model.view.VtPhieuThuView;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

public interface PhieuThuDAO {
    Optional<PagingResult> search(VtPhieuThuView item, PagingResult page);
    Boolean add(VTGoodsReceiptForm vTGoodsReceiptForm, User user, HttpServletRequest request);
    Boolean delete(Integer id, User user, String ip, HttpServletRequest request);
    VTGoodsReceiptForm getVtPhieuThuFormById(Integer id);
    Integer getMaxId();

}
