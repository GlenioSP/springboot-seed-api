package com.example.web.rest;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.service.dto.ProductDto;
import com.example.service.impl.ProductServiceImpl;
import com.example.web.rest.constant.ResourceTestConstants;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductServiceImpl productServiceImpl;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnNoElementsForFindAll() throws Exception {
        final Integer PAGE_NUMBER = 1;
        final Integer PAGE_SIZE = 5;

        Sort sort = new Sort(Direction.ASC, "id");
        Pageable pageable = new PageRequest(PAGE_NUMBER, PAGE_SIZE, sort);

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
