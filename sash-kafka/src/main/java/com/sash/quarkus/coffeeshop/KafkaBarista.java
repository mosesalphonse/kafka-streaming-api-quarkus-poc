package com.sash.quarkus.coffeeshop;

import io.smallrye.reactive.messaging.annotations.Blocking;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.util.Random;

import static com.sash.quarkus.coffeeshop.Names.pickAName;

@ApplicationScoped
public class KafkaBarista {

    private static final Logger LOGGER = LoggerFactory.getLogger("Kafka-Barista");

    private String name = pickAName();

    @Incoming("orders")
    @Outgoing("queue")
    @Blocking
    public Beverage process(Order order) {
        return prepare(order);
    }

    Beverage prepare(Order order) {
        LOGGER.info("barista-quarkus-kafka--> KafkaBarista's prepare is invoked");
        int delay = getPreparationTime();
        try {
            LOGGER.info("barista-quarkus-kafka--> KafkaBarista's processing time in Milli seconds {}",delay);
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        LOGGER.info("Order {} for {} is ready", order.getProduct(), order.getName());
        return new Beverage(order, name, Beverage.State.READY);
    }

    private Random random = new Random();

    int getPreparationTime() {
        return random.nextInt(5) * 1000;
    }

}
