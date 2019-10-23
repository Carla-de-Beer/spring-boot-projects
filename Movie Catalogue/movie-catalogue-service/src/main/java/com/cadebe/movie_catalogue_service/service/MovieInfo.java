package com.cadebe.movie_catalogue_service.service;

import com.cadebe.movie_catalogue_service.model.CatalogueItem;
import com.cadebe.movie_catalogue_service.model.Movie;
import com.cadebe.movie_catalogue_service.model.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfo {

    @Autowired
    RestTemplate restTemplate;

    // Bulkhead pattern
    @HystrixCommand(fallbackMethod = "getFallbackCatalogueItem",
            threadPoolKey = "movieInfoPool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "20"),
                    @HystrixProperty(name = "maxQueueSize", value = "10")
            })
    public CatalogueItem getCatalogueItem(Rating rating) {
        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
        assert movie != null;
        return new CatalogueItem(movie.getName(), movie.getDescription(), rating.getRating());
    }

    public CatalogueItem getFallbackCatalogueItem(Rating rating) {
        return new CatalogueItem("Movie name not found", "", rating.getRating());
    }

}
