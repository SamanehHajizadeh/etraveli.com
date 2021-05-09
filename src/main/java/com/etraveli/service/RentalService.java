package com.etraveli.service;

import com.etraveli.amountprocceesor.MovieTypeFactory;
import com.etraveli.domain.Customer;
import com.etraveli.domain.Movie;
import com.etraveli.domain.MovieRental;
import com.etraveli.repository.InMemoryRepositoryMovie;
import com.etraveli.repository.MovieRepository;
import com.etraveli.service.StatementMessageContext.TitleAmount;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RentalService {

    private final MovieRepository movieRepository = new InMemoryRepositoryMovie();
    private final RentalMessageService messageService = new RentalMessageService();

    public String statement(Customer customer) {
        return calculateTotalAmountsAndEnterPoints(customer, customer.getRentals());
    }

    private String calculateTotalAmountsAndEnterPoints(Customer customer, List<MovieRental> movieRentals) {
        Map<String, Movie> movies = movieRepository.findAll();
        var totalAmount = 0.0;
        var frequentEnterPoints = 0;

        List<TitleAmount> titleAmounts = new ArrayList<>();
        for (MovieRental movieRental : movieRentals) {
            String movieId = movieRental.getMovieId();
            double amount = calculateAmount(movies, movieRental);
            frequentEnterPoints += calculateFrequentEnterPoints(movies, movieRental);
            totalAmount = totalAmount + amount;

           var titleAmount = new TitleAmount(movies.get(movieId).getTitle(), amount);
            titleAmounts.add(titleAmount);
        }

        var context = new StatementMessageContext(customer, totalAmount, frequentEnterPoints, titleAmounts);
        return messageService.createStatementMessage(context);

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
