package com.etraveli.amountprocceesor;

import com.etraveli.domain.MovieRental;

class New implements MovieType {

    @Override
    public double calculateAmount(MovieRental movieRental) {
        return movieRental.getDays() * 3.0;
    }

    @Override
    public String getCode() {
        return "new";
    }

    @Override
    public int calculateFrequentEnterPoints(MovieRental movieRental) {
        if (movieRental.getDays() > 2) {
            return 2;
        }
        return 1;
    }

}
