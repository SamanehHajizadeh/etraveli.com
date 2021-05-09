package com.etraveli.amountprocceesor;

import com.etraveli.domain.MovieRental;

public class Regular implements MovieType {

    @Override
    public double calculateAmount(MovieRental movieRental) {
        double thisAmount = 2;
        if (movieRental.getDays() > 2) {
            thisAmount = ((movieRental.getDays() - 2) * 1.5) + thisAmount;
        }
        return thisAmount;
    }

    @Override
    public String getCode() {
        return "regular";
    }
}
