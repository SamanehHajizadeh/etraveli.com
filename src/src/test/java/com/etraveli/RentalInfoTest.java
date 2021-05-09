package com.etraveli;

import com.etraveli.domain.Customer;
import com.etraveli.domain.MovieRental;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


class RentalInfoTest {

    @Test
    void shouldReturnMessageForRegularMovies() {
        String expected = "Rental Record for C. U. Stomer\n\tYou've Got Mail\t3.5\n\tMatrix\t2.0\nAmount owed is 5.5\nYou earned 2 frequent points\n";

        String result = new RentalService().statement(new Customer("C. U. Stomer",
                List.of(new MovieRental("F001", 3),
                        new MovieRental("F002", 1))));

        assertThat(result, is(expected));
    }

    @Test
    void shouldReturnMessageForNewMovies() {
        String expected = "Rental Record for C. U. Stomer\n\tFast & Furious X\t3.0\n\tFast & Furious X\t12.0\nAmount owed is 15.0\nYou earned 3 frequent points\n";

        String result = new RentalService().statement(new Customer("C. U. Stomer",
                List.of(new MovieRental("F004", 1),
                        new MovieRental("F004", 4))));

        assertThat(result, is(expected));
    }

    @Test
    void shouldReturnMessageForChildrenMovies() {
        String expected = "Rental Record for C. U. Stomer\n\tCars\t1.5\n\tCars\t3.0\nAmount owed is 4.5\nYou earned 2 frequent points\n";

        String result = new RentalService().statement(new Customer("C. U. Stomer",
                List.of(new MovieRental("F003", 1),
                        new MovieRental("F003", 4))));

        assertThat(result, is(expected));
    }

    }