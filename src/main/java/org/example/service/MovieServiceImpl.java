package org.example.service;

import org.example.dao.MovieDAO;
import org.example.dao.MovieDAOImpl;
import org.example.entity.Movie;

import java.util.List;
import java.util.Optional;

public class MovieServiceImpl implements MovieService {
    private final MovieDAO movieDAO = new MovieDAOImpl();

    @Override
    public void addMovie(Movie movie) {
        movieDAO.addMovie(movie);
    }

    @Override
    public void removeMovie(long id) {
        movieDAO.removeMovie(id);
    }

    @Override
    public Movie findById(long id) {
        Optional<Movie> optionalMovie = movieDAO.findById(id);
        return optionalMovie.orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));
    }

    @Override
    public List<Movie> findAll() {
        return movieDAO.findAll();
    }

    @Override
    public List<Movie> findMoviesByDirector(String director) {
        return movieDAO.findMoviesByDirector(director);
    }

    @Override
    public List<Movie> findMoviesByType(String type) {
        return movieDAO.findMoviesByType(type);
    }

    @Override
    public List<Movie> findMoviesByReleaseYear(int year) {
        return movieDAO.findMoviesByReleaseYear(year);
    }
}
