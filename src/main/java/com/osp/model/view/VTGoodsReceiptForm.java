package com.osp.model.view;

import com.osp.model.VtReceiptDetail;
import com.osp.model.VtToaHang;

import java.util.List;

/**
 * Created by Admin on 12/27/2017.
 */
public class VTGoodsReceiptForm {
    private VtGoodsReceiptBO vtGoodsReceiptBO;
    private VtPhieuThuView vtPhieuThuView;
    private VtToaHang vtToaHang;
    private List<VtReceiptView> vtReceiptViews;
    private List<VtReceiptDetail> vtReceiptDetail;

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

    public VtToaHang getVtToaHang() {
        return vtToaHang;
    }

    public void setVtToaHang(VtToaHang vtToaHang) {
        this.vtToaHang = vtToaHang;
    }

    public List<VtReceiptDetail> getVtReceiptDetail() {
        return vtReceiptDetail;
    }

    public void setVtReceiptDetail(List<VtReceiptDetail> vtReceiptDetail) {
        this.vtReceiptDetail = vtReceiptDetail;
    }
    
    
}
