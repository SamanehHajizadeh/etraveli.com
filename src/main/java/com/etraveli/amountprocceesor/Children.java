package com.etraveli.amountprocceesor;

import com.etraveli.domain.MovieRental;

public class Children implements MovieType {

    @Override
    public double calculateAmount(MovieRental movieRental) {
        var thisAmount = 1.5;
        if (movieRental.getDays() > 3) {
            thisAmount = ((movieRental.getDays() - 3) * 1.5) + thisAmount;
        }
        return thisAmount;
    }

    @Override
    public String getCode() {
        return "children";
    }
}
