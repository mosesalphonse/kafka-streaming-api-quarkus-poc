package com.sash.quarkus.coffeeshop.codecs;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;
import io.quarkus.runtime.annotations.RegisterForReflection;
import com.sash.quarkus.coffeeshop.model.Beverage;

@RegisterForReflection
public class BeverageDeserializer extends ObjectMapperDeserializer<Beverage> {

    public BeverageDeserializer() {
        super(Beverage.class);
    }
}
