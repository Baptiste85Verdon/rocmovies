package com.ndroc.rocmovies.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;
    private String role;

    @ManyToOne
    @JoinColumn(name="actor_id", nullable = false)
    private Actor actor;

    @ManyToOne
    @JoinColumn(name="movie_id", nullable = false)
    private Movie movie;

    public Role() {}

    public Role(int roleId, String role, Actor actor, Movie movie) {
        this.roleId = roleId;
        this.role = role;
        this.actor = actor;
        this.movie = movie;
    }

    public int getRoleId() {
        return roleId;
    }
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public Actor getActor() {
        return actor;
    }
    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Movie getMovie() {
        return movie;
    }
    public void setMovie(Movie movie) {
        this.movie = movie;
    }

}
