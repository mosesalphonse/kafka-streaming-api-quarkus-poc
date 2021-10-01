package com.sash.quarkus.coffeeshop;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

class Names {

    private static final List<String> VALUES = Arrays.asList(
            "Sashvin",
            "Moses",
            "Alphonse",
            "Mohan",
            "Saravanan"
    );

    static String pickAName() {
        Random random = new Random();  //NOSONAR - cannot be a static field, would be inlined at build time.
        int index = random.nextInt(VALUES.size());
        return VALUES.get(index);
    }

    private Names() {
        // avoid direct instantiation
    }
}
