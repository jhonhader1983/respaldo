package com.lamaison.auth.infrastructure.controller.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import com.lamaison.auth.domain.model.Categoria;

public interface CategoriaRepository extends ReactiveMongoRepository<Categoria, String> {
}
