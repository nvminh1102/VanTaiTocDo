package com.osp.web.dao;

import com.osp.common.PagingResult;
import com.osp.model.VtReceipt;
import com.osp.model.VtReceiptDetail;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BienNhanDAO {

    VtReceipt add(VtReceipt item);

    Optional<PagingResult> search(PagingResult page, String receiptCode, String nameStock, Date fromGenDate, Date toGenDate, String loaiXe, String bienSo);
    
    Optional<PagingResult> page(PagingResult page, String receiptCode, String nameStock, Date fromGenDate, Date toGenDate, String loaiXe, String bienSo);

    boolean delete(int id);

    VtReceipt getById(Integer id);

    boolean edit(VtReceipt item);
    
    List<VtReceiptDetail> getListVtReceiptDetail(Integer receiptId);

    Integer getMaxId();
}
