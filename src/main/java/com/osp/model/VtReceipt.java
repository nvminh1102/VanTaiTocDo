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
@Table(name = "vt_receipt")
@XmlRootElement
public class VtReceipt implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "receipt_code", length = 200)
    private String receiptCode;
    @Column(name = "delivery_partner_id")
    private Integer deliveryPartnerId;
    @Column(name = "receive_partner_id")
    private Integer receivePartnerId;
    @Column(name = "name_Stock", length = 200)
    private String nameStock;
    @Column(name = "date_receipt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateReceipt;
    @Column(name = "date_push_Stock")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datepushStock;
    @Column(name = "payment_type")
    private Integer paymentType;
    @Column(name = "payer", length = 200)
    private String payer;
    @Column(name = "tax_code", length = 200)
    private String taxCode;
    @Column(name = "nha_xe", length = 200)
    private String nhaXe;
    @Column(name = "loai_xe", length = 200)
    private String loaiXe;
    @Column(name = "bien_so", length = 50)
    private String bienSo;
    @Column(name = "employee", length = 200)
    private String employee;
    @Column(name = "status")
    private Integer status;
    @Column(name = "file_attach", length = 200)
    private String fileAttach;
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
    @Column(name = "tien_da_tra")
    private Integer tienDaTra;

    
    @Transient
    private Date fromDeceipt;
    @Transient
    private Date toDeceipt;
    
    
    public VtReceipt() {
    }

    public VtReceipt(Integer id) {
        this.id = id;
    }

    public VtReceipt(Integer id, Date dateReceipt, Date datepushStock, Date genDate) {
        this.id = id;
        this.dateReceipt = dateReceipt;
        this.datepushStock = datepushStock;
        this.genDate = genDate;
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

    public Date getDateReceipt() {
        return dateReceipt;
    }

    public void setDateReceipt(Date dateReceipt) {
        this.dateReceipt = dateReceipt;
    }

    public Date getDatepushStock() {
        return datepushStock;
    }

    public void setDatepushStock(Date datepushStock) {
        this.datepushStock = datepushStock;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getNhaXe() {
        return nhaXe;
    }

    public void setNhaXe(String nhaXe) {
        this.nhaXe = nhaXe;
    }

    public String getLoaiXe() {
        return loaiXe;
    }

    public void setLoaiXe(String loaiXe) {
        this.loaiXe = loaiXe;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFileAttach() {
        return fileAttach;
    }

    public void setFileAttach(String fileAttach) {
        this.fileAttach = fileAttach;
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
        if (!(object instanceof VtReceipt)) {
            return false;
        }
        VtReceipt other = (VtReceipt) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.osp.model.VtReceipt[ id=" + id + " ]";
    }

    public Date getFromDeceipt() {
        return fromDeceipt;
    }

    public void setFromDeceipt(Date fromDeceipt) {
        this.fromDeceipt = fromDeceipt;
    }

    public Date getToDeceipt() {
        return toDeceipt;
    }

    public void setToDeceipt(Date toDeceipt) {
        this.toDeceipt = toDeceipt;
    }

    public Integer getTienDaTra() {
        return tienDaTra;
    }

    public void setTienDaTra(Integer tienDaTra) {
        this.tienDaTra = tienDaTra;
    }
    
    
    
}
