package com.lamaison.auth.application.service;

import com.lamaison.auth.domain.model.Categoria;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoriaService {

    Mono<Categoria> crear(Categoria categoria);

    Flux<Categoria> listar();

    Mono<Categoria> actualizar(String id, Categoria categoria);

    Mono<Void> eliminar(String id);
}
