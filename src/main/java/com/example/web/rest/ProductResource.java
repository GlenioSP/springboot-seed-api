package com.example.web.rest;

import com.example.service.ProductService;
import com.example.service.dto.ProductDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/products")
@Api(value = "onlinestore", tags = "Operations pertaining to products in an Online Store")
public class ProductResource {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ProductService productService;

    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @ApiOperation(value = "View a paginated list of available products", response = Page.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<ProductDto>> findAll(Pageable pageable) {
        Page<ProductDto> page = productService.findAll(pageable);
        return new ResponseEntity<>(page.getContent(), HttpStatus.OK);
    }

    @ApiOperation(value = "Find a product with an ID", response = ProductDto.class)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public CompletableFuture<ResponseEntity<ProductDto>> findOne(@PathVariable Long id) {
        logger.info("Request one product");
        return CompletableFuture.supplyAsync(() -> new ResponseEntity<>(productService.findOne(id), HttpStatus.OK));
    }

    @ApiOperation(value = "Add/Create a product", response = ProductDto.class)
    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ProductDto> create(UriComponentsBuilder b, @Valid @RequestBody ProductDto productDto) {
        ProductDto createdProduct = productService.create(productDto);
        UriComponents uriComponents = b.path("/api/products/{id}").buildAndExpand(createdProduct.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponents.toUri());
        return new ResponseEntity<>(createdProduct, headers, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update a product", response = ProductDto.class)
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ProductDto> update(@PathVariable Long id, @Valid @RequestBody ProductDto productDto) {
        return new ResponseEntity<>(productService.update(id, productDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a product")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity delete(@PathVariable Long id) {
        productService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
