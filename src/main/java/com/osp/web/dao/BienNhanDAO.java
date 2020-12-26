package com.osp.web.dao;

import com.osp.common.PagingResult;
import com.osp.model.VtReceipt;
import java.util.Date;
import java.util.Optional;

public interface BienNhanDAO {

    VtReceipt add(VtReceipt item);

    Optional<PagingResult> search(VtReceipt item, PagingResult page);

    Optional<PagingResult> page(PagingResult page, String receiptCode, String nameStock, Date fromGenDate, Date toGenDate);

    boolean delete(int id);
}
