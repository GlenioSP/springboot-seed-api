package com.example.service.impl;

import com.example.constant.MessageConstants;
import com.example.domain.Product;
import com.example.exception.EntityConflictException;
import com.example.exception.EntityNotFoundException;
import com.example.repository.ProductRepository;
import com.example.service.ProductService;
import com.example.service.dto.ProductDto;
import com.example.service.mapper.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Primary
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ProductRepository productRepository;

    private ProductMapper productMapper;

    private ProductService2 productService2;

    private ProductService3 productService3;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper,
                              ProductService2 productService2, ProductService3 productService3) {
        this.productService2 = productService2;
        this.productService3 = productService3;
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDto> findAll(Pageable pageable) {
        logger.debug("Request to get all products");
        return productRepository.findAll(pageable).map(productMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto findOne(Long id) {
        logger.info("1 - Request to find product of id {}", id);
        Optional<Product> product1 = productRepository.findById(id);
        if (!product1.isPresent()) {
            throw new EntityNotFoundException(MessageConstants.ENTITY_NOT_FOUND + id);
        }
        CompletableFuture<ProductDto> product2 = productService2.findOne(id);
        CompletableFuture<ProductDto> product3 = productService3.findOne(id);

        ProductDto productDto1 = productMapper.toDto(product1.get());

        CompletableFuture.allOf(product2, product3);

        try {
            if (productDto1.getId().equals(product2.get().getId()) && productDto1.getId().equals(product3.get().getId())) {
                logger.info("1 - Threads results are equal");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return productDto1;
    }

    @Override
    public ProductDto create(ProductDto productDto) {
        logger.debug("Request to create product");
        if (!productDto.isNew()) {
            throw new EntityConflictException(MessageConstants.ENTITY_MUST_HAVE_NULL_ID);
        }
        return productMapper.toDto(productRepository.save(productMapper.toEntity(productDto)));
    }

    @Override
    public ProductDto update(Long id, ProductDto productDto) {
        logger.debug("Request to update product of id {}", id);
        findOne(id);
        productDto.setId(id);
        return productMapper.toDto(productRepository.save(productMapper.toEntity(productDto)));
    }

    @Override
    public void delete(Long id) {
        logger.debug("Request to delete product of id {}", id);
        findOne(id);
        productRepository.deleteById(id);
    }
}
