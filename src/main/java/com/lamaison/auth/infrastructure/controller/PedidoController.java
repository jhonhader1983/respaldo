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
import com.lamaison.auth.domain.model.Pedido;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoRepository pedidoRepository;

    public PedidoController(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
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
        return pedidoRepository.save(pedido);
    }

    @PutMapping("/{id}")
    public Mono<Pedido> updateEstado(@PathVariable String id, @RequestBody Pedido pedido) {
        return pedidoRepository.findById(id)
                .flatMap(existing -> {
                    existing.setEstado(pedido.getEstado());
                    return pedidoRepository.save(existing);
                });
    }
}
