package com.example.repository;

import com.example.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testSaveMethodToCreateAndUpdateProduct(){
        Product product = new Product();
        product.setDescription("Spring Framework Shirt");
        product.setPrice(new BigDecimal("18.95"));
        product.setProductId("1234");

        assertNull(product.getId());

        Product savedProduct = productRepository.save(product);

        assertNotNull(savedProduct.getId());

        Product fetchedProduct = productRepository.findById(savedProduct.getId()).orElse(null);

        assertNotNull(fetchedProduct);
        assertEquals(fetchedProduct, savedProduct);

        fetchedProduct.setDescription("New Spring Framework Shirt");
        productRepository.save(fetchedProduct);

        Product fetchedUpdatedProduct = productRepository.findById(fetchedProduct.getId()).orElse(null);

        assertNotNull(fetchedUpdatedProduct);
        assertEquals(fetchedUpdatedProduct.getDescription(), fetchedProduct.getDescription());

        long productCount = productRepository.count();
        assertEquals(1, productCount);

        List<Product> products = productRepository.findAll();
        assertEquals(1, products.size());
    }
}
