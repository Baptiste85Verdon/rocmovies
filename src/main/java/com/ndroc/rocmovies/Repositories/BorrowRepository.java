package com.ndroc.rocmovies.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ndroc.rocmovies.Entity.Borrow;
import com.ndroc.rocmovies.Entity.Movie;

public interface BorrowRepository extends JpaRepository<Borrow, Integer> {

    /**
     * Trouver tous les emprunts d'un customer
     */
    List<Borrow> findByCustomerCustomerId(int customerId);

    @Query("SELECT DISTINCT b.movie FROM Borrow b WHERE b.customer.customerId = :customerId")
    List<Movie> findBorrowedMoviesByCustomerId(@Param("customerId") int customerId);
}