package com.falabella.stock.repositories;

import com.falabella.stock.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findBySku(String sku);

    @Query(value = "SELECT nextval('my_seq')", nativeQuery = true)
    int findNextSequenceValue();
}
