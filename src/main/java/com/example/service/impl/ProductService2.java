package com.example.service.impl;

import com.example.constant.MessageConstants;
import com.example.domain.Product;
import com.example.exception.EntityConflictException;
import com.example.exception.EntityNotFoundException;
import com.example.repository.ProductRepository;
import com.example.service.dto.ProductDto;
import com.example.service.mapper.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
public class ProductService2 {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ProductRepository productRepository;

    private ProductMapper productMapper;

    public ProductService2(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    
    @Transactional(readOnly = true)
    public Page<ProductDto> findAll(Pageable pageable) {
        logger.debug("Request to get all products");
        return productRepository.findAll(pageable).map(productMapper::toDto);
    }

    @Async
    @Transactional(readOnly = true)
    public CompletableFuture<ProductDto> findOne(Long id) {
        logger.info("2 - Request to find product of id {}", id);
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            throw new EntityNotFoundException(MessageConstants.ENTITY_NOT_FOUND + id);
        }
        return CompletableFuture.completedFuture(productMapper.toDto(product.get()));
    }

    
    public ProductDto create(ProductDto productDto) {
        logger.debug("Request to create product");
        if (!productDto.isNew()) {
            throw new EntityConflictException(MessageConstants.ENTITY_MUST_HAVE_NULL_ID);
        }
        return productMapper.toDto(productRepository.save(productMapper.toEntity(productDto)));
    }

    
    public ProductDto update(Long id, ProductDto productDto) {
        logger.debug("Request to update product of id {}", id);
        findOne(id);
        productDto.setId(id);
        return productMapper.toDto(productRepository.save(productMapper.toEntity(productDto)));
    }

    
    public void delete(Long id) {
        logger.debug("Request to delete product of id {}", id);
        findOne(id);
        productRepository.deleteById(id);
    }
}
