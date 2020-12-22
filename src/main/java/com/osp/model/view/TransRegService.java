package com.osp.model.view;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class TransRegService {
    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "MSISDN_ID")
    private String msisdnId;
    @Column(name = "REQ_TIME")
    private Long reqTime;
    @Column(name = "RESP_TIME")
    private Long respTime;
    @Column(name = "RESP_CODE")
    private String respCode;
    @Column(name = "RESP_DESC")
    private String respDesc;
    @Column(name = "TRANS_ID")
    private String transId;
    @Column(name = "TYPE")
    private Long type;
    @Column(name = "AMOUNT")
    private Long amount;
    @Column(name = "IS_DEREGISTER")
    private Long isDeregister;
    @Column(name = "PACK_CODE")
    private String packCode;
    @Column(name = "GEN_DATE")
    private Timestamp genDate;
    @Column(name = "LAST_UPDATED")
    private Timestamp lastUpdated;
    @Column(name = "CURR_CPOINT")
    private Long currCpoint;
    @Column(name = "INVITE_DATE")
    private Timestamp inviteDate;
    @Column(name = "RESP_TIME_INVITE")
    private Timestamp restTimeInvite;
    @Column(name = "DESCRIPTION")
    private String descriptionBlackList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsisdnId() {
        return msisdnId;
    }

    public void setMsisdnId(String msisdnId) {
        this.msisdnId = msisdnId;
    }

    public Long getReqTime() {
        return reqTime;
    }

    public void setReqTime(Long reqTime) {
        this.reqTime = reqTime;
    }

    public Long getRespTime() {
        return respTime;
    }

    public void setRespTime(Long respTime) {
        this.respTime = respTime;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespDesc() {
        return respDesc;
    }

    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getIsDeregister() {
        return isDeregister;
    }

    public void setIsDeregister(Long isDeregister) {
        this.isDeregister = isDeregister;
    }

    public String getPackCode() {
        return packCode;
    }

    public void setPackCode(String packCode) {
        this.packCode = packCode;
    }

    public Timestamp getGenDate() {
        return genDate;
    }

    public void setGenDate(Timestamp genDate) {
        this.genDate = genDate;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Long getCurrCpoint() {
        return currCpoint;
    }

    public void setCurrCpoint(Long currCpoint) {
        this.currCpoint = currCpoint;
    }

    public Timestamp getInviteDate() {
        return inviteDate;
    }

    public void setInviteDate(Timestamp inviteDate) {
        this.inviteDate = inviteDate;
    }

    public Timestamp getRestTimeInvite() {
        return restTimeInvite;
    }

    public void setRestTimeInvite(Timestamp restTimeInvite) {
        this.restTimeInvite = restTimeInvite;
    }

    public String getDescriptionBlackList() {
        return descriptionBlackList;
    }

    public void setDescriptionBlackList(String descriptionBlackList) {
        this.descriptionBlackList = descriptionBlackList;
    }
}
