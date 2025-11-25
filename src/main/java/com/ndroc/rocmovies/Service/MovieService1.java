package com.ndroc.rocmovies.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ndroc.rocmovies.Entity.Movie;
import com.ndroc.rocmovies.Entity.Style;
import com.ndroc.rocmovies.Entity.Borrow;
import com.ndroc.rocmovies.Entity.BorrowStatus;
import com.ndroc.rocmovies.Entity.Customer;
import com.ndroc.rocmovies.Repositories.BorrowRepository;
import com.ndroc.rocmovies.Repositories.CustomerRepository;
import com.ndroc.rocmovies.Repositories.MovieRepositoryJPA;
import com.ndroc.rocmovies.Repositories.StyleRepository;

import jakarta.transaction.Transactional;


@Service(value="Service1")
public class MovieService1 implements IMovieService {


    @Autowired
    private MovieRepositoryJPA movieRepositoryJPA;

    @Autowired
    private StyleRepository styleRepository;

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private CustomerRepository customerRepository;
    
    /** 
     * Liste complète de tous les films
     */
    @Override
    public List<Movie> getListMovies(){
        return movieRepositoryJPA.findAll();
    }

    /**
     * Liste de tous les filmes avec le style passé en param
     * @param style
     * @return
     */
    @Override
    public List<Movie> getListMoviesByStyle(Style style){
        List<Movie> ListMoviesByStyle = new ArrayList<>();
        for (Movie movie : getListMovies()) {
            if(movie.getStyle().equals(style)){
                ListMoviesByStyle.add(movie);
            }
        }
        return ListMoviesByStyle;
    }


    @Override
    public Optional<Movie> getMovieById(long id){
        return movieRepositoryJPA.findById((int)id);
    }


    public MovieService1(){
        System.out.println("Création du service MovieService1");
    }

    

    @Override
    public void addMovie(Movie movie) {
        movieRepositoryJPA.save(movie);
    }

    @Override
    public void deleteMovie(long id) {
        movieRepositoryJPA.deleteById((int)id);
    }

    @Override
    public List<Movie> getListMoviesBetweenYears(int yearMin, int yearMax) {
        List<Movie> moviesBetweenYears = new ArrayList<>();
        for (Movie movie : getListMovies()) {
            if (movie.getProductionYear() >= yearMin && movie.getProductionYear() <= yearMax) {
                moviesBetweenYears.add(movie);
            }
        }
        return moviesBetweenYears;
    }

    @Override
    public Optional<Style> getStyleByName(String name) {
        Style style = styleRepository.findByStyleName(name);
        return style != null ? Optional.of(style) : Optional.empty();
    }

    /** 
     * Liste complète de tous les styles
     */
    @Override
    public List<Style> getListStyles(){
        return styleRepository.findAll();
    }

    @Override
    public List<Movie> getAllBorrowedMoviesByCustomerId(long customerId) {
        return borrowRepository.findBorrowedMoviesByCustomerId((int) customerId);
    }

    @Override
    public void addBorrowToCustomer(Borrow borrow) {
        // Vérifier que le film existe
        if (borrow.getMovie() == null) {
            throw new RuntimeException("Le film doit être spécifié");
        }
        
        Optional<Movie> movieOpt = movieRepositoryJPA.findById(borrow.getMovie().getMovieId());
        if (!movieOpt.isPresent()) {
            throw new RuntimeException("Film avec l'id " + borrow.getMovie().getMovieId() + " n'existe pas");
        }
        
        // Vérifier que le customer existe (si nécessaire)
        if (borrow.getCustomer() == null) {
            throw new RuntimeException("Le client doit être spécifié");
        }
        
        // S'assurer que le statut est défini
        if (borrow.getStatus() == null) {
            borrow.setStatus(BorrowStatus.EN_COURS);
        }
        
        // Utiliser le film de la base de données pour éviter les problèmes de références
        borrow.setMovie(movieOpt.get());
        
        borrowRepository.save(borrow);
    }

    @Transactional
    public void transferAllBorrows(int customerIdFrom, int customerIdTo) {
        List<Borrow> borrowsToTransfer = borrowRepository.findByCustomerCustomerId(customerIdFrom);
        for (Borrow borrow : borrowsToTransfer) {
            borrow.getCustomer().setCustomerId(customerIdTo);
            borrowRepository.save(borrow);
        }
    }

    /** 
     * Liste complète de tous les customers
     */
    @Override
    public List<Customer> getListCustomers(){
        return customerRepository.findAll();
    }

    
}