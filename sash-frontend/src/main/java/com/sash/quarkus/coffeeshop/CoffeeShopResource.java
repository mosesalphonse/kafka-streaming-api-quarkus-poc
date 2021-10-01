package com.sash.quarkus.coffeeshop;

import io.smallrye.mutiny.Uni;
import com.sash.quarkus.coffeeshop.http.BaristaService;
import com.sash.quarkus.coffeeshop.model.Beverage;
import com.sash.quarkus.coffeeshop.model.Order;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.time.Duration;
import java.util.UUID;

@Path("/")
public class CoffeeShopResource {

    private static final Logger LOGGER=LoggerFactory.getLogger("CoffeeShop-Service");
    
    @RestClient
    BaristaService barista;
    
    @POST
    @Path("/http")
    public Uni<Beverage> http(Order order) {
        LOGGER.info("coffeeshop-service --> CoffeeShopResource's http POST, createing order");
        return barista.order(order.setOrderId(getId()))
                .onItem().invoke(beverage -> beverage.preparationState = Beverage.State.READY)
                .ifNoItem().after(Duration.ofMillis(1500)).fail()
                .onFailure().recoverWithItem(createFallbackBeverage(order));
    }

    private Beverage createFallbackBeverage(Order order) {
        LOGGER.info("coffeeshop-service --> CoffeeShopResource's createFallbackBeverage , Failed");
        return new Beverage(order, null, Beverage.State.FAILED);
    }

    // Orders emitter (orders)
    @Channel("orders")
    Emitter<Order> orders;
    // Queue emitter (beverages)
    @Channel("queue")
    Emitter<Beverage> queue;

    @POST
    @Path("/messaging")
    public Order messaging(Order order) {
        LOGGER.info("coffeeshop-service --> CoffeeShopResource's messaging POST , Order name :"+order.getName());
        order = order.setOrderId(getId());
        queue.send(Beverage.queued(order));
        orders.send(order);
        return order;
    }

    private String getId() {
        return UUID.randomUUID().toString();
    }

}
