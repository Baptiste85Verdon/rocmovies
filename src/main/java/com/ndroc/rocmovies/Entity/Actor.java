package com.ndroc.rocmovies.Entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer actorId;
    private String lastname;
    private String firstname;
    private String birthdate;

    @OneToMany(mappedBy = "actor")
    private List<Role> roles = new ArrayList<>();

    public Actor() {}

    public Actor(int actorId, String lastname, String firstname, String birthdate, List<Role> roles) {
        this.actorId = actorId;
        this.lastname = lastname;
        this.firstname = firstname;
        this.birthdate = birthdate;
        this.roles = roles;
    }

    public int getActorId() {
        return actorId;
    }
    public void setActorId(int actorId) {
        this.actorId = actorId;
    }

    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getBirthdate() {
        return birthdate;
    }
    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public List<Role> getRoles() {
        return roles;
    }
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }



}
