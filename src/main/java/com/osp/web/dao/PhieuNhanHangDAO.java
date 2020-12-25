package com.osp.web.dao;

import com.osp.common.PagingResult;
import com.osp.model.VtGoodsReceipt;
import java.util.Optional;

public interface PhieuNhanHangDAO {

    VtGoodsReceipt add(VtGoodsReceipt item);

    Optional<PagingResult> search(VtGoodsReceipt item, PagingResult page);

}
