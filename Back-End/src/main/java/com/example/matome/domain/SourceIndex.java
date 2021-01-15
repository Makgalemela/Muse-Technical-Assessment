package com.example.matome.domain;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class SourceIndex {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Integer index;
    private String source;


    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
       this.index = index;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
