package com.lamaison.auth.controller;

import com.lamaison.auth.model.Producto;
import com.lamaison.auth.repository.ProductoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoRepository productoRepository;

    public ProductoController(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @GetMapping
    public Flux<Producto> getAll() {
        return productoRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Producto> create(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }

    @PutMapping("/{id}")
    public Mono<Producto> update(@PathVariable String id, @RequestBody Producto producto) {
        return productoRepository.findById(id)
                .flatMap(existing -> {
                    existing.setNombre(producto.getNombre());
                    existing.setDescripcion(producto.getDescripcion());
                    existing.setPrecio(producto.getPrecio());
                    existing.setImagen(producto.getImagen());
                    existing.setCategoria(producto.getCategoria());
                    return productoRepository.save(existing);
                });
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable String id) {
        return productoRepository.deleteById(id);
    }
}