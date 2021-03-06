package com.sash.quarkus.coffeeshop.codecs;

import io.quarkus.kafka.client.serialization.JsonbDeserializer;
import io.quarkus.runtime.annotations.RegisterForReflection;
import com.sash.quarkus.coffeeshop.Order;

@RegisterForReflection
public class OrderDeserializer extends JsonbDeserializer<Order> {

    public OrderDeserializer() {
        super(Order.class);
    }
}
