package org.example.dao;

import org.example.entity.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieDAO {

    void addMovie(Movie movie);
    void removeMovie(long id);
    Optional<Movie> findById(long id);
    List<Movie> findAll();
    List<Movie> findMoviesByDirector(String director);
    List<Movie> findMoviesByType(String type);
    List<Movie> findMoviesByReleaseYear(int year);
}
