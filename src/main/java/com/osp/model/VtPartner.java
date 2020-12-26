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
@Table(name = "vt_partner")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VtPartner.findAll", query = "SELECT v FROM VtPartner v")
    , @NamedQuery(name = "VtPartner.findById", query = "SELECT v FROM VtPartner v WHERE v.id = :id")
    , @NamedQuery(name = "VtPartner.findByUserName", query = "SELECT v FROM VtPartner v WHERE v.userName = :userName")
    , @NamedQuery(name = "VtPartner.findByFullName", query = "SELECT v FROM VtPartner v WHERE v.fullName = :fullName")
    , @NamedQuery(name = "VtPartner.findByAddress", query = "SELECT v FROM VtPartner v WHERE v.address = :address")
    , @NamedQuery(name = "VtPartner.findByTaxCode", query = "SELECT v FROM VtPartner v WHERE v.taxCode = :taxCode")
    , @NamedQuery(name = "VtPartner.findByMobile", query = "SELECT v FROM VtPartner v WHERE v.mobile = :mobile")
    , @NamedQuery(name = "VtPartner.findByEmail", query = "SELECT v FROM VtPartner v WHERE v.email = :email")
    , @NamedQuery(name = "VtPartner.findByTypePartner", query = "SELECT v FROM VtPartner v WHERE v.typePartner = :typePartner")
    , @NamedQuery(name = "VtPartner.findByGenDate", query = "SELECT v FROM VtPartner v WHERE v.genDate = :genDate")
    , @NamedQuery(name = "VtPartner.findByCreatedBy", query = "SELECT v FROM VtPartner v WHERE v.createdBy = :createdBy")
    , @NamedQuery(name = "VtPartner.findByLastUpdate", query = "SELECT v FROM VtPartner v WHERE v.lastUpdate = :lastUpdate")
    , @NamedQuery(name = "VtPartner.findByUpdatedBy", query = "SELECT v FROM VtPartner v WHERE v.updatedBy = :updatedBy")})
public class VtPartner implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "USER_NAME", length = 200)
    private String userName;
    @Column(name = "FULL_NAME", length = 200)
    private String fullName;
    @Column(name = "address", length = 500)
    private String address;
    @Column(name = "tax_code", length = 200)
    private String taxCode;
    @Column(name = "MOBILE", length = 50)
    private String mobile;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Column(name = "EMAIL", length = 50)
    private String email;
    @Column(name = "type_partner")
    private Integer typePartner;
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

    public VtPartner() {
    }

    public VtPartner(Integer id) {
        this.id = id;
    }

    public VtPartner(Integer id, Date genDate) {
        this.id = id;
        this.genDate = genDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getTypePartner() {
        return typePartner;
    }

    public void setTypePartner(Integer typePartner) {
        this.typePartner = typePartner;
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
        if (!(object instanceof VtPartner)) {
            return false;
        }
        VtPartner other = (VtPartner) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.osp.model.VtPartner[ id=" + id + " ]";
    }
    
}
