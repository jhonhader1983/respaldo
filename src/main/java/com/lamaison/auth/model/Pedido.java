package com.lamaison.auth.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "pedidos")
public class Pedido {

    @Id
    private String id;
    private UsuarioPedido usuario;
    private List<ItemPedido> items;
    private Double total;
    private String estado;
    private LocalDateTime createdAt;

    public Pedido() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public UsuarioPedido getUsuario() { return usuario; }
    public void setUsuario(UsuarioPedido usuario) { this.usuario = usuario; }
    public List<ItemPedido> getItems() { return items; }
    public void setItems(List<ItemPedido> items) { this.items = items; }
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public static class UsuarioPedido {
        private String nombre;
        private String email;

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }

    public static class ItemPedido {
        private String nombre;
        private Double precio;
        private Integer cantidad;

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public Double getPrecio() { return precio; }
        public void setPrecio(Double precio) { this.precio = precio; }
        public Integer getCantidad() { return cantidad; }
        public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    }
}