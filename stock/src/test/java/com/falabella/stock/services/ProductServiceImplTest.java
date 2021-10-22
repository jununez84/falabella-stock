package com.falabella.stock.services;

import com.falabella.stock.exceptions.StockFullException;
import com.falabella.stock.models.Image;
import com.falabella.stock.models.ImageType;
import com.falabella.stock.models.Product;
import com.falabella.stock.repositories.ProductRepository;
import com.falabella.stock.exceptions.ProductNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

    @InjectMocks
    private ProductService service = new ProductServiceImpl();

    @Mock
    private ProductRepository productRepository;

    private List<Product> products = new ArrayList<>();

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.products.add(getProduct("FAL-1000001", "Producto uno"));
        this.products.add(getProduct("FAL-1000002", "Producto dos"));
    }

    @Test
    public void getAll_shouldReturnAllProducts() {
        Mockito.when(this.productRepository.findAll()).thenReturn(this.products);

        List<Product> response = this.service.getAll();

        Mockito.verify(this.productRepository, Mockito.times(1)).findAll();
        Assert.assertEquals(response.size(), 2);
        Assert.assertEquals(response.get(0).getSku(), "FAL-1000001");
        Assert.assertEquals(response.get(1).getSku(), "FAL-1000002");
    }

    @Test
    public void getById_shouldReturnAProduct() {
        Mockito.when(this.productRepository.findBySku("FAL-1000001"))
               .thenReturn(this.products.stream().findFirst().get());

        Product response = this.service.getById("FAL-1000001");

        Assert.assertEquals(response.getSku(), "FAL-1000001");
        Assert.assertEquals(response.getName(), "Producto uno");
    }

    @Test(expected = Exception.class)
    public void getById_shouldThrowException() {
        Mockito.when(this.productRepository.findBySku("FAL-1000003"))
                .thenReturn(null);

        Product response = this.service.getById("FAL-1000003");
    }

    @Test
    public void delete_shouldReturnAProduct() {
        Product product = this.products.stream().findFirst().get();
        Mockito.when(this.productRepository.findBySku("FAL-1000001"))
                .thenReturn(product);

        Mockito.doNothing().when(this.productRepository).delete(Mockito.any(Product.class));

        boolean response = this.service.delete("FAL-1000001");

        Mockito.verify(this.productRepository, Mockito.times(1)).delete(product);
        Assert.assertTrue(response);
    }

    @Test(expected = ProductNotFoundException.class)
    public void delete_shouldThrowException() {
        Mockito.when(this.productRepository.findBySku("FAL-1000001"))
                .thenReturn(null);

        this.service.delete("FAL-1000001");
    }

    @Test
    public void create_shouldReturnACreatedProduct() throws Exception {
        Product product = this.getProduct("FAL-1000003", "Producto tres");

        Mockito.when(this.productRepository.findNextSequenceValue())
                .thenReturn(1_000_003);

        Mockito.when(this.productRepository.save(Mockito.any(Product.class)))
                .thenReturn(product);

        Product response = this.service.create(product);

        Mockito.verify(this.productRepository, Mockito.times(1)).findNextSequenceValue();
        Mockito.verify(this.productRepository, Mockito.times(1)).save(product);
        Assert.assertEquals(response.getSku(), "FAL-1000003");
        Assert.assertEquals(response.getName(), "Producto tres");
    }

    @Test(expected = StockFullException.class)
    public void create_shouldThrowStockFullException() throws Exception {
        Product product = this.getProduct("FAL-1000004", "Producto tres");

        Mockito.when(this.productRepository.findNextSequenceValue())
                .thenReturn(ProductServiceImpl.MAX);

        Product response = this.service.create(product);
    }

    @Test
    public void update_shouldUpdateAProduct() throws Exception {
        Product product = this.getProduct("FAL-1000001", "Producto tres");

        Mockito.when(this.productRepository.findBySku("FAL-1000001"))
                .thenReturn(this.products.stream().findFirst().get());

        Mockito.when(this.productRepository.save(Mockito.any(Product.class)))
                .thenReturn(product);

        Product response = this.service.update("FAL-1000001", product);

        Mockito.verify(this.productRepository, Mockito.times(1)).findBySku("FAL-1000001");
        Mockito.verify(this.productRepository, Mockito.times(1)).save(product);
        Assert.assertEquals(response.getSku(), product.getSku());
        Assert.assertEquals(response.getName(), product.getName());
    }

    @Test(expected = ProductNotFoundException.class)
    public void update_shouldThrowAException() throws Exception {
        Product product = this.getProduct("FAL-1000004", "Producto cuatro");

        Mockito.when(this.productRepository.findBySku("FAL-1000004"))
                .thenReturn(null);

        Product response = this.service.update("FAL-1000004", product);
    }

    private Product getProduct(String sku, String name) {
        return Product.builder()
                .sku(sku)
                .price(30.00)
                .brand("Marca")
                .images(Arrays.asList(Image.builder().type(ImageType.MAIN).url("http://image.png").build()))
                .name(name)
                .build();
    }
}