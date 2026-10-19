package com.tiendasara.Tienda.Sara.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.tiendasara.Tienda.Sara.models.Product;
import com.tiendasara.Tienda.Sara.services.ProductRepository;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Product> obtenerProductos() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public Product obtenerProducto(@PathVariable Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Product crearProducto(@RequestBody Product producto) {
        return productRepository.save(producto);
    }

    @PutMapping("/{id}")
    public Product actualizarProducto(
            @PathVariable Integer id,
            @RequestBody Product productoActualizado) {

        return productRepository.findById(id).map(producto -> {
            producto.setDescripcion(productoActualizado.getDescripcion());
            producto.setPrecio(productoActualizado.getPrecio());
            producto.setCantidad(productoActualizado.getCantidad());
            producto.setCategoria(productoActualizado.getCategoria());
            producto.setMarca(productoActualizado.getMarca());
            return productRepository.save(producto);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable Integer id) {
        productRepository.deleteById(id);
    }
}