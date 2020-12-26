package com.osp.model.view;

import com.osp.model.VtGoodsReceipt;
import java.text.SimpleDateFormat;

/**
 * Created by Admin on 12/27/2017.
 */
public class VtGoodsReceiptBO {

    private Integer id;
    private String receiptCode;
    private Integer truckPartnerId;
    private String bienSo;
    private String loaiXe;
    private String strDateDelivery;
    private String strDateReceive;

    public VtGoodsReceiptBO() {

    }

    public VtGoodsReceiptBO(VtGoodsReceipt vtGoodsReceipt) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        this.id = vtGoodsReceipt.getId();
        this.receiptCode = vtGoodsReceipt.getReceiptCode();
        this.bienSo = vtGoodsReceipt.getBienSo();
        this.loaiXe = vtGoodsReceipt.getLoaiXe();
        this.strDateDelivery = simpleDateFormat.format(vtGoodsReceipt.getDateDelivery());
        this.strDateReceive = simpleDateFormat.format(vtGoodsReceipt.getDateReceive());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReceiptCode() {
        return receiptCode;
    }

    public void setReceiptCode(String receiptCode) {
        this.receiptCode = receiptCode;
    }

    public Integer getTruckPartnerId() {
        return truckPartnerId;
    }

    public void setTruckPartnerId(Integer truckPartnerId) {
        this.truckPartnerId = truckPartnerId;
    }

    public String getBienSo() {
        return bienSo;
    }

    public void setBienSo(String bienSo) {
        this.bienSo = bienSo;
    }

    public String getLoaiXe() {
        return loaiXe;
    }

    public void setLoaiXe(String loaiXe) {
        this.loaiXe = loaiXe;
    }

    public String getStrDateDelivery() {
        return strDateDelivery;
    }

    public void setStrDateDelivery(String strDateDelivery) {
        this.strDateDelivery = strDateDelivery;
    }

    public String getStrDateReceive() {
        return strDateReceive;
    }

    public void setStrDateReceive(String strDateReceive) {
        this.strDateReceive = strDateReceive;
    }

}
