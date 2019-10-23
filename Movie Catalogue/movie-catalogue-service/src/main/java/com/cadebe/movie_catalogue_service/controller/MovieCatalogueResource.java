package com.cadebe.movie_catalogue_service.controller;

import com.cadebe.movie_catalogue_service.model.CatalogueItem;
import com.cadebe.movie_catalogue_service.model.Movie;
import com.cadebe.movie_catalogue_service.model.Rating;
import com.cadebe.movie_catalogue_service.model.UserRating;
import com.cadebe.movie_catalogue_service.service.MovieInfo;
import com.cadebe.movie_catalogue_service.service.UserRatingInfo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalogue")

public class MovieCatalogueResource {

    private final RestTemplate restTemplate;

    @Autowired
    MovieInfo movieInfo;

    @Autowired
    UserRatingInfo userRatingInfo;

    @Autowired
    public MovieCatalogueResource(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping("/{userId}")
    public List<CatalogueItem> getCatalogueForId(@PathVariable("userId") String userId) {
        UserRating userRating = userRatingInfo.getUserRating(userId);
        return userRating.getRatings().stream()
                .map(movieInfo::getCatalogueItem)
                .collect(Collectors.toList());
    }

    // Alternative to RestTemplate
    // Asynchronous when used without block
    //            Movie movie = webClientBuilder.build()
    //                    .get()
    //                    .uri("http://localhost:8082/movies/foo" + rating.getMovieId())
    //                    .retrieve()
    //                    .bodyToMono(Movie.class) // mono => type of promise
    //                    .block(); // block until mono has been fulfilled
}
