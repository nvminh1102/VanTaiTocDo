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
@Table(name = "vt_receipt_detail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VtReceiptDetail.findAll", query = "SELECT v FROM VtReceiptDetail v")
    , @NamedQuery(name = "VtReceiptDetail.findById", query = "SELECT v FROM VtReceiptDetail v WHERE v.id = :id")
    , @NamedQuery(name = "VtReceiptDetail.findByReceiptId", query = "SELECT v FROM VtReceiptDetail v WHERE v.receiptId = :receiptId")
    , @NamedQuery(name = "VtReceiptDetail.findByName", query = "SELECT v FROM VtReceiptDetail v WHERE v.name = :name")
    , @NamedQuery(name = "VtReceiptDetail.findByUnit", query = "SELECT v FROM VtReceiptDetail v WHERE v.unit = :unit")
    , @NamedQuery(name = "VtReceiptDetail.findByNumbers", query = "SELECT v FROM VtReceiptDetail v WHERE v.numbers = :numbers")
    , @NamedQuery(name = "VtReceiptDetail.findByWeight", query = "SELECT v FROM VtReceiptDetail v WHERE v.weight = :weight")
    , @NamedQuery(name = "VtReceiptDetail.findBySizes", query = "SELECT v FROM VtReceiptDetail v WHERE v.sizes = :sizes")
    , @NamedQuery(name = "VtReceiptDetail.findByCost", query = "SELECT v FROM VtReceiptDetail v WHERE v.cost = :cost")
    , @NamedQuery(name = "VtReceiptDetail.findByDebt", query = "SELECT v FROM VtReceiptDetail v WHERE v.debt = :debt")
    , @NamedQuery(name = "VtReceiptDetail.findByDocument", query = "SELECT v FROM VtReceiptDetail v WHERE v.document = :document")
    , @NamedQuery(name = "VtReceiptDetail.findByNote", query = "SELECT v FROM VtReceiptDetail v WHERE v.note = :note")
    , @NamedQuery(name = "VtReceiptDetail.findByStatus", query = "SELECT v FROM VtReceiptDetail v WHERE v.status = :status")
    , @NamedQuery(name = "VtReceiptDetail.findByGenDate", query = "SELECT v FROM VtReceiptDetail v WHERE v.genDate = :genDate")
    , @NamedQuery(name = "VtReceiptDetail.findByCreatedBy", query = "SELECT v FROM VtReceiptDetail v WHERE v.createdBy = :createdBy")
    , @NamedQuery(name = "VtReceiptDetail.findByLastUpdate", query = "SELECT v FROM VtReceiptDetail v WHERE v.lastUpdate = :lastUpdate")
    , @NamedQuery(name = "VtReceiptDetail.findByUpdatedBy", query = "SELECT v FROM VtReceiptDetail v WHERE v.updatedBy = :updatedBy")})
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
    private Integer weight;
    @Column(name = "sizes")
    private Integer sizes;
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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getSizes() {
        return sizes;
    }

    public void setSizes(Integer sizes) {
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
    
}
