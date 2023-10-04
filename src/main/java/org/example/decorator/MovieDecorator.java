package org.example.decorator;

import org.example.entity.Movie;

/**
 * Using the Decorator pattern we can extend a class without changing the main class
 */
public abstract class MovieDecorator extends Movie {
    private Movie movie;

    public MovieDecorator(Movie movie) {
        super(movie.getTitle(), movie.getDirector(), movie.getPriceCode(), movie.getReleaseYear());
        this.movie = movie;
    }

    @Override
    public String toString() {
        return movie.toString();
    }
}