package com.sash.quarkus.coffeeshop.dashboard;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.mutiny.Multi;
import com.sash.quarkus.coffeeshop.model.Beverage;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.Duration;

@Path("/queue")
public class BoardResource {

    private static Logger LOGGER = LoggerFactory.getLogger("BoardResource");
    @Inject
    @Channel("beverages")
    Multi<Beverage> queue;

    @Inject
    ObjectMapper mapper;

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Publisher<String> getQueue() {
        LOGGER.info("coffeeshop-service --> BoardResource's Queue GET");
        return Multi.createBy().merging()
                .streams(
                        queue.map(this::toJson),
                        getPingStream()
                );
    }

    Multi<String> getPingStream() {
        LOGGER.info("coffeeshop-service --> getPingStream method invoked");
        return Multi.createFrom().ticks().every(Duration.ofSeconds(10))
                .onItem().transform(x -> "{}");
    }

    private String toJson(Beverage b) {
        try {
            return mapper.writeValueAsString(b);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

}
