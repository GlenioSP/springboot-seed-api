package com.example.service.impl;

import com.example.constant.MessageConstants;
import com.example.domain.Product;
import com.example.exception.EntityConflictException;
import com.example.exception.EntityNotFoundException;
import com.example.repository.ProductRepository;
import com.example.service.ProductService;
import com.example.service.dto.ProductDto;
import com.example.service.mapper.ProductMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ProductRepository productRepository;

    private ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
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
        logger.debug("Request to find product of id {}", id);
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            throw new EntityNotFoundException(MessageConstants.ENTITY_NOT_FOUND + id);
        }
        return productMapper.toDto(product.get());
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
