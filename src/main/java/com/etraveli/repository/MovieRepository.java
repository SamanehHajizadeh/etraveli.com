package com.etraveli.repository;

import com.etraveli.Movie;

import java.util.Map;

public interface MovieRepository {

    Map<String, Movie> findAll();
}
