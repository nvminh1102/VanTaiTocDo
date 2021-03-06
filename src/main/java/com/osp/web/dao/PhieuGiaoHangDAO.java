package com.osp.web.dao;

import com.osp.common.PagingResult;
import com.osp.model.User;
import com.osp.model.VtPhieuGiaoHang;
import com.osp.model.VtPhieuGiaoHangDetail;
import com.osp.model.VtReceiptDetail;
import com.osp.model.view.VTGoodsReceiptForm;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

public interface PhieuGiaoHangDAO {

    Optional<PagingResult> search(VtPhieuGiaoHang vtPhieuGiaoHang, PagingResult page);

    Boolean add(VTGoodsReceiptForm vTGoodsReceiptForm, User user, HttpServletRequest request);

    Boolean delete(Integer id, User user, String ip);

    VTGoodsReceiptForm getVTGoodsReceiptFormById(Integer id);

    VTGoodsReceiptForm getExportById(Integer id);

    List<VtReceiptDetail> getPhieuNhanHang(Integer id);

    List<VtPhieuGiaoHangDetail> getListVtPhieuGiaoHangDetail(Integer phieuGiaoHangId);

    Integer getMaxId();

}
