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
@Table(name = "vt_cong_no_detail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VtCongNoDetail.findAll", query = "SELECT v FROM VtCongNoDetail v")
    , @NamedQuery(name = "VtCongNoDetail.findById", query = "SELECT v FROM VtCongNoDetail v WHERE v.id = :id")
    , @NamedQuery(name = "VtCongNoDetail.findByCongNoId", query = "SELECT v FROM VtCongNoDetail v WHERE v.congNoId = :congNoId")
    , @NamedQuery(name = "VtCongNoDetail.findByReceiptId", query = "SELECT v FROM VtCongNoDetail v WHERE v.receiptId = :receiptId")
    , @NamedQuery(name = "VtCongNoDetail.findByFileAttach", query = "SELECT v FROM VtCongNoDetail v WHERE v.fileAttach = :fileAttach")
    , @NamedQuery(name = "VtCongNoDetail.findByGenDate", query = "SELECT v FROM VtCongNoDetail v WHERE v.genDate = :genDate")
    , @NamedQuery(name = "VtCongNoDetail.findByCreatedBy", query = "SELECT v FROM VtCongNoDetail v WHERE v.createdBy = :createdBy")
    , @NamedQuery(name = "VtCongNoDetail.findByLastUpdate", query = "SELECT v FROM VtCongNoDetail v WHERE v.lastUpdate = :lastUpdate")
    , @NamedQuery(name = "VtCongNoDetail.findByUpdatedBy", query = "SELECT v FROM VtCongNoDetail v WHERE v.updatedBy = :updatedBy")})
public class VtCongNoDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "cong_no_id")
    private Integer congNoId;
    @Column(name = "receipt_id")
    private Integer receiptId;
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

    public VtCongNoDetail() {
    }

    public VtCongNoDetail(Integer id) {
        this.id = id;
    }

    public VtCongNoDetail(Integer id, Date genDate) {
        this.id = id;
        this.genDate = genDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCongNoId() {
        return congNoId;
    }

    public void setCongNoId(Integer congNoId) {
        this.congNoId = congNoId;
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
        if (!(object instanceof VtCongNoDetail)) {
            return false;
        }
        VtCongNoDetail other = (VtCongNoDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.osp.model.VtCongNoDetail[ id=" + id + " ]";
    }
    
}
