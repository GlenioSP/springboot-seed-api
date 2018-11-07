package com.example.service;

import com.example.service.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Page<ProductDto> findAll(Pageable pageable);

    ProductDto findOne(Long id);

    ProductDto create(ProductDto productDTO);

    ProductDto update(ProductDto productDTO);

    void delete(Long id);
}
