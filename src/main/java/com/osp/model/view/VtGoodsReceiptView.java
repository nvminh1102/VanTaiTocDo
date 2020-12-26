/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osp.model.view;

import com.osp.model.*;
import com.osp.model.view.VtGoodsReceiptBO;
import java.io.Serializable;
import java.text.SimpleDateFormat;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author THANH-LOI
 */
@Entity
public class VtGoodsReceiptView implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "receipt_code")
    private String receiptCode;
    @Column(name = "truck_partner_id")
    private Integer truckPartnerId;
    @Column(name = "bien_so")
    private String bienSo;
    @Column(name = "loai_xe")
    private String loaiXe;
    @Column(name = "date_delivery")
    private Date dateDelivery;
    @Column(name = "date_receive")
    private Date dateReceive;
    @Column(name = "gen_date")
    private Date genDate;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "last_update")
    private Date lastUpdate;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "truckPartnerName")
    private String truckPartnerName;

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

    public Date getDateDelivery() {
        return dateDelivery;
    }

    public void setDateDelivery(Date dateDelivery) {
        this.dateDelivery = dateDelivery;
    }

    public Date getDateReceive() {
        return dateReceive;
    }

    public void setDateReceive(Date dateReceive) {
        this.dateReceive = dateReceive;
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

    public String getTruckPartnerName() {
        return truckPartnerName;
    }

    public void setTruckPartnerName(String truckPartnerName) {
        this.truckPartnerName = truckPartnerName;
    }

    
    
}
