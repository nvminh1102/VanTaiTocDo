package com.osp.web.dao;

import com.osp.common.PagingResult;
import com.osp.model.User;
import com.osp.model.VtGoodsReceipt;
import com.osp.model.view.VTGoodsReceiptForm;
import java.util.Optional;

public interface PhieuNhanHangDAO {

    VtGoodsReceipt add(VtGoodsReceipt item);

    Optional<PagingResult> search(VtGoodsReceipt item, PagingResult page);
    Boolean add(VTGoodsReceiptForm vTGoodsReceiptForm, User user);
    VTGoodsReceiptForm getVTGoodsReceiptFormById(Integer id);

}
