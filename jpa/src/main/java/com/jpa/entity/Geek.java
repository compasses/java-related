package com.jpa.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by i311352 on 6/19/16.
 */
@Entity
@Table(name = "T_GEEK")
public class Geek extends Person {

    private String favouriteProgrammingLanguage;
    private List<Project> projects = new ArrayList<Project>();

    @ManyToMany
    @JoinTable(name = "T_GEEK_PROJECT", joinColumns = {@JoinColumn(name = "GEEK_ID",referencedColumnName = "ID")},
    inverseJoinColumns = {@JoinColumn(name = "PROJECT_ID", referencedColumnName = "ID")})
    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Column(name = "FAV_PROG_LANG")
    public String getFavouriteProgrammingLanguage() {
        return favouriteProgrammingLanguage;
    }

    public void setFavouriteProgrammingLanguage(String favouriteProgrammingLanguage) {
        this.favouriteProgrammingLanguage = favouriteProgrammingLanguage;
    }

//    public void testQuery() {
//        EntityManager entityManager;
//        TypedQuery<Person> query = entityManager.createQuery("from Person", Person.class);
//        List<Person> resultList = query.getResultList();
//        for (Person person : resultList) {
//            StringBuilder sb = new StringBuilder();
//            sb.append(person.getFirstName()).append(" ").append(person.getLastName());
//            if (person instanceof Geek) {
//                Geek geek = (Geek)person;
//                sb.append(" ").append(geek.getFavouriteProgrammingLanguage());
//            }
//            Log.Logger LOGGER;
//            LOGGER.info(sb.toString());
//        }
//    }
}

