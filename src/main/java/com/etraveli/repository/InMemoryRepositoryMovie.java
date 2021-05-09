package com.etraveli.repository;

import com.etraveli.domain.Movie;

import java.util.Map;

public class InMemoryRepositoryMovie implements MovieRepository {

    private final Map<String, Movie> movies;

    public InMemoryRepositoryMovie() {
        this.movies = Map.of("F001", new Movie("You've Got Mail", "regular"),
                "F002", new Movie("Matrix", "regular"),
                "F003", new Movie("Cars", "children"),
                "F004", new Movie("Fast & Furious X", "new")
        );
    }

    @Override
    public final Map<String, Movie> findAll() {
        return movies;
    }
}
