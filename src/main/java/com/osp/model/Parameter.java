package com.osp.model;

import com.osp.common.Utils;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "ADM_PARAMETER")
public class Parameter {

    private Long id;
    private String key;
    private String value;
    private String description;
    private Long userId;
    private Date genDate;
    private Long lastUserId;
    private Date lastUpdate;

    private String str_gen_date;
    private String str_last_update;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "P_KEY", unique = true, nullable = false, length = 50)
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Column(name = "VALUE", nullable = false, length = 200)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Column(name = "DESCRIPTION", length = 500)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "USER_ID")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "GEN_DATE")
    public Date getGenDate() {
        return genDate;
    }

    public void setGenDate(Date genDate) {
        this.genDate = genDate;
        this.str_gen_date = Utils.date2str(genDate, "dd/MM/yyyy hh:mm:ss");
    }

    @Column(name = "LAST_USERID")
    public Long getLastUserId() {
        return lastUserId;
    }

    public void setLastUserId(Long lastUserId) {
        this.lastUserId = lastUserId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_UPDATE")
    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
        this.str_last_update = Utils.date2str(lastUpdate, "dd/MM/yyyy hh:mm:ss");
    }

    @Transient
    public String getStr_gen_date() {
        return str_gen_date;
    }

    public void setStr_gen_date(String str_gen_date) throws ParseException {
        this.str_gen_date = str_gen_date;
        this.genDate = Utils.str2date(str_gen_date, "dd/MM/yyyy hh:mm:ss");
    }

    @Transient
    public String getStr_last_update() {
        return str_last_update;
    }

    public void setStr_last_update(String str_last_update) throws ParseException {
        this.str_last_update = str_last_update;
        this.lastUpdate = Utils.str2date(str_last_update, "dd/MM/yyyy hh:mm:ss");
    }
}
