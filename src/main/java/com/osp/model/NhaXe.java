package com.osp.model;

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
@Entity
@Table(name = "vt_nha_xe")
public class NhaXe {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "ID")
  private Integer id;
  @Column(name = "nha_xe")
  private String nhaXe;
  @Column(name = "loai_xe")
  private String loaiXe;
  @Column(name = "bien_so")
  private String bienSo;
  @Column(name = "ten_lai_xe")
  private String tenLaiXe;
  @Column(name = "sdt_lai_xe")
  private String sdtLaiXe;
  @Column(name = "gen_date")
  @Temporal(TemporalType.TIMESTAMP)
  private Date genDate;
  @Column(name = "created_by")
  private String createdBy;
  @Column(name = "last_update")
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastUpdate;
  @Column(name = "updated_by")
  private String updatedBy;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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
}
