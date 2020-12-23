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
@Table(name = "vt_cong_no")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VtCongNo.findAll", query = "SELECT v FROM VtCongNo v")
    , @NamedQuery(name = "VtCongNo.findById", query = "SELECT v FROM VtCongNo v WHERE v.id = :id")
    , @NamedQuery(name = "VtCongNo.findByCongNoCode", query = "SELECT v FROM VtCongNo v WHERE v.congNoCode = :congNoCode")
    , @NamedQuery(name = "VtCongNo.findByDateCongNo", query = "SELECT v FROM VtCongNo v WHERE v.dateCongNo = :dateCongNo")
    , @NamedQuery(name = "VtCongNo.findByDateExp", query = "SELECT v FROM VtCongNo v WHERE v.dateExp = :dateExp")
    , @NamedQuery(name = "VtCongNo.findByGenDate", query = "SELECT v FROM VtCongNo v WHERE v.genDate = :genDate")
    , @NamedQuery(name = "VtCongNo.findByCreatedBy", query = "SELECT v FROM VtCongNo v WHERE v.createdBy = :createdBy")
    , @NamedQuery(name = "VtCongNo.findByLastUpdate", query = "SELECT v FROM VtCongNo v WHERE v.lastUpdate = :lastUpdate")
    , @NamedQuery(name = "VtCongNo.findByUpdatedBy", query = "SELECT v FROM VtCongNo v WHERE v.updatedBy = :updatedBy")})
public class VtCongNo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "cong_no_code", length = 200)
    private String congNoCode;
    @Basic(optional = false)
    @Column(name = "date_cong_no")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCongNo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_exp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateExp;
    @Basic(optional = false)
    @NotNull
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

    public VtCongNo() {
    }

    public VtCongNo(Integer id) {
        this.id = id;
    }

    public VtCongNo(Integer id, Date dateCongNo, Date dateExp, Date genDate) {
        this.id = id;
        this.dateCongNo = dateCongNo;
        this.dateExp = dateExp;
        this.genDate = genDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCongNoCode() {
        return congNoCode;
    }

    public void setCongNoCode(String congNoCode) {
        this.congNoCode = congNoCode;
    }

    public Date getDateCongNo() {
        return dateCongNo;
    }

    public void setDateCongNo(Date dateCongNo) {
        this.dateCongNo = dateCongNo;
    }

    public Date getDateExp() {
        return dateExp;
    }

    public void setDateExp(Date dateExp) {
        this.dateExp = dateExp;
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
        if (!(object instanceof VtCongNo)) {
            return false;
        }
        VtCongNo other = (VtCongNo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.osp.model.VtCongNo[ id=" + id + " ]";
    }
    
}
