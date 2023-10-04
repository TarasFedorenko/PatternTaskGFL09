package org.example.decorator;

import org.example.entity.Movie;

import java.util.List;

public class MovieWithDescriptionAndActors extends MovieDecorator {
    private String description;
    private List<String> actors;

    public MovieWithDescriptionAndActors(Movie movie, String description, List<String> actors) {
        super(movie);
        this.description = description;
        this.actors = actors;
    }

    @Override
    public String toString() {
        return super.toString() + "\nDescription: " + description + "\nActors: " + actors;
    }
}
