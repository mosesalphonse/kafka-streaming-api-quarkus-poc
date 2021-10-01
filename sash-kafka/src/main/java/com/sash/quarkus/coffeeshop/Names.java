package com.sash.quarkus.coffeeshop;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

class Names {

    private static final List<String> VALUES = Arrays.asList(
            "Moses",
            "Sashvin",
            "Rahul",
            "Alphonse"
           
    );

    static String pickAName() {
        Random random = new Random();
        int index = random.nextInt(VALUES.size());
        return VALUES.get(index);
    }

    private Names() {
        // avoid direct instantiation
    }
}
