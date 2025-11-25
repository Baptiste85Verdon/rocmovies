package com.ndroc.rocmovies.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.ndroc.rocmovies.Entity.Movie;
import com.ndroc.rocmovies.Entity.Productor;
import com.ndroc.rocmovies.Entity.Style;
import com.ndroc.rocmovies.Entity.Borrow;
import com.ndroc.rocmovies.Entity.Customer;
import com.ndroc.rocmovies.Entity.BorrowStatus;
import com.ndroc.rocmovies.Repositories.MovieRepositoryJPA;
import com.ndroc.rocmovies.Repositories.StyleRepository;
import com.ndroc.rocmovies.Repositories.BorrowRepository;

@ExtendWith(MockitoExtension.class)
public class MovieService1Test {
    
    // Bouchon (mock) du MovieRepositoryJPA
    @Mock
    private MovieRepositoryJPA movieRepositoryJPA;

    @Mock
    private StyleRepository styleRepository;

    @Mock
    private BorrowRepository borrowRepository;
    
    // Injecte le mock dans le MovieService1
    @InjectMocks
    private MovieService1 movieService1;

    @Test
    void testAddMovie() {
        // Préparer le mock pour le service
        Style mockStyle = new Style(1, "SCI-FI");
        Productor mockProductor = new Productor(1, "Warner Bros");
        Movie mockMovie = new Movie( 1, "The Matrix", 1999, mockStyle, "REF123", mockProductor);

        movieService1.addMovie(mockMovie);
        
        // Vérifier que save a été appelé
        verify(movieRepositoryJPA).save(mockMovie);
    }

    @Test
    void testDeleteMovie() {
        // Préparer le mock pour le service
        int movieId = 1;

        movieService1.deleteMovie(movieId);
        
        // Vérifier que deleteById a été appelé
        verify(movieRepositoryJPA).deleteById(movieId);
    }

    @Test
    void testGetListMovies() {
        // Préparer le mock pour le repository
        Style mockStyle = new Style(1, "SCI-FI");
        Productor mockProductor = new Productor(1, "Warner Bros");
        Movie mockMovie1 = new Movie( 1, "The Matrix", 1999, mockStyle, "REF-123456", mockProductor);
        Movie mockMovie2 = new Movie( 2, "Inception", 2010, mockStyle, "REF-654321", mockProductor);
 
        when(movieRepositoryJPA.findAll()).thenReturn(Arrays.asList(mockMovie1, mockMovie2));

        // Appeler la méthode à tester
        var result = movieService1.getListMovies();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("The Matrix", result.get(0).getTitle());
        assertEquals("Inception", result.get(1).getTitle());
    }

    @Test
    void testGetListMoviesBetweenYears() {
        // Préparer le mock pour le repository
        Style mockStyle = new Style(1, "SCI-FI");
        Productor mockProductor = new Productor(1, "Warner Bros");
        Movie mockMovie1 = new Movie( 1, "The Matrix", 1999, mockStyle, "REF-123456", mockProductor);
        Movie mockMovie2 = new Movie( 2, "Inception", 2010, mockStyle, "REF-654321", mockProductor);
        Movie mockMovie3 = new Movie( 3, "Interstellar", 2014, mockStyle, "REF-789012", mockProductor);
 
        when(movieRepositoryJPA.findAll()).thenReturn(Arrays.asList(mockMovie1, mockMovie2, mockMovie3));

        // Appeler la méthode à tester
        var result = movieService1.getListMoviesBetweenYears(2000, 2015);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Inception", result.get(0).getTitle());
        assertEquals("Interstellar", result.get(1).getTitle());
    }

    @Test
    void testGetListMoviesByStyle() {
        // Préparer le mock pour qu'il retourne une liste de films spécifique
        Style sfStyle = new Style(1, "SF");
        Style comedyStyle = new Style(2, "Comedy");
        Productor mockProductor = new Productor(1, "Warner Bros");
        Movie mockMovie1 = new Movie(1, "The Matrix", 1999, sfStyle, "REF-123456", mockProductor);
        Movie mockMovie2 = new Movie(2, "Inception", 2010, sfStyle, "REF-654321", mockProductor);
        Movie mockMovie3 = new Movie(3, "Comedy Movie", 2005, comedyStyle, "REF-789012", mockProductor);
        
        when(movieRepositoryJPA.findAll()).thenReturn(Arrays.asList(mockMovie1, mockMovie2, mockMovie3));

        // Appeler la méthode à tester
        var result = movieService1.getListMoviesByStyle(sfStyle);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("The Matrix", result.get(0).getTitle());
        assertEquals("Inception", result.get(1).getTitle());
    }

    @Test
    void testGetMovieById() {
        // Préparer les mock pour le repository
        Style mockStyle = new Style(1, "SCI-FI");
        Productor mockProductor = new Productor(1, "Warner Bros");
        Movie mockMovie = new Movie( 1, "The Matrix", 1999, mockStyle, "REF-123456", mockProductor);

        when(movieRepositoryJPA.findById(1)).thenReturn(Optional.of(mockMovie));

        // Appeler la méthode à tester
        Optional<Movie> result = movieService1.getMovieById(1);

        // Vérifier que le résultat est correct
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals("The Matrix", result.get().getTitle());

    }

    @Test
    void testTransferAllBorrows() {
        // Préparer les mock pour le repository
        Customer mockCustomerFrom = new Customer(1, "Client Source");
        Customer mockCustomerTo = new Customer(2, "Client Destination");
        
        Movie mockMovie1 = new Movie(1, "The Matrix", 1999, new Style(1, "SF"), "REF001", new Productor(1, "Warner"));
        Movie mockMovie2 = new Movie(2, "Inception", 2010, new Style(1, "SF"), "REF002", new Productor(1, "Warner"));

        Borrow mockBorrow1 = new Borrow(1, "2025-01-01", mockCustomerFrom, mockMovie1, BorrowStatus.EN_COURS);
        Borrow mockBorrow2 = new Borrow(2, "2025-01-02", mockCustomerFrom, mockMovie2, BorrowStatus.EN_COURS);
        // Mocker le repository pour retourner les emprunts du client source
        when(borrowRepository.findByCustomerCustomerId(1)).thenReturn(Arrays.asList(mockBorrow1, mockBorrow2));

        // Appeler la méthode à tester
        movieService1.transferAllBorrows(1, 2);

        // Vérifier que findByCustomerCustomerId a été appelé
        verify(borrowRepository).findByCustomerCustomerId(1);

        // Vérifier que save a été appelé pour chaque emprunt avec le nouveau customerId
        verify(borrowRepository).save(mockBorrow1);
        verify(borrowRepository).save(mockBorrow2);

        // Vérifier que les emprunts ont été transférés au bon client
        assertEquals(2, mockBorrow1.getCustomer().getCustomerId());
        assertEquals(2, mockBorrow2.getCustomer().getCustomerId());
    }
}
