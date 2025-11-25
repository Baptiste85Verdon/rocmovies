package com.ndroc.rocmovies.Entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Pattern;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;
    private String title;
    private Integer productionYear;

    @ManyToOne
    @JoinColumn(name="styleId", nullable = false)
    private Style style;

    @Pattern(regexp = "[a-zA-Z]{3}-[0-9]{6}")
    private String reference;

    @OneToMany(mappedBy = "movie")
    @JsonIgnore
    private List<Role> roles = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="producedBy", nullable = false)
    private Productor productor;


    @OneToMany(mappedBy = "movie")
    @JsonIgnore
    private List<Borrow> borrows = new ArrayList<>();

    public Movie() {}

    public Movie(int movieId, String title, int productionYear, Style style, String reference, Productor productor) {
        this.movieId = movieId;
        this.title = title;
        this.productionYear = productionYear;
        this.style = style;
        this.reference = reference;
        this.productor = productor;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    public String getReference() {
        return reference;
    }
    public void setReference(String reference) {
        this.reference = reference;
    }

    public Productor getProductor() {
        return productor;
    }

    public void setProductor(Productor productor) {
        this.productor = productor;
    }
    
    
    
    
}
