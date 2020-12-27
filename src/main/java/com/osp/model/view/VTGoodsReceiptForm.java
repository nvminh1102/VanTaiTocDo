package com.osp.model.view;

import com.osp.model.VtGoodsReceiptDetail;
import com.osp.model.VtReceipt;

import java.util.List;

/**
 * Created by Admin on 12/27/2017.
 */
public class VTGoodsReceiptForm {
    private VtGoodsReceiptBO vtGoodsReceiptBO;
    private VtPhieuThuView vtPhieuThuView;
    private List<VtReceiptView> vtReceiptViews;

    public VtPhieuThuView getVtPhieuThuView() {
        return vtPhieuThuView;
    }

    public void setVtPhieuThuView(VtPhieuThuView vtPhieuThuView) {
        this.vtPhieuThuView = vtPhieuThuView;
    }

    
    public VtGoodsReceiptBO getVtGoodsReceiptBO() {
        return vtGoodsReceiptBO;
    }

    public void setVtGoodsReceiptBO(VtGoodsReceiptBO vtGoodsReceiptBO) {
        this.vtGoodsReceiptBO = vtGoodsReceiptBO;
    }

    public List<VtReceiptView> getVtReceiptViews() {
        return vtReceiptViews;
    }

    public void setVtReceiptViews(List<VtReceiptView> vtReceiptViews) {
        this.vtReceiptViews = vtReceiptViews;
    }


}
