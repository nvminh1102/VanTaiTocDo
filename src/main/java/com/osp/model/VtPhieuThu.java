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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author THANH-LOI
 */
@Entity
@Table(name = "vt_phieu_thu")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VtPhieuThu.findAll", query = "SELECT v FROM VtPhieuThu v")
    , @NamedQuery(name = "VtPhieuThu.findById", query = "SELECT v FROM VtPhieuThu v WHERE v.id = :id")
    , @NamedQuery(name = "VtPhieuThu.findByReceiptCode", query = "SELECT v FROM VtPhieuThu v WHERE v.receiptCode = :receiptCode")
    , @NamedQuery(name = "VtPhieuThu.findByDeliveryPartnerId", query = "SELECT v FROM VtPhieuThu v WHERE v.deliveryPartnerId = :deliveryPartnerId")
    , @NamedQuery(name = "VtPhieuThu.findByReceivePartnerId", query = "SELECT v FROM VtPhieuThu v WHERE v.receivePartnerId = :receivePartnerId")
    , @NamedQuery(name = "VtPhieuThu.findByNameStock", query = "SELECT v FROM VtPhieuThu v WHERE v.nameStock = :nameStock")
    , @NamedQuery(name = "VtPhieuThu.findByDateReceipt", query = "SELECT v FROM VtPhieuThu v WHERE v.dateReceipt = :dateReceipt")
    , @NamedQuery(name = "VtPhieuThu.findByDatepushStock", query = "SELECT v FROM VtPhieuThu v WHERE v.datepushStock = :datepushStock")
    , @NamedQuery(name = "VtPhieuThu.findByPayer", query = "SELECT v FROM VtPhieuThu v WHERE v.payer = :payer")
    , @NamedQuery(name = "VtPhieuThu.findByEmployee", query = "SELECT v FROM VtPhieuThu v WHERE v.employee = :employee")
    , @NamedQuery(name = "VtPhieuThu.findByStatus", query = "SELECT v FROM VtPhieuThu v WHERE v.status = :status")
    , @NamedQuery(name = "VtPhieuThu.findByFileAttach", query = "SELECT v FROM VtPhieuThu v WHERE v.fileAttach = :fileAttach")
    , @NamedQuery(name = "VtPhieuThu.findByGenDate", query = "SELECT v FROM VtPhieuThu v WHERE v.genDate = :genDate")
    , @NamedQuery(name = "VtPhieuThu.findByCreatedBy", query = "SELECT v FROM VtPhieuThu v WHERE v.createdBy = :createdBy")
    , @NamedQuery(name = "VtPhieuThu.findByLastUpdate", query = "SELECT v FROM VtPhieuThu v WHERE v.lastUpdate = :lastUpdate")
    , @NamedQuery(name = "VtPhieuThu.findByUpdatedBy", query = "SELECT v FROM VtPhieuThu v WHERE v.updatedBy = :updatedBy")})
public class VtPhieuThu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 200)
    @Column(name = "receipt_code")
    private String receiptCode;
    @Column(name = "delivery_partner_id")
    private Integer deliveryPartnerId;
    @Column(name = "receive_partner_id")
    private Integer receivePartnerId;
    @Size(max = 200)
    @Column(name = "name_Stock")
    private String nameStock;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_receipt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateReceipt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_push_Stock")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datepushStock;
    @Size(max = 200)
    @Column(name = "payer")
    private String payer;
    @Size(max = 200)
    @Column(name = "employee")
    private String employee;
    @Column(name = "status")
    private Integer status;
    @Size(max = 200)
    @Column(name = "file_attach")
    private String fileAttach;
    @Basic(optional = false)
    @NotNull
    @Column(name = "gen_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date genDate;
    @Size(max = 200)
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "last_update")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;
    @Size(max = 200)
    @Column(name = "updated_by")
    private String updatedBy;

    public VtPhieuThu() {
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