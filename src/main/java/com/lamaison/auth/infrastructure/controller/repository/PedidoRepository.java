package com.lamaison.auth.repository;

import com.lamaison.auth.model.Pedido;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PedidoRepository extends ReactiveMongoRepository<Pedido, String> {
}