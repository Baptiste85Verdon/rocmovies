package com.ndroc.rocmovies.Controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ndroc.rocmovies.Entity.Movie;
import com.ndroc.rocmovies.Entity.Productor;
import com.ndroc.rocmovies.Entity.Style;
import com.ndroc.rocmovies.Entity.Borrow;
import com.ndroc.rocmovies.Entity.Customer;
import com.ndroc.rocmovies.Entity.BorrowStatus;
import com.ndroc.rocmovies.Service.IMovieService;

@WebMvcTest(MovieController.class)
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean(name = "Service2")
    private IMovieService movieService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetMovieById_Returns200AndJson() throws Exception {
        // Préparer le mock pour le service
        Style mockStyle = new Style(1, "SCI-FI");
        Productor mockProductor = new Productor(1, "Warner Bros");
        Movie mockMovie = new Movie( 1, "The Matrix", 1999, mockStyle, "REF-123456", mockProductor);
        when(movieService.getMovieById(1L)).thenReturn(Optional.of(mockMovie));

        // Effectuer la requête GET sur /movie/1 et vérifier la réponse
        mockMvc.perform(get("/movie/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void testGetMovieById_Returns404() throws Exception {
        when(movieService.getMovieById(999L)).thenReturn(Optional.empty());

        // Effectuer la requête GET sur /movie/1 et vérifier la réponse
        mockMvc.perform(get("/movie/999"))
            .andExpect(status().isNotFound());
    }

    @Test
    void testAddMovie_Returns201AndJson() throws Exception {
        // Préparer le mock pour le service
        Style mockStyle = new Style(1, "SCI-FI");
        Productor mockProductor = new Productor(1, "Warner Bros");
        Movie mockMovie = new Movie( 1, "The Matrix", 1999, mockStyle, "REF-123456", mockProductor);

        // Effectuer la requête POST sur /movie/add et vérifier la réponse
        mockMvc.perform(
            org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/movie/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockMovie))
        )        .andExpect(status().isCreated());
    }

    @Test
    void testAddMovie_Returns400ForBadJson() throws Exception {
        // Préparer le mock pour le service
        String badJson = "{ \"movieId\": \"one\", \"title\": 123, \"style\": \"UNKNOWN\", \"productionYear\": \"nineteen ninety nine\" }";

        // Effectuer la requête POST sur /movie/add et vérifier la réponse
        mockMvc.perform(
            org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/movie/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(badJson)
        )        .andExpect(status().isBadRequest());

        // Vérifier que le service n'a pas été appelé quand le JSON est malformé
        verify(movieService, never()).addMovie(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void testDeleteMovie_Returns204() throws Exception {
        // Préparer le mock pour le service
        Style mockStyle = new Style(1, "SCI-FI");
        Productor mockProductor = new Productor(1, "Warner Bros");
        Movie mockMovie = new Movie(1, "The Matrix", 1999, mockStyle, "REF-123456", mockProductor);
        when(movieService.getMovieById(1L)).thenReturn(Optional.of(mockMovie));

        // Effectuer la requête DELETE sur /movie/1 avec confirmation
        mockMvc.perform(delete("/movie/1?confirm=true"))
            .andExpect(status().isNoContent());

        // Vérifier que deleteMovie a été appelé
        verify(movieService).deleteMovie(1L);
    }

    @Test
    void testDeleteMovie_Returns404() throws Exception {
        // Préparer le mock pour le service - film non trouvé
        when(movieService.getMovieById(999L)).thenReturn(Optional.empty());

        // Effectuer la requête DELETE sur /movie/999 avec confirmation
        mockMvc.perform(delete("/movie/999?confirm=true"))
            .andExpect(status().isNotFound());

        // Vérifier que deleteMovie n'a pas été appelé
        verify(movieService, never()).deleteMovie(999L);
    }

    @Test
    void testDeleteMovie_WithoutConfirmation_Returns400() throws Exception {
        // Effectuer la requête DELETE sur /movie/1 sans confirmation
        mockMvc.perform(delete("/movie/1"))
            .andExpect(status().isBadRequest());

        // Vérifier que deleteMovie n'a pas été appelé
        verify(movieService, never()).deleteMovie(1L);
    }

    @Test
    void testGetAllBorrowedMoviesByCustomerId_Returns200AndJson() throws Exception {
        // Préparer le mock pour le service
        Style mockStyle = new Style(1, "SCI-FI");
        Productor mockProductor = new Productor(1, "Warner Bros");
        Movie mockMovie1 = new Movie(1, "The Matrix", 1999, mockStyle, "REF-123456", mockProductor);
        Movie mockMovie2 = new Movie(2, "Inception", 2010, mockStyle, "REF-654321", mockProductor);
        List<Movie> borrowedMovies = Arrays.asList(mockMovie1, mockMovie2);
        
        when(movieService.getAllBorrowedMoviesByCustomerId(1L)).thenReturn(borrowedMovies);

        // Effectuer la requête GET sur /customers/1/borrows-customer
        mockMvc.perform(get("/customers/1/borrows-customer").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    void testGetAllBorrowedMoviesByCustomerId_Returns404() throws Exception {
        // Préparer le mock pour le service - aucun film emprunté
        when(movieService.getAllBorrowedMoviesByCustomerId(999L)).thenReturn(Arrays.asList());

        // Effectuer la requête GET sur /customers/999/borrows-customer
        mockMvc.perform(get("/customers/999/borrows-customer"))
            .andExpect(status().isNotFound());
    }

    @Test
    void testAddBorrowToCustomer_Returns201() throws Exception {
        // Préparer le mock pour le service
        Style mockStyle = new Style(1, "SCI-FI");
        Productor mockProductor = new Productor(1, "Warner Bros");
        Movie mockMovie = new Movie(1, "The Matrix", 1999, mockStyle, "REF-123456", mockProductor);
        Customer mockCustomer = new Customer(1, "Test Customer");

        Borrow mockBorrow = new Borrow(1, "2025-11-25", mockCustomer, mockMovie, BorrowStatus.EN_COURS);


        // Effectuer la requête POST sur /customers/1/borrow avec le JSON Borrow
        mockMvc.perform(post("/customers/1/borrow")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(mockBorrow)))
            .andExpect(status().isCreated());

        // Vérifier que addBorrowToCustomer a été appelé avec un Borrow
        verify(movieService).addBorrowToCustomer(any(Borrow.class));
    }

}
