package com.jpa.entity;

import javax.persistence.*;

/**
 * Created by i311352 on 6/19/16.
 */
@Entity
@Table(name = "T_PHONE")
public class Phone {
    private Long id;
    private String number;
    private Person person;

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "NUMBER")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSON_ID")
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
