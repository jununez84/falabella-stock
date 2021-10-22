package com.falabella.stock.web.controllers;

import com.falabella.stock.models.Product;
import com.falabella.stock.services.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

    @Mock
    private ProductService service;

    @InjectMocks
    private ProductController controller = new ProductController();

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getProducts() {
        List<Product> products = new ArrayList<>();
        Mockito.when(this.service.getAll()).thenReturn(products);

        this.controller.getProducts();

        Mockito.verify(this.service, Mockito.times(1)).getAll();
    }

    @Test
    public void getProduct() throws Exception {
        Mockito.when(this.service.getById(Mockito.anyString())).thenReturn(Product.builder().build());

        this.controller.getProduct("FAL-1000001");

        Mockito.verify(this.service, Mockito.times(1)).getById("FAL-1000001");
    }

    @Test
    public void createProduct() throws Exception {
        Product product = Product.builder().build();
        Mockito.when(this.service.create(Mockito.any(Product.class))).thenReturn(product);

        this.controller.createProduct(product);

        Mockito.verify(this.service, Mockito.times(1)).create(product);
    }

    @Test
    public void updateProduct() throws Exception {
        Product product = Product.builder().build();
        Mockito.when(this.service.update(Mockito.anyString(), Mockito.any(Product.class))).thenReturn(product);

        this.controller.updateProduct("FAL-1000001", product);

        Mockito.verify(this.service, Mockito.times(1)).update("FAL-1000001", product);
    }

    @Test
    public void deleteProduct() throws Exception {
        Product product = Product.builder().build();
        Mockito.when(this.service.delete(Mockito.anyString())).thenReturn(true);

        this.controller.deleteProduct("FAL-1000001");

        Mockito.verify(this.service, Mockito.times(1)).delete("FAL-1000001");
    }
}