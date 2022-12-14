package com.cv.sc.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
public class Report implements SCEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jsonUrl;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private SearchParent searchParent;

    @NotNull
    @Temporal(TIMESTAMP)
    private Date executedDate;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Config config;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJsonUrl() {
        return jsonUrl;
    }

    public void setJsonUrl(String jsonUrl) {
        this.jsonUrl = jsonUrl;
    }

    public SearchParent getSearchParent() {
        return searchParent;
    }

    public void setSearchParent(SearchParent searchParent) {
        this.searchParent = searchParent;
    }

    public Date getExecutedDate() {
        return executedDate;
    }

    public void setExecutedDate(Date executedDate) {
        this.executedDate = executedDate;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", jsonUrl='" + jsonUrl + '\'' +
                ", searchParent=" + searchParent +
                ", executedDate=" + executedDate +
                ", config=" + config +
                '}';
    }
}
