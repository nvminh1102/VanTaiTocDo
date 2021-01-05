/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osp.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author THANH-LOI
 */
@Entity
@Table(name = "vt_receipt_detail")
@XmlRootElement
public class VtReceiptDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "receipt_id")
    private Integer receiptId;
    @Column(name = "name", length = 200)
    private String name;
    @Column(name = "unit", length = 50)
    private String unit;
    @Column(name = "numbers")
    private Integer numbers;
    @Column(name = "weight")
    private String weight;
    @Column(name = "sizes")
    private String sizes;
    @Column(name = "cost")
    private Integer cost;
    @Column(name = "debt")
    private Integer debt;
    @Column(name = "document", length = 500)
    private String document;
    @Column(name = "note", length = 500)
    private String note;
    @Column(name = "status")
    private Integer status;
    @Column(name = "gen_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date genDate;
    @Column(name = "created_by", length = 200)
    private String createdBy;
    @Column(name = "last_update")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;
    @Column(name = "updated_by", length = 200)
    private String updatedBy;

    @Transient
    private String receiptCode;
    @Transient
    private String tenNguoiGui;
    @Transient
    private String sdtNguoiGui;
    @Transient
    private String diaChiNguoiGui;
    @Transient
    private String tenNguoiNhan;
    @Transient
    private String sdtNguoiNhan;
    @Transient
    private String diaChiNguoiNhan;
    @Transient
    private String nguoiThanhToan;
    @Transient
    private String strGenDate;
    @Transient
    private String stockName;
    @Transient
    private String soTienPhaiThu;
    @Transient
    private String soHopDong;
    @Transient
    private Integer paymentType;
    @Transient
    private String maPhieuThu;
    @Transient
    private Integer tienDaTra;
    
    public VtReceiptDetail() {
    }

    public VtReceiptDetail(Integer id) {
        this.id = id;
    }

    public VtReceiptDetail(Integer id, Date genDate) {
        this.id = id;
        this.genDate = genDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Integer receiptId) {
        this.receiptId = receiptId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getNumbers() {
        return numbers;
    }

    public void setNumbers(Integer numbers) {
        this.numbers = numbers;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getSizes() {
        return sizes;
    }

    public void setSizes(String sizes) {
        this.sizes = sizes;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getDebt() {
        return debt;
    }

    public void setDebt(Integer debt) {
        this.debt = debt;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getGenDate() {
        return genDate;
    }

    public void setGenDate(Date genDate) {
        this.genDate = genDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VtReceiptDetail)) {
            return false;
        }
        VtReceiptDetail other = (VtReceiptDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.osp.model.VtReceiptDetail[ id=" + id + " ]";
    }

    public String getReceiptCode() {
        return receiptCode;
    }

    public void setReceiptCode(String receiptCode) {
        this.receiptCode = receiptCode;
    }

    public String getTenNguoiGui() {
        return tenNguoiGui;
    }

    public void setTenNguoiGui(String tenNguoiGui) {
        this.tenNguoiGui = tenNguoiGui;
    }

    public String getTenNguoiNhan() {
        return tenNguoiNhan;
    }

    public void setTenNguoiNhan(String tenNguoiNhan) {
        this.tenNguoiNhan = tenNguoiNhan;
    }

    public String getSdtNguoiNhan() {
        return sdtNguoiNhan;
    }

    public void setSdtNguoiNhan(String sdtNguoiNhan) {
        this.sdtNguoiNhan = sdtNguoiNhan;
    }

    public String getDiaChiNguoiNhan() {
        return diaChiNguoiNhan;
    }

    public void setDiaChiNguoiNhan(String diaChiNguoiNhan) {
        this.diaChiNguoiNhan = diaChiNguoiNhan;
    }

    public String getSdtNguoiGui() {
        return sdtNguoiGui;
    }

    public void setSdtNguoiGui(String sdtNguoiGui) {
        this.sdtNguoiGui = sdtNguoiGui;
    }

    public String getDiaChiNguoiGui() {
        return diaChiNguoiGui;
    }

    public void setDiaChiNguoiGui(String diaChiNguoiGui) {
        this.diaChiNguoiGui = diaChiNguoiGui;
    }

    public String getNguoiThanhToan() {
        return nguoiThanhToan;
    }

    public void setNguoiThanhToan(String nguoiThanhToan) {
        this.nguoiThanhToan = nguoiThanhToan;
    }

    public String getStrGenDate() {
        return strGenDate;
    }

    public void setStrGenDate(String strGenDate) {
        this.strGenDate = strGenDate;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getSoTienPhaiThu() {
        return soTienPhaiThu;
    }

    public void setSoTienPhaiThu(String soTienPhaiThu) {
        this.soTienPhaiThu = soTienPhaiThu;
    }

    public String getSoHopDong() {
        return soHopDong;
    }

    public void setSoHopDong(String soHopDong) {
        this.soHopDong = soHopDong;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public String getMaPhieuThu() {
        return maPhieuThu;
    }

    public void setMaPhieuThu(String maPhieuThu) {
        this.maPhieuThu = maPhieuThu;
    }

    public Integer getTienDaTra() {
        return tienDaTra;
    }

    public void setTienDaTra(Integer tienDaTra) {
        this.tienDaTra = tienDaTra;
    }
    
}
