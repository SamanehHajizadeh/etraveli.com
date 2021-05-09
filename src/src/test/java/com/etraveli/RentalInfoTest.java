package com.etraveli;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


class RentalInfoTest {

    @Test
    void showReturn() {
        String expected = "Rental Record for C. U. Stomer\n\tYou've Got Mail\t3.5\n\tMatrix\t2.0\nAmount owed is 5.5\nYou earned 2 frequent points\n";

        String result = new RentalInfo().statement(new Customer("C. U. Stomer",
                List.of(new MovieRental("F001", 3),
                        new MovieRental("F002", 1))));

        assertThat(result, is(expected));

    }

}