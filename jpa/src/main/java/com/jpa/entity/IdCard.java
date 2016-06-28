package com.jpa.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by i311352 on 6/19/16.
 */
@Entity
@Table(name = "T_ID_CARD")
public class IdCard {
    private Long Id;
    private String idNumber;
    private Date issueDate;

    @Column(name = "ID_NUMBER")
    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
    @Column(name = "ISSUE_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

}
