package com.jpa.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by i311352 on 6/17/16.
 */
@Entity
@Table(name = "T_PERSON")
@Access(AccessType.PROPERTY)
public class Person {
    private Long id;
    private String firstName;
    private String lastName;
    private IdCard idCard;
    private List<Phone> phones = new ArrayList<Phone>();

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_CARD_ID")
    public IdCard getIdCard() {
        return idCard;
    }

    public void setIdCard(IdCard idCard) {
        this.idCard = idCard;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
