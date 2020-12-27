package com.osp.model.view;

import com.osp.model.VtPhieuThu;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class VtPhieuThuView {
  @Id
  @Column(name = "ID")
  private Integer id;
  @Column(name = "receipt_code")
  private String receiptCode;
  @Column(name = "delivery_partner_id")
  private Integer deliveryPartnerId;
  @Column(name = "receive_partner_id")
  private Integer receivePartnerId;
  @Column(name = "name_Stock")
  private String nameStock;
  @Column(name = "date_receipt")
  private Date dateReceipt;
  @Column(name = "date_push_Stock")
  private Date datePushStock;
  @Transient
  private Date fromPushStock;
  @Transient
  private Date toPushStock;
  @Column(name = "payer")
  private String payer;
  @Column(name = "employee")
  private String employee;
  @Column(name = "ten_nguoi_nhan")
  private String tenNguoiNhan;
  @Column(name = "dia_chi_nguoi_nhan")
  private String diaChiNguoiNhan;
  @Column(name = "mobile_nguoi_nhan")
  private String mobileNguoiNhan;
  @Column(name = "ten_nguoi_gui")
  private String tenNguoiGui;
  @Column(name = "dia_chi_nguoi_gui")
  private String diaChiNguoiGui;
  @Column(name = "mobile_nguoi_Gui")
  private String mobileNguoiGui;
  @Column(name = "gen_date")
  private Date genDate;
  @Column(name = "last_update")
  private Date lastUpdate;
  @Column(name = "created_by")
  private String createdBy;
  @Column(name = "updated_by")
  private String updatedBy;

  @Transient
  private String strDateReceipt;
  @Transient
  private String strFromPushStock;
  @Transient
  private String strToPushStock;
  @Transient
  private String strDatePushStock;
  
  
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

    public Integer getDeliveryPartnerId() {
        return deliveryPartnerId;
    }

    public void setDeliveryPartnerId(Integer deliveryPartnerId) {
        this.deliveryPartnerId = deliveryPartnerId;
    }

    public Integer getReceivePartnerId() {
        return receivePartnerId;
    }

    public void setReceivePartnerId(Integer receivePartnerId) {
        this.receivePartnerId = receivePartnerId;
    }


    public String getNameStock() {
        return nameStock;
    }

    public void setNameStock(String nameStock) {
        this.nameStock = nameStock;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getTenNguoiNhan() {
        return tenNguoiNhan;
    }

    public void setTenNguoiNhan(String tenNguoiNhan) {
        this.tenNguoiNhan = tenNguoiNhan;
    }

    public String getDiaChiNguoiNhan() {
        return diaChiNguoiNhan;
    }

    public void setDiaChiNguoiNhan(String diaChiNguoiNhan) {
        this.diaChiNguoiNhan = diaChiNguoiNhan;
    }

    public String getMobileNguoiNhan() {
        return mobileNguoiNhan;
    }

    public void setMobileNguoiNhan(String mobileNguoiNhan) {
        this.mobileNguoiNhan = mobileNguoiNhan;
    }

    public String getTenNguoiGui() {
        return tenNguoiGui;
    }

    public void setTenNguoiGui(String tenNguoiGui) {
        this.tenNguoiGui = tenNguoiGui;
    }

    public String getDiaChiNguoiGui() {
        return diaChiNguoiGui;
    }

    public void setDiaChiNguoiGui(String diaChiNguoiGui) {
        this.diaChiNguoiGui = diaChiNguoiGui;
    }

    public String getMobileNguoiGui() {
        return mobileNguoiGui;
    }

    public void setMobileNguoiGui(String mobileNguoiGui) {
        this.mobileNguoiGui = mobileNguoiGui;
    }

    public Date getFromPushStock() {
        return fromPushStock;
    }

    public void setFromPushStock(Date fromPushStock) {
        this.fromPushStock = fromPushStock;
    }

    public Date getToPushStock() {
        return toPushStock;
    }

    public void setToPushStock(Date toPushStock) {
        this.toPushStock = toPushStock;
    }

    public Date getDateReceipt() {
        return dateReceipt;
    }

    public void setDateReceipt(Date dateReceipt) {
        this.dateReceipt = dateReceipt;
    }

    public String getStrDateReceipt() {
        return strDateReceipt;
    }

    public void setStrDateReceipt(String strDateReceipt) {
        this.strDateReceipt = strDateReceipt;
    }

    public String getStrFromPushStock() {
        return strFromPushStock;
    }

    public void setStrFromPushStock(String strFromPushStock) {
        this.strFromPushStock = strFromPushStock;
    }

    public String getStrToPushStock() {
        return strToPushStock;
    }

    public void setStrToPushStock(String strToPushStock) {
        this.strToPushStock = strToPushStock;
    }

    public Date getDatePushStock() {
        return datePushStock;
    }

    public void setDatePushStock(Date datePushStock) {
        this.datePushStock = datePushStock;
    }

    public String getStrDatePushStock() {
        return strDatePushStock;
    }

    public void setStrDatePushStock(String strDatePushStock) {
        this.strDatePushStock = strDatePushStock;
    }

    public Date getGenDate() {
        return genDate;
    }

    public void setGenDate(Date genDate) {
        this.genDate = genDate;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }


  
}
