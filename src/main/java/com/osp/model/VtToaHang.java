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
@Table(name = "vt_toa_hang")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VtToaHang.findAll", query = "SELECT v FROM VtToaHang v")
    , @NamedQuery(name = "VtToaHang.findById", query = "SELECT v FROM VtToaHang v WHERE v.id = :id")
    , @NamedQuery(name = "VtToaHang.findByToaHangCode", query = "SELECT v FROM VtToaHang v WHERE v.toaHangCode = :toaHangCode")
    , @NamedQuery(name = "VtToaHang.findByFromWhere", query = "SELECT v FROM VtToaHang v WHERE v.fromWhere = :fromWhere")
    , @NamedQuery(name = "VtToaHang.findByToWhere", query = "SELECT v FROM VtToaHang v WHERE v.toWhere = :toWhere")
    , @NamedQuery(name = "VtToaHang.findByBienSo", query = "SELECT v FROM VtToaHang v WHERE v.bienSo = :bienSo")
    , @NamedQuery(name = "VtToaHang.findByPartnerId", query = "SELECT v FROM VtToaHang v WHERE v.partnerId = :partnerId")
    , @NamedQuery(name = "VtToaHang.findByNguoiNhan", query = "SELECT v FROM VtToaHang v WHERE v.nguoiNhan = :nguoiNhan")
    , @NamedQuery(name = "VtToaHang.findByNoiNhan", query = "SELECT v FROM VtToaHang v WHERE v.noiNhan = :noiNhan")
    , @NamedQuery(name = "VtToaHang.findByGenDate", query = "SELECT v FROM VtToaHang v WHERE v.genDate = :genDate")
    , @NamedQuery(name = "VtToaHang.findByCreatedBy", query = "SELECT v FROM VtToaHang v WHERE v.createdBy = :createdBy")
    , @NamedQuery(name = "VtToaHang.findByLastUpdate", query = "SELECT v FROM VtToaHang v WHERE v.lastUpdate = :lastUpdate")
    , @NamedQuery(name = "VtToaHang.findByUpdatedBy", query = "SELECT v FROM VtToaHang v WHERE v.updatedBy = :updatedBy")})
public class VtToaHang implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "toa_hang_code", length = 200)
    private String toaHangCode;
    @Column(name = "from_where", length = 500)
    private String fromWhere;
    @Column(name = "to_where" , length = 500)
    private String toWhere;
    @Column(name = "bien_so", length = 50)
    private String bienSo;
    @Column(name = "partner_id")
    private Integer partnerId;
    @Column(name = "nguoi_nhan", length = 200)
    private String nguoiNhan;
    @Column(name = "noi_nhan", length = 500)
    private String noiNhan;
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

    public VtToaHang() {
    }

    public VtToaHang(Integer id) {
        this.id = id;
    }

    public VtToaHang(Integer id, Date genDate) {
        this.id = id;
        this.genDate = genDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToaHangCode() {
        return toaHangCode;
    }

    public void setToaHangCode(String toaHangCode) {
        this.toaHangCode = toaHangCode;
    }

    public String getFromWhere() {
        return fromWhere;
    }

    public void setFromWhere(String fromWhere) {
        this.fromWhere = fromWhere;
    }

    public String getToWhere() {
        return toWhere;
    }

    public void setToWhere(String toWhere) {
        this.toWhere = toWhere;
    }

    public String getBienSo() {
        return bienSo;
    }

    public void setBienSo(String bienSo) {
        this.bienSo = bienSo;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public String getNguoiNhan() {
        return nguoiNhan;
    }

    public void setNguoiNhan(String nguoiNhan) {
        this.nguoiNhan = nguoiNhan;
    }

    public String getNoiNhan() {
        return noiNhan;
    }

    public void setNoiNhan(String noiNhan) {
        this.noiNhan = noiNhan;
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
        if (!(object instanceof VtToaHang)) {
            return false;
        }
        VtToaHang other = (VtToaHang) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.osp.model.VtToaHang[ id=" + id + " ]";
    }
    
}
