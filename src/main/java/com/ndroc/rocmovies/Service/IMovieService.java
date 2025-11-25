package com.ndroc.rocmovies.Service;

import java.util.List;
import java.util.Optional;

import com.ndroc.rocmovies.Entity.Borrow;
import com.ndroc.rocmovies.Entity.Movie;
import com.ndroc.rocmovies.Entity.Style;
import com.ndroc.rocmovies.Entity.Customer;

public interface IMovieService {

    /** 
     * Liste complète de tous les films
     */
    List<Movie> getListMovies();

    /**
     * Liste de tous les filmes avec le style passé en param
     * @param style
     * @return
     */
    List<Movie> getListMoviesByStyle(Style style);

    Optional<Movie> getMovieById(long id);

    void addMovie(Movie movie);

    /**
     * Supprimer un film par son ID
     * @param id
     */
    void deleteMovie(long id);

    /**
     * Liste des films entre deux années
     * @param yearMin
     * @param yearMax
     * @return
     */
    List<Movie> getListMoviesBetweenYears(int yearMin, int yearMax);

    /**
     * Trouver un style par son nom
     * @param name
     * @return
     */
    Optional<Style> getStyleByName(String name);

    /**
     * Liste complète de tous les styles
     */
    List<Style> getListStyles();

    /**
     * Récupérer tous les films empruntés par un customer (en cours et terminés)
     * @param customerId
     * @return
     */
    List<Movie> getAllBorrowedMoviesByCustomerId(long customerId);

    /**
     * Ajouter un emprunt pour un customer
     * @param borrow l'objet emprunt à créer
     */
    void addBorrowToCustomer(Borrow borrow);

    /**
     * Transférer tous les emprunts d'un customer à un autre
     * @param customerIdFrom ID du customer source
     * @param customerIdTo ID du customer destination
     */
    void transferAllBorrows(int customerIdFrom, int customerIdTo);

    /** 
     * Liste complète de tous les customers
     */
    List<Customer> getListCustomers();

}