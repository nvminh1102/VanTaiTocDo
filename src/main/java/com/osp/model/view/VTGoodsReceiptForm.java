package com.osp.model.view;

import com.osp.model.VtGoodsReceiptDetail;
import com.osp.model.VtReceipt;

import java.util.List;

/**
 * Created by Admin on 12/27/2017.
 */
public class VTGoodsReceiptForm {
    private VtGoodsReceiptBO vtGoodsReceiptBO;
    private List<VtReceipt> vtReceipts;

    public VtGoodsReceiptBO getVtGoodsReceiptBO() {
        return vtGoodsReceiptBO;
    }

    public void setVtGoodsReceiptBO(VtGoodsReceiptBO vtGoodsReceiptBO) {
        this.vtGoodsReceiptBO = vtGoodsReceiptBO;
    }

    public List<VtReceipt> getVtReceipts() {
        return vtReceipts;
    }

    public void setVtReceipts(List<VtReceipt> vtReceipts) {
        this.vtReceipts = vtReceipts;
    }

}
