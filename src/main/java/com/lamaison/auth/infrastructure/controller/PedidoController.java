package com.lamaison.auth.infrastructure.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lamaison.auth.infrastructure.controller.repository.PedidoRepository;
import com.lamaison.auth.infrastructure.queue.EventProducer;
import com.lamaison.auth.infrastructure.queue.QueueEvent;
import com.lamaison.auth.domain.model.Pedido;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final EventProducer eventProducer;

    public PedidoController(PedidoRepository pedidoRepository, EventProducer eventProducer) {
        this.pedidoRepository = pedidoRepository;
        this.eventProducer = eventProducer;
    }

    @GetMapping
    public Flux<Pedido> getAll() {
        return pedidoRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Pedido> create(@RequestBody Pedido pedido) {
        pedido.setEstado("pendiente");
        pedido.setCreatedAt(LocalDateTime.now());
        return pedidoRepository.save(pedido)
                .flatMap(saved -> {
                    String payload = "Pedido #" + saved.getId() + " | Total: $" + saved.getTotal()
                            + " | Usuario: " + (saved.getUsuario() != null ? saved.getUsuario().getEmail() : "desconocido");
                    QueueEvent event = new QueueEvent("PEDIDO_CREADO", payload, LocalDateTime.now());
                    return eventProducer.publish(event).thenReturn(saved);
                });
    }

    @PutMapping("/{id}")
    public Mono<Pedido> updateEstado(@PathVariable String id, @RequestBody Pedido pedido) {
        return pedidoRepository.findById(id)
                .flatMap(existing -> {
                    existing.setEstado(pedido.getEstado());
                    return pedidoRepository.save(existing)
                            .flatMap(saved -> {
                                QueueEvent event = new QueueEvent(
                                        "PEDIDO_ACTUALIZADO",
                                        "Pedido #" + saved.getId() + " → estado: " + saved.getEstado(),
                                        LocalDateTime.now()
                                );
                                return eventProducer.publish(event).thenReturn(saved);
                            });
                });
    }
}
