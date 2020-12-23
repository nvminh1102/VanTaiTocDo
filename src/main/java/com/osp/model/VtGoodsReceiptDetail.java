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
@Table(name = "vt_goods_receipt_detail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VtGoodsReceiptDetail.findAll", query = "SELECT v FROM VtGoodsReceiptDetail v")
    , @NamedQuery(name = "VtGoodsReceiptDetail.findById", query = "SELECT v FROM VtGoodsReceiptDetail v WHERE v.id = :id")
    , @NamedQuery(name = "VtGoodsReceiptDetail.findByGoodsreceiptid", query = "SELECT v FROM VtGoodsReceiptDetail v WHERE v.goodsreceiptid = :goodsreceiptid")
    , @NamedQuery(name = "VtGoodsReceiptDetail.findByReceiptId", query = "SELECT v FROM VtGoodsReceiptDetail v WHERE v.receiptId = :receiptId")
    , @NamedQuery(name = "VtGoodsReceiptDetail.findByFileAttach", query = "SELECT v FROM VtGoodsReceiptDetail v WHERE v.fileAttach = :fileAttach")
    , @NamedQuery(name = "VtGoodsReceiptDetail.findByGenDate", query = "SELECT v FROM VtGoodsReceiptDetail v WHERE v.genDate = :genDate")
    , @NamedQuery(name = "VtGoodsReceiptDetail.findByCreatedBy", query = "SELECT v FROM VtGoodsReceiptDetail v WHERE v.createdBy = :createdBy")
    , @NamedQuery(name = "VtGoodsReceiptDetail.findByLastUpdate", query = "SELECT v FROM VtGoodsReceiptDetail v WHERE v.lastUpdate = :lastUpdate")
    , @NamedQuery(name = "VtGoodsReceiptDetail.findByUpdatedBy", query = "SELECT v FROM VtGoodsReceiptDetail v WHERE v.updatedBy = :updatedBy")})
public class VtGoodsReceiptDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "Goods_receipt_id")
    private Integer goodsreceiptid;
    @Column(name = "receipt_id")
    private Integer receiptId;
    @Column(name = "file_attach", length = 200)
    private String fileAttach;
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

    public VtGoodsReceiptDetail() {
    }

    public VtGoodsReceiptDetail(Integer id) {
        this.id = id;
    }

    public VtGoodsReceiptDetail(Integer id, Date genDate) {
        this.id = id;
        this.genDate = genDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsreceiptid() {
        return goodsreceiptid;
    }

    public void setGoodsreceiptid(Integer goodsreceiptid) {
        this.goodsreceiptid = goodsreceiptid;
    }

    public Integer getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Integer receiptId) {
        this.receiptId = receiptId;
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
        if (!(object instanceof VtGoodsReceiptDetail)) {
            return false;
        }
        VtGoodsReceiptDetail other = (VtGoodsReceiptDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.osp.model.VtGoodsReceiptDetail[ id=" + id + " ]";
    }
    
}
