package com.ndroc.rocmovies.Entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Style {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer styleId;
    private String styleName;

    @OneToMany(mappedBy = "style")
    @JsonIgnore
    private List<Movie> movies = new ArrayList<>();



    public Style() {}

    public Style( int styleId, String styleName) {
        this.styleId = styleId;
        this.styleName = styleName;
    }

    public int getStyleId() {
        return styleId;
    }

    public void setStyleId(int styleId) {
        this.styleId = styleId;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public List<Movie> getMovies() {
        return movies;
    }


}
