package com.ndroc.rocmovies.Controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ndroc.rocmovies.Entity.Movie;
import com.ndroc.rocmovies.Entity.Style;
import com.ndroc.rocmovies.Entity.Borrow;
import com.ndroc.rocmovies.Entity.Customer;
import com.ndroc.rocmovies.Service.IMovieService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
public class MovieController {

    //public MovieService movieService = new MovieService();
    @Autowired
    @Qualifier("Service2")
    private IMovieService movieService;

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello world from my first controller !";
    }
    

    @GetMapping("/movies")
    public List<Movie> getListMovies(
        @RequestParam("style") Optional<String> styleParam)
    {
        Optional<Style> style = Optional.empty();
        if(styleParam.isPresent()){
            Optional<Style> foundStyle = movieService.getStyleByName(styleParam.get());
            if(foundStyle.isPresent()){
                style = foundStyle;
            }else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le style " + styleParam.get() + " n'existe pas !");
            }
        }
        List<Movie> list;
        if(style.isPresent()){
            list = movieService.getListMoviesByStyle(style.get());
            if(list.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun film trouvé pour le style " + style.get() + " !");
            }
        }else{
            list = movieService.getListMovies();
            if(list.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun film trouvé !");
            }
        }
        return list;
    }

    @GetMapping("/movie/{id}")
    public Movie getMovieById(@PathVariable long id)
    {
        Optional<Movie> optional = movieService.getMovieById(id);
        if(optional.isPresent())
        {
            return optional.get();
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Il n'existe aucun film avec l'id "+id+" !");
        }
    }

    @PostMapping("/movie/add")
    public ResponseEntity<Void> addMovie(@RequestBody @Valid Movie movie) 
    {    
        movieService.addMovie(movie);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/movie/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable long id, @RequestParam(required = false, defaultValue = "false") boolean confirm) 
    {
        if (!confirm) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
                "Pour supprimer un film, ajoutez ?confirm=true à l'URL");
        }
        
        Optional<Movie> optional = movieService.getMovieById(id);
        if(optional.isPresent()) {
            movieService.deleteMovie(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Il n'existe aucun film avec l'id "+id+" !");
        }
    }



    @GetMapping("/customers/{id}/borrows-customer")
    public List<Movie> getAllBorrowedMoviesByCustomerId(@PathVariable long id)
    {
        List<Movie> borrowedMovies = movieService.getAllBorrowedMoviesByCustomerId(id);
        if(borrowedMovies.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
                "Aucun film emprunté trouvé pour le client avec l'id "+id+" !");
        }
        return borrowedMovies;
    }
    
    @PostMapping("/customers/{id}/borrow")
    public ResponseEntity<Void> addBorrowToCustomer(@PathVariable long id, @RequestBody Borrow borrow) 
    {    
        try {
            // S'assurer que le customerId correspond au path variable
            if (borrow.getCustomer() == null) {
                borrow.setCustomer(new Customer());
            }
            borrow.getCustomer().setCustomerId((int) id);
            
            movieService.addBorrowToCustomer(borrow);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/customers")
    public List<Customer> getListCustomers()
    {
        List<Customer> list;
        list = movieService.getListCustomers();
        if(list.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun client trouvé !");
        }
        return list;
    }
}
