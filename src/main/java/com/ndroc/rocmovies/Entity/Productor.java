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
public class Productor {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productorId;
    private String name;

    @OneToMany(mappedBy = "productor")
    @JsonIgnore
    private List<Movie> movies = new ArrayList<>();


    public Productor() {}

    public Productor(int productorId, String name) {
        this.productorId = productorId;
        this.name = name;
    }

    public int getProductorId() {
        return productorId;
    }
    public void setProductorId(int productorId) {
        this.productorId = productorId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<Movie> getMovies() {
        return movies;
    }
    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
