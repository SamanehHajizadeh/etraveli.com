package com.etraveli.amountprocceesor;

import com.etraveli.domain.MovieRental;

public interface MovieType {
    double calculateAmount(MovieRental movieRental);

    String getCode();

    default  int calculateFrequentEnterPoints(MovieRental movieRental) {
        return 1;
    }
}
