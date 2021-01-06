package com.osp.model.view;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

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
  @Column(name = "payer")
  private String payer;
  @Column(name = "ten_nguoi_gui")
  private String tenNguoiGui;
  @Column(name = "dia_chi_nguoi_gui")
  private String diaChiNguoiGui;
  @Column(name = "ten_nguoi_nhan")
  private String tenNguoiNhan;
  @Column(name = "dia_chi_nguoi_nhan")
  private String diaChiNguoiNhan;
  @Column(name = "mobile_nguoi_nhan")
  private String mobileNguoiNhan;
  @Column(name = "payment_type")
  private int paymentType;
  @Column(name = "tien_da_tra")
  private Long tienDaTra;
  @Column(name = "tong_tien")
  private Long tongTien;
  @Column(name = "status")
  private Long status;

  @Transient
  private Long tienTraTruoc;
  @Transient
  private Long tienTraSau;
  @Transient
  private Long tienCongNo;
  @Transient
  private Long tienConPhaiThu;
  @Transient
  private String daThanhToan;
  @Transient
  private Integer soLuong;
  @Transient
  private String name;
  @Transient
  private String note;
  @Transient
  private String soHopDong;
  @Transient
  private String maPhieuThu;
  @Transient
  private String soTienPhaiThu;
  @Transient
  private String maToaHang;


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

    public String getMobileNguoiNhan() {
        return mobileNguoiNhan;
    }

    public void setMobileNguoiNhan(String mobileNguoiNhan) {
        this.mobileNguoiNhan = mobileNguoiNhan;
    }

  public String getPayer() {
    return payer;
  }

  public void setPayer(String payer) {
    this.payer = payer;
  }

  public int getPaymentType() {
    return paymentType;
  }

  public void setPaymentType(int paymentType) {
    this.paymentType = paymentType;
  }

  public Long getTienDaTra() {
    return tienDaTra;
  }

  public void setTienDaTra(Long tienDaTra) {
    this.tienDaTra = tienDaTra;
  }

  public Long getTongTien() {
    return tongTien;
  }

  public void setTongTien(Long tongTien) {
    this.tongTien = tongTien;
  }

  public Long getTienTraTruoc() {
    return tienTraTruoc;
  }

  public void setTienTraTruoc(Long tienTraTruoc) {
    this.tienTraTruoc = tienTraTruoc;
  }

  public Long getTienTraSau() {
    return tienTraSau;
  }

  public void setTienTraSau(Long tienTraSau) {
    this.tienTraSau = tienTraSau;
  }

  public Long getTienCongNo() {
    return tienCongNo;
  }

  public void setTienCongNo(Long tienCongNo) {
    this.tienCongNo = tienCongNo;
  }

  public Long getTienConPhaiThu() {
    return tienConPhaiThu;
  }

  public void setTienConPhaiThu(Long tienConPhaiThu) {
    this.tienConPhaiThu = tienConPhaiThu;
  }

  public String getDaThanhToan() {
    return daThanhToan;
  }

  public void setDaThanhToan(String daThanhToan) {
    this.daThanhToan = daThanhToan;
  }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

  public Long getStatus() {
    return status;
  }

  public void setStatus(Long status) {
    this.status = status;
  }

    public String getSoHopDong() {
        return soHopDong;
    }

    public void setSoHopDong(String soHopDong) {
        this.soHopDong = soHopDong;
    }

    public String getMaPhieuThu() {
        return maPhieuThu;
    }

    public void setMaPhieuThu(String maPhieuThu) {
        this.maPhieuThu = maPhieuThu;
    }

    public String getSoTienPhaiThu() {
        return soTienPhaiThu;
    }

    public void setSoTienPhaiThu(String soTienPhaiThu) {
        this.soTienPhaiThu = soTienPhaiThu;
    }

    public String getMaToaHang() {
        return maToaHang;
    }

    public void setMaToaHang(String maToaHang) {
        this.maToaHang = maToaHang;
    }
  
  
}
