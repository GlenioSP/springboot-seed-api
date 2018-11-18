package com.example.service.mapper;

import com.example.domain.Product;
import com.example.service.dto.ProductDto;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class ProductMapperTest {

    private ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @Test
    public void givenEntityToDtoWhenMapsThenCorrect() {
        Product product = new Product();
        product.setProductId("1");
        product.setDescription("Product 1");
        product.setPrice(new BigDecimal(1));

        ProductDto productDto = productMapper.toDto(product);

        assertEquals(product.getProductId(), productDto.getProductId());
        assertEquals(product.getDescription(), productDto.getDescription());
        assertEquals(product.getPrice(), productDto.getPrice());
    }

    @Test
    public void givenDtoToEntityWhenMapsThenCorrect() {
        ProductDto productDto = new ProductDto();
        productDto.setProductId("1");
        productDto.setDescription("Product 1");
        productDto.setPrice(new BigDecimal(1));

        Product product = productMapper.toEntity(productDto);
        assertEquals(product.getProductId(), productDto.getProductId());
        assertEquals(product.getDescription(), productDto.getDescription());
        assertEquals(product.getPrice(), productDto.getPrice());
    }
}
