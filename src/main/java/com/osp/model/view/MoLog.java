package com.osp.model.view;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
@Entity
public class MoLog {
    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "SENDER_NUMBER")
    private String senderNumber;
    @Column(name = "SERVICE_NUMBER")
    private String serviceNumber;
    @Column(name = "MOBILE_OPERATOR")
    private String mobileOperator;
    @Column(name = "COMMAND_CODE")
    private String commandCode;
    @Column(name = "INFO")
    private String info;
    @Column(name = "REQUEST_ID")
    private String requestId;
    @Column(name = "GEN_DATE")
    private Timestamp genDate;
    @Column(name = "RESULT_ACTION")
    private String resultAction;
    @Column(name = "INVITE_DATE")
    private Timestamp inviteDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSenderNumber() {
        return senderNumber;
    }

    public void setSenderNumber(String senderNumber) {
        this.senderNumber = senderNumber;
    }

    public String getServiceNumber() {
        return serviceNumber;
    }

    public void setServiceNumber(String serviceNumber) {
        this.serviceNumber = serviceNumber;
    }

    public String getMobileOperator() {
        return mobileOperator;
    }

    public void setMobileOperator(String mobileOperator) {
        this.mobileOperator = mobileOperator;
    }

    public String getCommandCode() {
        return commandCode;
    }

    public void setCommandCode(String commandCode) {
        this.commandCode = commandCode;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Timestamp getGenDate() {
        return genDate;
    }

    public void setGenDate(Timestamp genDate) {
        this.genDate = genDate;
    }

    public String getResultAction() {
        return resultAction;
    }

    public void setResultAction(String resultAction) {
        this.resultAction = resultAction;
    }

    public Timestamp getInviteDate() {
        return inviteDate;
    }

    public void setInviteDate(Timestamp inviteDate) {
        this.inviteDate = inviteDate;
    }
}
