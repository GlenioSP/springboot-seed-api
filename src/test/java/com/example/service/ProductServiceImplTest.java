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
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ProductServiceImplTest {

    private ProductServiceImpl productServiceImpl;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private Product product;

    @Mock
    private ProductDto productDto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        productServiceImpl = new ProductServiceImpl(productRepository, productMapper);
    }

    @Test
    public void shouldReturnProduct_whenFindOneIsCalled() {
        when(productRepository.findById(5L)).thenReturn(Optional.of(product));
        when(productMapper.toDto(product)).thenReturn(productDto);
        ProductDto retrievedProduct = productServiceImpl.findOne(5L);
        assertThat(retrievedProduct, is(equalTo(productDto)));
    }

    @Test
    public void shouldReturnProduct_whenSaveProductIsCalled() {
        when(productMapper.toEntity(productDto)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toDto(product)).thenReturn(productDto);
        ProductDto savedProduct = productServiceImpl.create(productMapper.toDto(product));
        assertThat(savedProduct, is(equalTo(productDto)));
    }

    @Test
    public void shouldCallDeleteMethodOfProductRepository_whenDeleteProductIsCalled() {
        when(productRepository.findById(5L)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).deleteById(5L);
        productServiceImpl.delete(5L);
        verify(productRepository, times(1)).deleteById(5L);
    }
}