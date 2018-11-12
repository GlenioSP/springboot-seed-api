//package com.example.service;
//
//import com.example.domain.Product;
//import com.example.repository.ProductRepository;
//import com.example.service.dto.ProductDto;
//import com.example.service.impl.ProductServiceImpl;
//import com.example.service.mapper.ProductMapper;
//import com.example.util.ProductTestUtils;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mapstruct.factory.Mappers;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.data.domain.*;
//import org.springframework.data.domain.Sort.Direction;
//
//import java.util.Iterator;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.*;
//
//@RunWith(MockitoJUnitRunner.class)
//public class ProductServiceTest {
//
//    private ProductService productService;
//
//    @Mock
//    private ProductRepository productRepository;
//
//    private ProductTestUtils testUtils;
//
//    @Before
//    public void setup() {
//        ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);
//        productService = new ProductServiceImpl(productRepository, productMapper);
//        testUtils = new ProductTestUtils();
//    }
//
//    @Test
//    public void shouldReturnAllProductsWhenFindAllIsCalled() {
//        List<Product> products = testUtils.createProductList();
//        List<ProductDto> productDtos = testUtils.createProductDtoList();
//
//        Sort sort = new Sort(Direction.ASC, "id");
//        Pageable pageable = PageRequest.of(1, 5, sort);
//
//        Page<Product> productPage = new PageImpl<>(products, pageable, products.size());
//        Page<ProductDto> productPageDto = new PageImpl<>(productDtos, pageable, productDtos.size());
//
//        when(productRepository.findAll(pageable)).thenReturn(productPage);
//
//        Page<ProductDto> retrievedProductPageDto = productService.findAll(pageable);
//
//        Iterator<ProductDto> iProductDto = productPageDto.getContent().iterator();
//        Iterator<ProductDto> iRetrievedProductDto = retrievedProductPageDto.getContent().iterator();
//
//        while(iProductDto.hasNext() && iRetrievedProductDto.hasNext()) {
//            assertThat(iProductDto.next()).isEqualToComparingFieldByField(iRetrievedProductDto.next());
//        }
//    }
//
//    @Test
//    public void shouldReturnProductWhenFindOneIsCalled() {
//        Long id = 1L;
//        Product product = testUtils.createProduct(id);
//        ProductDto productDto = testUtils.createProductDto(id);
//
//        when(productRepository.findById(id)).thenReturn(Optional.of(product));
//        ProductDto retrievedProduct = productService.findOne(id);
//
//        assertThat(retrievedProduct).isEqualToComparingFieldByField(productDto);
//    }
//
//    @Test
//    public void shouldReturnCreatedProductWhenCreateProductIsCalled() {
//        Long id = null;
//        Product product = testUtils.createProduct(id);
//        ProductDto productDto = testUtils.createProductDto(id);
//
//        Product productWithId = testUtils.createProduct(1L);
//        when(productRepository.save(product)).thenReturn(productWithId);
//        ProductDto productDtoWithId = testUtils.createProductDto(1L);
//
//        ProductDto savedProduct = productService.create(productDto);
//        assertThat(savedProduct).isEqualToComparingFieldByField(productDtoWithId);
//    }
//
//    @Test
//    public void shouldReturnUpdatedProductWhenUpdateProductIsCalled() {
//        Long id = 1L;
//        Product product = testUtils.createProduct(id);
//
//        Product updatedProduct = testUtils.createProduct(id);
//        updatedProduct.setDescription("New Spring Framework Shirt");
//        ProductDto updatedProductDto = testUtils.createProductDto(id);
//        updatedProductDto.setDescription("New Spring Framework Shirt");
//
//        when(productRepository.findById(id)).thenReturn(Optional.of(product));
//        when(productRepository.save(updatedProduct)).thenReturn(updatedProduct);
//
//        ProductDto savedProduct = productService.update(id, updatedProductDto);
//        assertThat(savedProduct).isEqualToComparingFieldByField(updatedProductDto);
//    }
//
//    @Test
//    public void shouldCallDeleteMethodOfProductRepositoryWhenDeleteProductIsCalled() {
//        Long id = 1L;
//        Product product = testUtils.createProduct(id);
//
//        when(productRepository.findById(id)).thenReturn(Optional.of(product));
//
//        doNothing().when(productRepository).deleteById(id);
//        productService.delete(id);
//        verify(productRepository, times(1)).deleteById(id);
//    }
//}