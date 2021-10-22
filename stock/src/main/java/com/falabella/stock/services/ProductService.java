package com.falabella.stock.services;

import com.falabella.stock.models.Product;

import java.util.List;

public interface ProductService {
    public List<Product> getAll();
    public Product getById(String id);
    public Product create(Product product);
    public Product update(String id, Product product);
    public boolean delete(String id);
}
