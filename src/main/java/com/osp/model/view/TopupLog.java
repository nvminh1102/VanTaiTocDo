package com.osp.model.view;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class TopupLog {
    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "MSISDN_ID")
    private String msisdnId;
    @Column(name = "AMOUNT")
    private Long amount;
    @Column(name = "TIME_MARK")
    private Date timeMark;
    @Column(name = "SOURCE")
    private String source;
    @Column(name = "PROCESS_RESULT")
    private String processResult;
    @Column(name = "FILE_NAME ")
    private String fileName;
    @Column(name = "GEN_DATE")
    private Date genDate;
    @Column(name = "LAST_UPDATED")
    private Date lastUpdated;

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

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Date getTimeMark() {
        return timeMark;
    }

    public void setTimeMark(Date timeMark) {
        this.timeMark = timeMark;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getProcessResult() {
        return processResult;
    }

    public void setProcessResult(String processResult) {
        this.processResult = processResult;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getGenDate() {
        return genDate;
    }

    public void setGenDate(Date genDate) {
        this.genDate = genDate;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
