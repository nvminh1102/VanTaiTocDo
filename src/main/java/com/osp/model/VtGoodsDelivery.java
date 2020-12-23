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
@Table(name = "vt_goods_delivery")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VtGoodsDelivery.findAll", query = "SELECT v FROM VtGoodsDelivery v")
    , @NamedQuery(name = "VtGoodsDelivery.findById", query = "SELECT v FROM VtGoodsDelivery v WHERE v.id = :id")
    , @NamedQuery(name = "VtGoodsDelivery.findByDeliveryCode", query = "SELECT v FROM VtGoodsDelivery v WHERE v.deliveryCode = :deliveryCode")
    , @NamedQuery(name = "VtGoodsDelivery.findByTruckPartnerId", query = "SELECT v FROM VtGoodsDelivery v WHERE v.truckPartnerId = :truckPartnerId")
    , @NamedQuery(name = "VtGoodsDelivery.findByBienSo", query = "SELECT v FROM VtGoodsDelivery v WHERE v.bienSo = :bienSo")
    , @NamedQuery(name = "VtGoodsDelivery.findByDateDelivery", query = "SELECT v FROM VtGoodsDelivery v WHERE v.dateDelivery = :dateDelivery")
    , @NamedQuery(name = "VtGoodsDelivery.findByDateReceive", query = "SELECT v FROM VtGoodsDelivery v WHERE v.dateReceive = :dateReceive")
    , @NamedQuery(name = "VtGoodsDelivery.findByGenDate", query = "SELECT v FROM VtGoodsDelivery v WHERE v.genDate = :genDate")
    , @NamedQuery(name = "VtGoodsDelivery.findByCreatedBy", query = "SELECT v FROM VtGoodsDelivery v WHERE v.createdBy = :createdBy")
    , @NamedQuery(name = "VtGoodsDelivery.findByLastUpdate", query = "SELECT v FROM VtGoodsDelivery v WHERE v.lastUpdate = :lastUpdate")
    , @NamedQuery(name = "VtGoodsDelivery.findByUpdatedBy", query = "SELECT v FROM VtGoodsDelivery v WHERE v.updatedBy = :updatedBy")})
public class VtGoodsDelivery implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "delivery_code", length = 200)
    private String deliveryCode;
    @Column(name = "truck_partner_id")
    private Integer truckPartnerId;
    @Column(name = "bien_so", length = 50)
    private String bienSo;
    @Basic(optional = false)
    @Column(name = "date_delivery")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDelivery;
    @Basic(optional = false)
    @Column(name = "date_receive")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateReceive;
    @Basic(optional = false)
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

    public VtGoodsDelivery() {
    }

    public VtGoodsDelivery(Integer id) {
        this.id = id;
    }

    public VtGoodsDelivery(Integer id, Date dateDelivery, Date dateReceive, Date genDate) {
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

    public String getDeliveryCode() {
        return deliveryCode;
    }

    public void setDeliveryCode(String deliveryCode) {
        this.deliveryCode = deliveryCode;
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
        if (!(object instanceof VtGoodsDelivery)) {
            return false;
        }
        VtGoodsDelivery other = (VtGoodsDelivery) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.osp.model.VtGoodsDelivery[ id=" + id + " ]";
    }
    
}
