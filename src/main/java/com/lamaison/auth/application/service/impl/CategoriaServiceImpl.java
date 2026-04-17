package com.lamaison.auth.service.impl;

import org.springframework.stereotype.Service;

import com.lamaison.auth.model.Categoria;
import com.lamaison.auth.repository.CategoriaRepository;
import com.lamaison.auth.service.CategoriaService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public Mono<Categoria> crear(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public Flux<Categoria> listar() {
        return categoriaRepository.findAll();
    }

    @Override
    public Mono<Categoria> actualizar(String id, Categoria categoria) {
        return categoriaRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Categoría no encontrada")))
                .flatMap(existing -> {
                    existing.setNombre(categoria.getNombre());
                    existing.setDescripcion(categoria.getDescripcion());
                    return categoriaRepository.save(existing);
                });
    }

    @Override
    public Mono<Void> eliminar(String id) {
        return categoriaRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Categoría no encontrada")))
                .flatMap(categoriaRepository::delete);
    }
}