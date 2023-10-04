package org.example.entity;

import org.example.enums.MovieType;

public class Movie extends BaseEntity {
    private String title;
    private String director;
    private MovieType priceCode;
    private Integer releaseYear;

    public Movie(String title, String director, MovieType priceCode, Integer releaseYear) {
        super();
        this.title = title;
        this.director = director;
        this.priceCode = priceCode;
        this.releaseYear = releaseYear;
    }
    public Movie(){

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setPriceCode(MovieType priceCode) {
        this.priceCode = priceCode;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDirector() {
        return director;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public MovieType getPriceCode() {
        return priceCode;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + getId() + '\'' +
                "title='" + title + '\'' +
                ", director='" + director + '\'' +
                ", priceCode=" + priceCode +
                ", releaseYear=" + releaseYear +
                '}';
    }
}