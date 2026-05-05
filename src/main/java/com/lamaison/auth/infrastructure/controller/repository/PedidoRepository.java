package com.lamaison.auth.infrastructure.controller.repository;

import com.lamaison.auth.domain.model.Pedido;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PedidoRepository extends ReactiveMongoRepository<Pedido, String> {
}
