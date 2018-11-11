package com.example.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.domain.Product;
import com.example.repository.ProductRepository;
import com.example.service.dto.ProductDto;
import com.example.service.impl.ProductServiceImpl;
import com.example.service.mapper.ProductMapper;
import com.example.util.ProductTestUtils;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    private ProductTestUtils testUtils;

    @Before
    public void setup() {
        productService = new ProductServiceImpl(productRepository, productMapper);
        testUtils = new ProductTestUtils();
    }

    @Test
    public void shouldReturnProduct_whenFindOneIsCalled() {
        Long id = 1L;
        Product product = testUtils.createProduct(id);
        ProductDto productDto = testUtils.createProductDto(id);

        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(productMapper.toDto(product)).thenReturn(productDto);
        ProductDto retrievedProduct = productService.findOne(id);
        assertThat(retrievedProduct, is(equalTo(productDto)));
    }

    @Test
    public void shouldReturnCreatedProduct_whenCreateProductIsCalled() {
        Long id = null;
        Product product = testUtils.createProduct(id);
        ProductDto productDto = testUtils.createProductDto(id);

        when(productMapper.toEntity(productDto)).thenReturn(product);
        Product productWithId = testUtils.createProduct(1L);
        when(productRepository.save(product)).thenReturn(productWithId);
        ProductDto productDtoWithId = testUtils.createProductDto(1L);
        when(productMapper.toDto(productWithId)).thenReturn(productDtoWithId);

        ProductDto savedProduct = productService.create(productDto);
        assertThat(savedProduct, is(equalTo(productDtoWithId)));
    }

    @Test
    public void shouldReturnUpdatedProduct_whenUpdateProductIsCalled() {
        Long id = 1L;
        Product product = testUtils.createProduct(id);
        ProductDto productDto = testUtils.createProductDto(id);

        Product updatedProduct = testUtils.createProduct(id);
        updatedProduct.setDescription("New Spring Framework Shirt");
        ProductDto updatedProductDto = testUtils.createProductDto(id);
        updatedProduct.setDescription("New Spring Framework Shirt");

        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(productMapper.toDto(product)).thenReturn(productDto);

        when(productMapper.toEntity(updatedProductDto)).thenReturn(updatedProduct);
        when(productRepository.save(updatedProduct)).thenReturn(updatedProduct);
        when(productMapper.toDto(updatedProduct)).thenReturn(updatedProductDto);

        ProductDto savedProduct = productService.update(id, updatedProductDto);
        assertThat(savedProduct, is(equalTo(updatedProductDto)));
    }

    @Test
    public void shouldCallDeleteMethodOfProductRepository_whenDeleteProductIsCalled() {
        Long id = 1L;
        Product product = testUtils.createProduct(id);
        ProductDto productDto = testUtils.createProductDto(id);

        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(productMapper.toDto(product)).thenReturn(productDto);

        doNothing().when(productRepository).deleteById(id);
        productService.delete(id);
        verify(productRepository, times(1)).deleteById(id);
    }
}