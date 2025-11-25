package com.ndroc.rocmovies.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ndroc.rocmovies.Entity.Style;

@Repository
public interface StyleRepository extends JpaRepository<Style, Integer> {
    Style findByStyleName(String styleName);
}