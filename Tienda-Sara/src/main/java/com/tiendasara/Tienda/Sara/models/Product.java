package com.tiendasara.Tienda.Sara.models;

import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity
@Table(name = "Productos")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Descripcion", nullable = false, length = 200)
    private String descripcion;

    @Column(name = "Precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(name = "Cantidad", nullable = false)
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "IdCategoria", nullable = false)
    private Category categoria;

    @ManyToOne
    @JoinColumn(name = "IdMarca", nullable = false)
    private Mark marca;

    public Product() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Category getCategoria() {
        return categoria;
    }

    public void setCategoria(Category categoria) {
        this.categoria = categoria;
    }

    public Mark getMarca() {
        return marca;
    }

    public void setMarca(Mark marca) {
        this.marca = marca;
    }
}