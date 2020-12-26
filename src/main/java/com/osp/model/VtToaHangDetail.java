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
@Table(name = "vt_toa_hang_detail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VtToaHangDetail.findAll", query = "SELECT v FROM VtToaHangDetail v")
    , @NamedQuery(name = "VtToaHangDetail.findById", query = "SELECT v FROM VtToaHangDetail v WHERE v.id = :id")
    , @NamedQuery(name = "VtToaHangDetail.findByToaHangId", query = "SELECT v FROM VtToaHangDetail v WHERE v.toaHangId = :toaHangId")
    , @NamedQuery(name = "VtToaHangDetail.findByReceiptId", query = "SELECT v FROM VtToaHangDetail v WHERE v.receiptId = :receiptId")
    , @NamedQuery(name = "VtToaHangDetail.findByFileAttach", query = "SELECT v FROM VtToaHangDetail v WHERE v.fileAttach = :fileAttach")
    , @NamedQuery(name = "VtToaHangDetail.findByGenDate", query = "SELECT v FROM VtToaHangDetail v WHERE v.genDate = :genDate")
    , @NamedQuery(name = "VtToaHangDetail.findByCreatedBy", query = "SELECT v FROM VtToaHangDetail v WHERE v.createdBy = :createdBy")
    , @NamedQuery(name = "VtToaHangDetail.findByLastUpdate", query = "SELECT v FROM VtToaHangDetail v WHERE v.lastUpdate = :lastUpdate")
    , @NamedQuery(name = "VtToaHangDetail.findByUpdatedBy", query = "SELECT v FROM VtToaHangDetail v WHERE v.updatedBy = :updatedBy")})
public class VtToaHangDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "toa_hang_id")
    private Integer toaHangId;
    @Column(name = "receipt_id")
    private Integer receiptId;
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

    public VtToaHangDetail() {
    }

    public VtToaHangDetail(Integer id) {
        this.id = id;
    }

    public VtToaHangDetail(Integer id, Date genDate) {
        this.id = id;
        this.genDate = genDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getToaHangId() {
        return toaHangId;
    }

    public void setToaHangId(Integer toaHangId) {
        this.toaHangId = toaHangId;
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
        if (!(object instanceof VtToaHangDetail)) {
            return false;
        }
        VtToaHangDetail other = (VtToaHangDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.osp.model.VtToaHangDetail[ id=" + id + " ]";
    }
    
}
