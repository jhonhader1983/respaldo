package com.lamaison.auth.infrastructure.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class EventProducer {

    private static final Logger log = LoggerFactory.getLogger(EventProducer.class);
    static final String CHANNEL = "lamaison:events";

    private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;
    private final ObjectMapper objectMapper;

    public EventProducer(ReactiveRedisTemplate<String, String> reactiveRedisTemplate,
                         ObjectMapper objectMapper) {
        this.reactiveRedisTemplate = reactiveRedisTemplate;
        this.objectMapper = objectMapper;
    }

    public Mono<Void> publish(QueueEvent event) {
        return Mono.fromCallable(() -> objectMapper.writeValueAsString(event))
                .flatMap(json -> reactiveRedisTemplate.convertAndSend(CHANNEL, json))
                .doOnNext(subscribers ->
                        log.info("[QUEUE] Evento publicado → canal={} tipo={} suscriptores={}",
                                CHANNEL, event.getTipo(), subscribers))
                .doOnError(e ->
                        log.error("[QUEUE] Error publicando evento tipo={}: {}", event.getTipo(), e.getMessage()))
                .then();
    }
}
