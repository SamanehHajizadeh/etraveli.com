package com.etraveli.repository;

import com.etraveli.domain.Movie;

import java.util.Map;

public interface MovieRepository {

    Map<String, Movie> findAll();
}
