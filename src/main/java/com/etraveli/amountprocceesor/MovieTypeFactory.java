package com.etraveli.amountprocceesor;

import java.util.List;

public class MovieTypeFactory {

    private MovieTypeFactory() {
    }

    public static List<MovieType> movieTypes() {
        return List.of(new Children(), new Regular(), new New());
    }
}
