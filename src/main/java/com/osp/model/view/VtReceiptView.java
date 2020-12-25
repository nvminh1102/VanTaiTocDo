package com.osp.model.view;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class VtReceiptView {
  @Id
  @Column(name = "ID")
  private Long id;
  @Column(name = "receipt_code")
  private String receiptCode;
  @Column(name = "date_receipt")
  private Date dateReceipt;
  @Column(name = "name_Stock")
  private String nameStock;
  @Column(name = "nha_xe")
  private String nhaXe;
  @Column(name = "bien_so")
  private String bienSo;
  @Column(name = "employee")
  private String employee;
  @Column(name = "ten_nguoi_gui")
  private String tenNguoiGui;
  @Column(name = "dia_chi_nguoi_gui")
  private String diaChiNguoiGui;
  @Column(name = "ten_nguoi_nhan")
  private String tenNguoiNhan;
  @Column(name = "dia_chi_nguoi_nhan")
  private String diaChiNguoiNhan;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getReceiptCode() {
    return receiptCode;
  }

  public void setReceiptCode(String receiptCode) {
    this.receiptCode = receiptCode;
  }

  public Date getDateReceipt() {
    return dateReceipt;
  }

  public void setDateReceipt(Date dateReceipt) {
    this.dateReceipt = dateReceipt;
  }

  public String getNameStock() {
    return nameStock;
  }

  public void setNameStock(String nameStock) {
    this.nameStock = nameStock;
  }

  public String getNhaXe() {
    return nhaXe;
  }

  public void setNhaXe(String nhaXe) {
    this.nhaXe = nhaXe;
  }

  public String getBienSo() {
    return bienSo;
  }

  public void setBienSo(String bienSo) {
    this.bienSo = bienSo;
  }

  public String getEmployee() {
    return employee;
  }

  public void setEmployee(String employee) {
    this.employee = employee;
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
}
