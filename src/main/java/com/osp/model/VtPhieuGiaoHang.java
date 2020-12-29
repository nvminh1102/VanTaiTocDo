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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "vt_phieu_giao_hang")
@XmlRootElement
public class VtPhieuGiaoHang implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "ma_phieu_giao")
    private String maPhieuGiao;
    @Column(name = "nha_xe", length = 200)
    private String nhaXe;
    @Column(name = "loai_xe", length = 200)
    private String loaiXe;
    @Column(name = "bien_so", length = 50)
    private String bienSo;
    @Column(name = "ten_lai_xe", length = 200)
    private String tenLaiXe;
    @Column(name = "sdt_lai_xe", length = 20)
    private String sdtLaiXe;
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

    public VtPhieuGiaoHang() {
    }

    public VtPhieuGiaoHang(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaPhieuGiao() {
        return maPhieuGiao;
    }

    public void setMaPhieuGiao(String maPhieuGiao) {
        this.maPhieuGiao = maPhieuGiao;
    }

    public String getNhaXe() {
        return nhaXe;
    }

    public void setNhaXe(String nhaXe) {
        this.nhaXe = nhaXe;
    }

    public String getLoaiXe() {
        return loaiXe;
    }

    public void setLoaiXe(String loaiXe) {
        this.loaiXe = loaiXe;
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
        if (!(object instanceof VtPhieuGiaoHang)) {
            return false;
        }
        VtPhieuGiaoHang other = (VtPhieuGiaoHang) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.osp.model.VtPhieuGiaoHang[ id=" + id + " ]";
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
