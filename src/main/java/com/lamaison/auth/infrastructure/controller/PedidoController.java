package com.lamaison.auth.controller;

import com.lamaison.auth.model.Pedido;
import com.lamaison.auth.repository.PedidoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

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