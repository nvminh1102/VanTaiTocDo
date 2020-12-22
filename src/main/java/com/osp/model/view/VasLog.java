package com.osp.model.view;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class VasLog {
    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "MSISDN_ID")
    private String msisdnId;
    @Column(name = "SERVICE_CODE")
    private String serviceCode;
    @Column(name = "COST")
    private Long cost;
    @Column(name = "CALL_STA_TIME")
    private Timestamp callStartTime;
    @Column(name = "TYPE")
    private String type;
    @Column(name = "INFO")
    private String info;
    @Column(name = "GEN_DATE")
    private Timestamp genDate;
    @Column(name = "PROCESS_RESULT")
    private String processResult;

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

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public Timestamp getCallStartTime() {
        return callStartTime;
    }

    public void setCallStartTime(Timestamp callStartTime) {
        this.callStartTime = callStartTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Timestamp getGenDate() {
        return genDate;
    }

    public void setGenDate(Timestamp genDate) {
        this.genDate = genDate;
    }

    public String getProcessResult() {
        return processResult;
    }

    public void setProcessResult(String processResult) {
        this.processResult = processResult;
    }
}
