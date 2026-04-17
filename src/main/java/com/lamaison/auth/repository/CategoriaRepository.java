package com.lamaison.auth.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import com.lamaison.auth.model.Categoria;

public interface CategoriaRepository extends ReactiveMongoRepository<Categoria, String> {
}