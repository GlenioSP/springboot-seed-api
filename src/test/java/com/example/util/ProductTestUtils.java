package com.example.util;

import com.example.domain.Product;
import com.example.service.dto.ProductDto;
import com.example.service.mapper.ProductMapper;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductTestUtils {

    private ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    public Product createProduct(Long id) {
        Product product = new Product();
        product.setId(id);
        product.setProductId("1234");
        product.setDescription("Spring Framework Shirt");
        product.setPrice(new BigDecimal("18.95"));
        return product;
    }

    public ProductDto createProductDto(Long id) {
        return this.productMapper.toDto(createProduct(id));
    }

    public List<Product> createProductList() {
        List<Product> products = new ArrayList<>();
        products.add(createProduct(1L));
        products.add(createProduct(2L));
        products.add(createProduct(3L));
        products.add(createProduct(4L));
        return products;
    }

    public List<ProductDto> createProductDtoList() {
        return this.productMapper.toDto(createProductList());
    }
}
