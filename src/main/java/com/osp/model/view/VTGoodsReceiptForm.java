package com.osp.model.view;

import com.osp.model.VtGoodsReceipt;
import com.osp.model.VtGoodsReceiptDetail;

import java.util.List;

/**
 * Created by Admin on 12/27/2017.
 */
public class VTGoodsReceiptForm {
    private VtGoodsReceipt vtGoodsReceipt;
    private List<VtGoodsReceiptDetail> vtGoodsReceiptDetail;

    public VtGoodsReceipt getVtGoodsReceipt() {
        return vtGoodsReceipt;
    }

    public void setVtGoodsReceipt(VtGoodsReceipt vtGoodsReceipt) {
        this.vtGoodsReceipt = vtGoodsReceipt;
    }

    public List<VtGoodsReceiptDetail> getVtGoodsReceiptDetail() {
        return vtGoodsReceiptDetail;
    }

    public void setVtGoodsReceiptDetail(List<VtGoodsReceiptDetail> vtGoodsReceiptDetail) {
        this.vtGoodsReceiptDetail = vtGoodsReceiptDetail;
    }

    
}
