package com.example.web.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/")
public class HomeResource {

    @GetMapping
    public void swaggerUi(HttpServletResponse response) throws IOException {
        response.sendRedirect("swagger-ui.html");
    }
}
