package com.etraveli;

import com.etraveli.amountprocceesor.MovieType;
import com.etraveli.amountprocceesor.MovieTypeFactory;
import com.etraveli.domain.Customer;
import com.etraveli.domain.Movie;
import com.etraveli.domain.MovieRental;
import com.etraveli.repository.InMemoryRepositoryMovie;
import com.etraveli.repository.MovieRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RentalService {

    private final MovieRepository movieRepository = new InMemoryRepositoryMovie();

    public String statement(Customer customer) {
        var result = new StringBuilder();
        result.append("Rental Record for ")
                .append(customer.getName())
                .append("\n");
        return calculateTotalAmountsAndEnterPoints(customer.getRentals(), result);
    }

    private String calculateTotalAmountsAndEnterPoints(List<MovieRental> movieRentals, StringBuilder result) {
        Map<String, Movie> movies = movieRepository.findAll();
        var totalAmount = 0.0;
        var frequentEnterPoints = 0;

        for (MovieRental movieRental : movieRentals) {
            String movieId = movieRental.getMovieId();
            double amount = calculateAmount(movies, movieRental);
            frequentEnterPoints += calculateFrequentEnterPoints(movies, movieRental);
            totalAmount = totalAmount + amount;
            result.append("\t")
                    .append(movies.get(movieId).getTitle())
                    .append("\t")
                    .append(amount)
                    .append("\n");
        }
        return result.append("Amount owed is ")
                .append(totalAmount)
                .append("\n")
                .append("You earned ")
                .append(frequentEnterPoints)
                .append(" frequent points\n")
                .toString();
    }

    private int calculateFrequentEnterPoints(Map<String, Movie> movies, MovieRental movieRental) {
        for (MovieType movieType : MovieTypeFactory.movieTypes()) {
            if (movieType.getCode().equals(movies.get(movieRental.getMovieId()).getCode())) {
                return movieType.calculateFrequentEnterPoints(movieRental);
            }
        }
        throw new IllegalArgumentException();
    }

    private double calculateAmount(Map<String, Movie> movies, MovieRental movieRental) {
        for (MovieType movieType : MovieTypeFactory.movieTypes()) {
            if (movieType.getCode().equals(movies.get(movieRental.getMovieId()).getCode())) {
                return movieType.calculateAmount(movieRental);
            }
        }
        throw new IllegalArgumentException();
    }

    public int findFrequentEnterPoints(Customer customer) {
        var frequentEnterPoints = 0;
        //add frequent bonus points
        frequentEnterPoints++;
        // add bonus for a two day new release rental
        List<Movie> twoDaysNew = movieRepository.findAll().values().stream().filter(
                mov -> customer.getRentals().stream()                    // filter
                        .anyMatch(ns ->                                  // compare both
                                mov.getCode().equals("new") && ns.getDays() > 2))
                .collect(Collectors.toList());
        System.out.println(twoDaysNew);

        if (movieRepository.findAll().values().stream().filter(
                mov -> customer.getRentals().stream()                    // filter
                        .anyMatch(ns ->                                  // compare both
                                mov.getCode().equals("new") && ns.getDays() > 2)).count() > 0)
            frequentEnterPoints++;

        return frequentEnterPoints;
    }
}
