package com.ndroc.rocmovies.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.ndroc.rocmovies.Entity.Movie;
import com.ndroc.rocmovies.Entity.Style;
import com.ndroc.rocmovies.Service.IMovieService;

@Controller
public class HomeController {

    @Autowired
    @Qualifier("Service1")
    private IMovieService movieService;

    public List<Movie> getListMovies() {
        List<Movie> list;
        list = movieService.getListMovies();
        if(list.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun film trouvé !");
        }
        return list;
        }
        
    @RequestMapping(value={"", "/", "home"})
    public String displayHomePage(Model model) {

        model.addAttribute("movies", getListMovies());
        model.addAttribute("styles", movieService.getListStyles());

        return "home.html";
    }
    
    @GetMapping("/films/filter")
    public String displayFilteredMovies(@RequestParam("style") Optional<String> styleParam, Model model) {

        List<Movie> movies = new ArrayList<>();

        if (styleParam.isPresent() && !styleParam.get().isEmpty()) {
            Optional<Style> styleOpt = movieService.getStyleByName(styleParam.get());
            if (styleOpt.isPresent()) {
                Style style = styleOpt.get();
                movies = movieService.getListMoviesByStyle(style);
                if (movies.isEmpty()) {
                    model.addAttribute("info", "Aucun film trouvé pour le style " + style.getStyleName() + " !");
                }
            } else {
                model.addAttribute("error", "Le style '" + styleParam.get() + "' n'existe pas !");
            }
        } else {
            movies = movieService.getListMovies();
            if (movies.isEmpty()) {
                model.addAttribute("info", "Aucun film trouvé !");
            }
        }

        model.addAttribute("movies", movies);
        model.addAttribute("selectedStyle", styleParam.orElse(""));
        model.addAttribute("styles", movieService.getListStyles());
        return "movies-filter.html";
    }

    @GetMapping("/film/{id}")
    public String displayMovieInfo(@PathVariable int id, Model model)
    {
        Optional<Movie> optional = movieService.getMovieById(id);
        if(optional.isPresent())
        {
            optional.get();
            model.addAttribute("movie", optional.get());
            
        }else{
            model.addAttribute("error", "Il n'existe aucun film avec l'id "+id+" !");
        }
        return "movie-details.html";
    }
    
}
