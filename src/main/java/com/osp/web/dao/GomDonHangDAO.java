package com.osp.web.dao;

import com.osp.common.PagingResult;
import com.osp.model.User;
import com.osp.model.VtReceiptDetail;
import com.osp.model.view.VTGoodsReceiptForm;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface GomDonHangDAO {

    Optional<PagingResult> search(String bienSo, Date fromGenDate, Date toGenDate , PagingResult page);
    Boolean add(VTGoodsReceiptForm vTGoodsReceiptForm, User user);
    Boolean delete(Integer id, User user, String ip);
    VTGoodsReceiptForm getVTGoodsReceiptFormById(Integer id);
    VTGoodsReceiptForm getExportById(Integer id);
    List<VtReceiptDetail> getPhieuNhanHang(Integer id);
    Integer getMaxId();

}
