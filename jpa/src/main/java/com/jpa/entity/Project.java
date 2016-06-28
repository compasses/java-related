package com.jpa.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by i311352 on 6/19/16.
 */
@Entity
@Table(name = "T_PROJECT")
public class Project {
    private Long id;
    private String title;
    private List<Geek> geeks = new ArrayList<Geek>();
    private Period projectPeriod;
    private ProjectType projectType;

    public enum ProjectType{
        FIXED, TIME_AND_MERTERIAL
    }

    @Enumerated(EnumType.ORDINAL)
    public ProjectType getProjectType() {
        return projectType;
    }

    public void setProjectType(ProjectType projectType) {
        this.projectType = projectType;
    }

    @Embedded
    public Period getProjectPeriod() {
        return projectPeriod;
    }

    public void setProjectPeriod(Period projectPeriod) {
        this.projectPeriod = projectPeriod;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @ManyToMany(mappedBy = "projects")
    public List<Geek> getGeeks() {
        return geeks;
    }

    public void setGeeks(List<Geek> geeks) {
        this.geeks = geeks;
    }
}
