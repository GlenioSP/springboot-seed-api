package com.example.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class BaseDto {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public boolean isNew() {
        return this.id != null;
    }
}
