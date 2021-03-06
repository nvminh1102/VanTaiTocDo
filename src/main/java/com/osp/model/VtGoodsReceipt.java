/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osp.model;

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
@Table(name = "vt_goods_receipt")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VtGoodsReceipt.findAll", query = "SELECT v FROM VtGoodsReceipt v")
    , @NamedQuery(name = "VtGoodsReceipt.findById", query = "SELECT v FROM VtGoodsReceipt v WHERE v.id = :id")
    , @NamedQuery(name = "VtGoodsReceipt.findByReceiptCode", query = "SELECT v FROM VtGoodsReceipt v WHERE v.receiptCode = :receiptCode")
    , @NamedQuery(name = "VtGoodsReceipt.findByTruckPartnerId", query = "SELECT v FROM VtGoodsReceipt v WHERE v.truckPartnerId = :truckPartnerId")
    , @NamedQuery(name = "VtGoodsReceipt.findByBienSo", query = "SELECT v FROM VtGoodsReceipt v WHERE v.bienSo = :bienSo")
    , @NamedQuery(name = "VtGoodsReceipt.findByDateDelivery", query = "SELECT v FROM VtGoodsReceipt v WHERE v.dateDelivery = :dateDelivery")
    , @NamedQuery(name = "VtGoodsReceipt.findByDateReceive", query = "SELECT v FROM VtGoodsReceipt v WHERE v.dateReceive = :dateReceive")
    , @NamedQuery(name = "VtGoodsReceipt.findByGenDate", query = "SELECT v FROM VtGoodsReceipt v WHERE v.genDate = :genDate")
    , @NamedQuery(name = "VtGoodsReceipt.findByCreatedBy", query = "SELECT v FROM VtGoodsReceipt v WHERE v.createdBy = :createdBy")
    , @NamedQuery(name = "VtGoodsReceipt.findByLastUpdate", query = "SELECT v FROM VtGoodsReceipt v WHERE v.lastUpdate = :lastUpdate")
    , @NamedQuery(name = "VtGoodsReceipt.findByUpdatedBy", query = "SELECT v FROM VtGoodsReceipt v WHERE v.updatedBy = :updatedBy")})
public class VtGoodsReceipt implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "receipt_code", length = 200)
    private String receiptCode;
    @Column(name = "truck_partner_id")
    private Integer truckPartnerId;
    @Column(name = "bien_so", length = 50)
    private String bienSo;
    @Column(name = "loai_xe", length = 200)
    private String loaiXe;
    @Column(name = "date_delivery")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDelivery;
    @Column(name = "date_receive")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateReceive;
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
    private String truckPartnerName;
    @Transient
    private String partName;
    @Transient
    private Date fromDelivery;
    @Transient
    private Date toDelivery;
    @Transient
    private Date fromReceive;
    @Transient
    private Date toReceive;

    public VtGoodsReceipt() {
    }

    public VtGoodsReceipt(Integer id) {
        this.id = id;
    }

    public VtGoodsReceipt(VtGoodsReceiptBO vtGoodsReceiptBO) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            this.id = vtGoodsReceiptBO.getId();
            this.receiptCode = vtGoodsReceiptBO.getReceiptCode();
            this.truckPartnerId = vtGoodsReceiptBO.getTruckPartnerId();
            this.bienSo = vtGoodsReceiptBO.getBienSo();
            this.loaiXe = vtGoodsReceiptBO.getLoaiXe();
            if(vtGoodsReceiptBO.getStrDateDelivery()!=null && !vtGoodsReceiptBO.getStrDateDelivery().trim().equals("")){
                this.dateDelivery = simpleDateFormat.parse(vtGoodsReceiptBO.getStrDateDelivery());
            }
            if(vtGoodsReceiptBO.getStrDateReceive()!=null && !vtGoodsReceiptBO.getStrDateReceive().trim().equals("")){
                this.dateReceive = simpleDateFormat.parse(vtGoodsReceiptBO.getStrDateReceive());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public VtGoodsReceipt(Integer id, Date dateDelivery, Date dateReceive, Date genDate) {
        this.id = id;
        this.dateDelivery = dateDelivery;
        this.dateReceive = dateReceive;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VtGoodsReceipt)) {
            return false;
        }
        VtGoodsReceipt other = (VtGoodsReceipt) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.osp.model.VtGoodsReceipt[ id=" + id + " ]";
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public Date getFromDelivery() {
        return fromDelivery;
    }

    public void setFromDelivery(Date fromDelivery) {
        this.fromDelivery = fromDelivery;
    }

    public Date getToDelivery() {
        return toDelivery;
    }

    public void setToDelivery(Date toDelivery) {
        this.toDelivery = toDelivery;
    }

    public Date getFromReceive() {
        return fromReceive;
    }

    public void setFromReceive(Date fromReceive) {
        this.fromReceive = fromReceive;
    }

    public Date getToReceive() {
        return toReceive;
    }

    public void setToReceive(Date toReceive) {
        this.toReceive = toReceive;
    }

    public String getTruckPartnerName() {
        return truckPartnerName;
    }

    public void setTruckPartnerName(String truckPartnerName) {
        this.truckPartnerName = truckPartnerName;
    }

}
