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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "vt_toa_hang")
public class VtToaHang implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "toa_hang_code", length = 200)
    private String toaHangCode;
    @Column(name = "noi_di", length = 500)
    private String noiDi;
    @Column(name = "noi_den", length = 500)
    private String noiDen;
    @Column(name = "bien_so", length = 50)
    private String bienSo;
    @Column(name = "ten_lai_xe", length = 200)
    private String tenLaiXe;
    @Column(name = "sdt_lai_xe", length = 20)
    private String sdtLaiXe;
    @Column(name = "nguoi_nhan", length = 200)
    private String nguoiNhan;
    @Column(name = "noi_nhan", length = 500)
    private String noiNhan;
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
    private Date fromGenDate;
    @Transient
    private Date toGenDate;

    public VtToaHang() {
    }

    public VtToaHang(Integer id) {
        this.id = id;
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

    public String getNoiDi() {
        return noiDi;
    }

    public void setNoiDi(String noiDi) {
        this.noiDi = noiDi;
    }

    public String getNoiDen() {
        return noiDen;
    }

    public void setNoiDen(String noiDen) {
        this.noiDen = noiDen;
    }

    public String getBienSo() {
        return bienSo;
    }

    public void setBienSo(String bienSo) {
        this.bienSo = bienSo;
    }

    public String getTenLaiXe() {
        return tenLaiXe;
    }

    public void setTenLaiXe(String tenLaiXe) {
        this.tenLaiXe = tenLaiXe;
    }

    public String getSdtLaiXe() {
        return sdtLaiXe;
    }

    public void setSdtLaiXe(String sdtLaiXe) {
        this.sdtLaiXe = sdtLaiXe;
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

    public Date getFromGenDate() {
        return fromGenDate;
    }

    public void setFromGenDate(Date fromGenDate) {
        this.fromGenDate = fromGenDate;
    }

    public Date getToGenDate() {
        return toGenDate;
    }

    public void setToGenDate(Date toGenDate) {
        this.toGenDate = toGenDate;
    }
    
    
}
