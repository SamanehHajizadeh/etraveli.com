package com.etraveli;

import com.etraveli.amountprocceesor.MovieTypeFactory;
import com.etraveli.domain.Customer;
import com.etraveli.domain.Movie;
import com.etraveli.domain.MovieRental;
import com.etraveli.repository.InMemoryRepositoryMovie;
import com.etraveli.repository.MovieRepository;

import java.util.List;
import java.util.Map;

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
        var movieRentalCode = movies.get(movieRental.getMovieId()).getCode();
        return MovieTypeFactory.movieTypes()
                .stream()
                .filter(movieType -> movieType.getCode().equals(movieRentalCode))
                .findFirst()
                .map(movieType -> movieType.calculateFrequentEnterPoints(movieRental))
                .orElseThrow(() -> new IllegalArgumentException("Movie type is not supported."));
    }

    private double calculateAmount(Map<String, Movie> movies, MovieRental movieRental) {
        var movieRentalCode = movies.get(movieRental.getMovieId()).getCode();
        return MovieTypeFactory.movieTypes()
                .stream()
                .filter(movieType -> movieType.getCode().equals(movieRentalCode))
                .findFirst()
                .map(movieType -> movieType.calculateAmount(movieRental))
                .orElseThrow(() -> new IllegalArgumentException("Movie type is not supported."));
    }
}
