package com.example.web.rest;

import com.example.service.dto.ProductDto;
import com.example.service.impl.ProductServiceImpl;
import com.example.web.rest.constant.ResourceTestConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
@EnableSpringDataWebSupport
@ActiveProfiles("test")
public class ProductResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductServiceImpl productServiceImpl;

    @Test
    public void shouldReturnNoElementsForFindAll() throws Exception {
        final Integer PAGE_NUMBER = 1;
        final Integer PAGE_SIZE = 5;

        Sort sort = new Sort(Direction.ASC, "id");
        Pageable pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE, sort);

        List<ProductDto> emptyListDto = new ArrayList<>();
        Page<ProductDto> emptyPageDto = new PageImpl<>(emptyListDto, pageable, emptyListDto.size());

        given(productServiceImpl.findAll(pageable)).willReturn(emptyPageDto);

        RequestBuilder request = get("/products")
                                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .param(ResourceTestConstants.REQUEST_PARAM_PAGE_NUMBER, PAGE_NUMBER.toString())
                                .param(ResourceTestConstants.REQUEST_PARAM_PAGE_SIZE, PAGE_SIZE.toString())
                                .param(ResourceTestConstants.REQUEST_PARAM_SORT, "id");

        mockMvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(0)));
    }
}
