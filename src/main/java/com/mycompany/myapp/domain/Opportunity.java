package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Opportunity.
 */
@Entity
@Table(name = "opportunity")
public class Opportunity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "sol_num")
    private String solNum;

    @Column(name = "noticeid")
    private String noticeid;

    @Column(name = "state")
    private String state;

    @Column(name = "zip")
    private String zip;

    @Column(name = "posted_from")
    private LocalDate postedFrom;

    @Column(name = "posted_to")
    private LocalDate postedTo;

    @Column(name = "reponse_dead_line")
    private String reponseDeadLine;

    @Column(name = "classification_code")
    private String classificationCode;

    @ManyToOne
    @JsonIgnoreProperties(value = "opportunities", allowSetters = true)
    private Naics naics;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Opportunity title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSolNum() {
        return solNum;
    }

    public Opportunity solNum(String solNum) {
        this.solNum = solNum;
        return this;
    }

    public void setSolNum(String solNum) {
        this.solNum = solNum;
    }

    public String getNoticeid() {
        return noticeid;
    }

    public Opportunity noticeid(String noticeid) {
        this.noticeid = noticeid;
        return this;
    }

    public void setNoticeid(String noticeid) {
        this.noticeid = noticeid;
    }

    public String getState() {
        return state;
    }

    public Opportunity state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public Opportunity zip(String zip) {
        this.zip = zip;
        return this;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public LocalDate getPostedFrom() {
        return postedFrom;
    }

    public Opportunity postedFrom(LocalDate postedFrom) {
        this.postedFrom = postedFrom;
        return this;
    }

    public void setPostedFrom(LocalDate postedFrom) {
        this.postedFrom = postedFrom;
    }

    public LocalDate getPostedTo() {
        return postedTo;
    }

    public Opportunity postedTo(LocalDate postedTo) {
        this.postedTo = postedTo;
        return this;
    }

    public void setPostedTo(LocalDate postedTo) {
        this.postedTo = postedTo;
    }

    public String getReponseDeadLine() {
        return reponseDeadLine;
    }

    public Opportunity reponseDeadLine(String reponseDeadLine) {
        this.reponseDeadLine = reponseDeadLine;
        return this;
    }

    public void setReponseDeadLine(String reponseDeadLine) {
        this.reponseDeadLine = reponseDeadLine;
    }

    public String getClassificationCode() {
        return classificationCode;
    }

    public Opportunity classificationCode(String classificationCode) {
        this.classificationCode = classificationCode;
        return this;
    }

    public void setClassificationCode(String classificationCode) {
        this.classificationCode = classificationCode;
    }

    public Naics getNaics() {
        return naics;
    }

    public Opportunity naics(Naics naics) {
        this.naics = naics;
        return this;
    }

    public void setNaics(Naics naics) {
        this.naics = naics;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Opportunity)) {
            return false;
        }
        return id != null && id.equals(((Opportunity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Opportunity{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", solNum='" + getSolNum() + "'" +
            ", noticeid='" + getNoticeid() + "'" +
            ", state='" + getState() + "'" +
            ", zip='" + getZip() + "'" +
            ", postedFrom='" + getPostedFrom() + "'" +
            ", postedTo='" + getPostedTo() + "'" +
            ", reponseDeadLine='" + getReponseDeadLine() + "'" +
            ", classificationCode='" + getClassificationCode() + "'" +
            "}";
    }
}
