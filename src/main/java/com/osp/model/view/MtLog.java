package com.osp.model.view;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Date;

@Entity
public class MtLog {
    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "SOURCE_MT")
    private String sourceMt;
    @Column(name = "REQUEST_ID")
    private String requestId;
    @Column(name = "SENDER_NUMBER")
    private String senderNumber;
    @Column(name = "SERVICE_NUMBER")
    private String serviceNumber;
    @Column(name = "MOBILE_OPERATOR")
    private String mobileOperator;
    @Column(name = "COMMAND_CODE")
    private String commandCode;
    @Column(name = "CONTENT_TYPE")
    private Integer contentType;
    @Column(name = "MESSAGE_TYPE")
    private Integer messageType;
    @Column(name = "INFO")
    private String info;
    @Column(name = "GEN_DATE")
    private Date genDate;
    @Column(name = "MP_TRANSID")
    private String mpTransId;
    @Column(name = "PROCESS_RESULT")
    private String processResult;
    @Column(name = "QUEUE_TIME")
    private Timestamp queueTime;
    @Column(name = "SUBMIT_TIME")
    private Timestamp submitTime;
    @Column(name = "RESP_TIME")
    private Timestamp respTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceMt() {
        return sourceMt;
    }

    public void setSourceMt(String sourceMt) {
        this.sourceMt = sourceMt;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
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

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getGenDate() {
        return genDate;
    }

    public void setGenDate(Date genDate) {
        this.genDate = genDate;
    }

    public String getMpTransId() {
        return mpTransId;
    }

    public void setMpTransId(String mpTransId) {
        this.mpTransId = mpTransId;
    }

    public String getProcessResult() {
        return processResult;
    }

    public void setProcessResult(String processResult) {
        this.processResult = processResult;
    }

    public Timestamp getQueueTime() {
        return queueTime;
    }

    public void setQueueTime(Timestamp queueTime) {
        this.queueTime = queueTime;
    }

    public Timestamp getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Timestamp submitTime) {
        this.submitTime = submitTime;
    }

    public Timestamp getRespTime() {
        return respTime;
    }

    public void setRespTime(Timestamp respTime) {
        this.respTime = respTime;
    }
}
