package com.falabella.stock.services;

import com.falabella.stock.exceptions.StockFullException;
import com.falabella.stock.models.Product;
import com.falabella.stock.repositories.ProductRepository;
import com.falabella.stock.exceptions.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    public static final int MAX = 99_999_999;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAll() {
        return this.productRepository.findAll();
    }

    @Override
    public Product getById(String id) throws ProductNotFoundException {
        Product product = this.productRepository.findBySku(id);
        if(product == null) {
            throw new ProductNotFoundException("El producto no existe");
        }
        return product;
    }

    @Override
    public Product create(Product product) {
        int nextSeq = this.productRepository.findNextSequenceValue();
        if (nextSeq >= MAX) {
            throw new StockFullException("El stock alcanzó el limite");
        }
        product.setSku("FAL-" + nextSeq);
        return this.productRepository.save(product);
    }

    @Override
    public Product update(String id, Product product) throws ProductNotFoundException {
        Product productToModify = this.productRepository.findBySku(id);
        if(productToModify == null) {
            throw new ProductNotFoundException("El producto no existe");
        }

        productToModify.setName(product.getName());
        productToModify.setBrand(product.getBrand());
        productToModify.setSize(product.getSize());
        productToModify.setImages(product.getImages());

        return this.productRepository.save(product);
    }

    @Override
    public boolean delete(String id) throws ProductNotFoundException {
        Product product = this.productRepository.findBySku(id);
        if(product == null) {
            throw new ProductNotFoundException("El producto no existe");
        }

        this.productRepository.delete(product);
        return true;
    }
}
