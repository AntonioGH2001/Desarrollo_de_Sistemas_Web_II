package com.tiendasara.Tienda.Sara.controllers;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.tiendasara.Tienda.Sara.models.Category;
import com.tiendasara.Tienda.Sara.models.Mark;
import com.tiendasara.Tienda.Sara.models.Product;
import com.tiendasara.Tienda.Sara.models.ProductDto;
import com.tiendasara.Tienda.Sara.services.CategoryRepository;
import com.tiendasara.Tienda.Sara.services.MarkRepository;
import com.tiendasara.Tienda.Sara.services.ProductRepository;

@Controller
@RequestMapping("/products")
public class ProductPageController {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final MarkRepository markRepository;

    public ProductPageController(
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            MarkRepository markRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.markRepository = markRepository;
    }

    @GetMapping({"", "/"})
    public String listarProductos(Model model) {
        model.addAttribute(
                "products",
                productRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
        );
        return "products/index";
    }

    @GetMapping("/create")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("productDto", new ProductDto());
        cargarCategoriasYMarcas(model);
        return "products/CreateProduct";
    }

    @PostMapping("/create")
    public String crearProducto(@ModelAttribute ProductDto productDto) {
        Product producto = new Product();
        producto.setDescripcion(productDto.getDescripcion());
        producto.setPrecio(productDto.getPrecio());
        producto.setCantidad(productDto.getCantidad());

        Category categoria = categoryRepository.findById(productDto.getCategoriaId())
                .orElseThrow();
        Mark marca = markRepository.findById(productDto.getMarcaId())
                .orElseThrow();

        producto.setCategoria(categoria);
        producto.setMarca(marca);
        productRepository.save(producto);

        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String mostrarFormularioEditar(@PathVariable Integer id, Model model) {
        Product producto = productRepository.findById(id).orElseThrow();

        ProductDto productDto = new ProductDto();
        productDto.setDescripcion(producto.getDescripcion());
        productDto.setPrecio(producto.getPrecio());
        productDto.setCantidad(producto.getCantidad());
        productDto.setCategoriaId(producto.getCategoria().getId());
        productDto.setMarcaId(producto.getMarca().getId());

        model.addAttribute("product", producto);
        model.addAttribute("productDto", productDto);
        cargarCategoriasYMarcas(model);

        return "products/EditProduct";
    }

    @PostMapping("/edit/{id}")
    public String actualizarProducto(
            @PathVariable Integer id,
            @ModelAttribute ProductDto productDto) {

        Product producto = productRepository.findById(id).orElseThrow();

        producto.setDescripcion(productDto.getDescripcion());
        producto.setPrecio(productDto.getPrecio());
        producto.setCantidad(productDto.getCantidad());
        producto.setCategoria(
                categoryRepository.findById(productDto.getCategoriaId()).orElseThrow()
        );
        producto.setMarca(
                markRepository.findById(productDto.getMarcaId()).orElseThrow()
        );

        productRepository.save(producto);
        return "redirect:/products";
    }

    @PostMapping("/delete/{id}")
    public String eliminarProducto(@PathVariable Integer id) {
        productRepository.deleteById(id);
        return "redirect:/products";
    }

    private void cargarCategoriasYMarcas(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("marks", markRepository.findAll());
    }
}