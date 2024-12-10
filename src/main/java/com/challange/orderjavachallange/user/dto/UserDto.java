package com.challange.orderjavachallange.user.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;


    public String getEmail() {
        return email;
    }

    public void setEmail(String skuCode) {
        this.email = skuCode;
    }

    public UserDto(Long id, String name, String skuCode) {
        this.id = id;
        this.name = name;
        this.email = skuCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}