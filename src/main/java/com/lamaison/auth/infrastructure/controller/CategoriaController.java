package com.lamaison.auth.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lamaison.auth.model.Categoria;
import com.lamaison.auth.service.CategoriaService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin("*")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // Crear categoría
    @PostMapping
    public Mono<Categoria> crear(@RequestBody Categoria categoria) {
        return categoriaService.crear(categoria);
    }

    // Listar categorías
    @GetMapping
    public Flux<Categoria> listar() {
        return categoriaService.listar();
    }

    // Actualizar categoría
    @PutMapping("/{id}")
    public Mono<Categoria> actualizar(@PathVariable String id,
                                      @RequestBody Categoria categoria) {
        return categoriaService.actualizar(id, categoria);
    }

    // Eliminar categoría
    @DeleteMapping("/{id}")
    public Mono<Void> eliminar(@PathVariable String id) {
        return categoriaService.eliminar(id);
    }
}