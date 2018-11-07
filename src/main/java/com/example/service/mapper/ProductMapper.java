package com.example.service.mapper;

import com.example.domain.Product;
import com.example.service.dto.ProductDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ProductMapper extends EntityMapper<ProductDto, Product> {
}
