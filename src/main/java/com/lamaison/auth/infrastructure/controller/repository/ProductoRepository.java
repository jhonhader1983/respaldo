package com.lamaison.auth.infrastructure.controller.repository;

import com.lamaison.auth.domain.model.Producto;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductoRepository extends ReactiveMongoRepository<Producto, String> {
}
