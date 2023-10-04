package org.example.service;

import org.example.entity.Movie;

import java.util.List;

public interface MovieService {
    void addMovie(Movie movie);
    void removeMovie(long id);
    Movie findById(long id);
    List<Movie> findAll();
    List<Movie> findMoviesByDirector(String director);
    List<Movie> findMoviesByType(String type);
    List<Movie> findMoviesByReleaseYear(int year);
}
