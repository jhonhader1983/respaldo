package com.lamaison.auth.infrastructure.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.ReactiveSubscription;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.ReactiveRedisMessageListenerContainer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class EventConsumer {

    private static final Logger log = LoggerFactory.getLogger(EventConsumer.class);

    private final ReactiveRedisMessageListenerContainer listenerContainer;
    private final ObjectMapper objectMapper;

    public EventConsumer(ReactiveRedisMessageListenerContainer listenerContainer,
                         ObjectMapper objectMapper) {
        this.listenerContainer = listenerContainer;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void subscribe() {
        RedisSerializationContext.SerializationPair<String> stringPair =
                RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer());

        listenerContainer
                .receive(
                        Collections.singletonList(ChannelTopic.of(EventProducer.CHANNEL)),
                        stringPair,
                        stringPair
                )
                .map(ReactiveSubscription.Message::getMessage)
                .subscribe(
                        json -> processJson(json),
                        err -> log.error("[CONSUMER] Error en la suscripción Redis: {}", err.getMessage())
                );

        log.info("[CONSUMER] Suscrito al canal Redis: {}", EventProducer.CHANNEL);
    }

    private void processJson(String json) {
        try {
            QueueEvent event = objectMapper.readValue(json, QueueEvent.class);
            log.info("[CONSUMER] Evento recibido → tipo={} timestamp={}", event.getTipo(), event.getTimestamp());
            processEvent(event);
        } catch (Exception e) {
            log.error("[CONSUMER] Error deserializando evento: {} | json={}", e.getMessage(), json);
        }
    }

    private void processEvent(QueueEvent event) {
        switch (event.getTipo()) {
            case "PEDIDO_CREADO" ->
                    log.info("[NOTIFICACION] Nuevo pedido creado → {}", event.getPayload());
            case "PEDIDO_ACTUALIZADO" ->
                    log.info("[NOTIFICACION] Pedido actualizado → {}", event.getPayload());
            case "USUARIO_REGISTRADO" ->
                    log.info("[NOTIFICACION] Nuevo usuario registrado → {}", event.getPayload());
            default ->
                    log.warn("[CONSUMER] Tipo de evento desconocido: {}", event.getTipo());
        }
    }
}
