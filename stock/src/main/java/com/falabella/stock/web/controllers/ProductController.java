package com.falabella.stock.web.controllers;

import com.falabella.stock.models.Product;
import com.falabella.stock.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getProducts() {
        return this.productService.getAll();
    }

    @GetMapping("{sku}")
    public Product getProduct(@PathVariable String sku) {
        return this.productService.getById(sku);
    }

    @PostMapping
    public Product createProduct(@Valid @RequestBody Product product) {
        return this.productService.create(product);
    }

    @PutMapping("{sku}")
    public Product updateProduct(@PathVariable String sku, @Valid @RequestBody Product product) {
        return this.productService.update(sku, product);
    }

    @DeleteMapping("{sku}")
    public boolean deleteProduct(@PathVariable String sku) {
        return this.productService.delete(sku);
    }
}
