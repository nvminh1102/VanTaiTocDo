/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osp.model;

import com.osp.model.view.VtPhieuThuView;
import java.io.Serializable;
import java.text.SimpleDateFormat;
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

/**
 *
 * @author THANH-LOI
 */
@Entity
@Table(name = "vt_phieu_thu")
public class VtPhieuThu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
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
    @Column(name = "payer", length = 200)
    private String payer;
    @Column(name = "employee")
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

    public VtPhieuThu() {
    }

    public VtPhieuThu(VtPhieuThuView vtPhieuThuView) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            this.id = vtPhieuThuView.getId();
            this.receiptCode = vtPhieuThuView.getReceiptCode();
            this.deliveryPartnerId = vtPhieuThuView.getDeliveryPartnerId();
            this.receivePartnerId = vtPhieuThuView.getReceivePartnerId();
            this.nameStock = vtPhieuThuView.getNameStock();
            this.payer = vtPhieuThuView.getPayer();
            this.employee = vtPhieuThuView.getEmployee();
            this.createdBy = vtPhieuThuView.getCreatedBy();
            this.updatedBy = vtPhieuThuView.getUpdatedBy();
            if(vtPhieuThuView.getStrDateReceipt()!=null && !vtPhieuThuView.getStrDateReceipt().trim().equals("")){
                this.dateReceipt = simpleDateFormat.parse(vtPhieuThuView.getStrDateReceipt());
            }
            if(vtPhieuThuView.getStrDatePushStock()!=null && !vtPhieuThuView.getStrDatePushStock().trim().equals("")){
                this.datepushStock = simpleDateFormat.parse(vtPhieuThuView.getStrDatePushStock());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public VtPhieuThu(Integer id) {
        this.id = id;
    }

    public VtPhieuThu(Integer id, Date dateReceipt, Date datepushStock, Date genDate) {
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
        if (!(object instanceof VtPhieuThu)) {
            return false;
        }
        VtPhieuThu other = (VtPhieuThu) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.osp.model.VtPhieuThu[ id=" + id + " ]";
    }

}
