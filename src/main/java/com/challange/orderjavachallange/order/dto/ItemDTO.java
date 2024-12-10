package com.challange.orderjavachallange.order.dto;

import lombok.Data;

@Data
public class ItemDTO {
    private Long id;
    private String name;
    private String skuCode;


    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public ItemDTO(Long id, String name, String skuCode) {
        this.id = id;
        this.name = name;
        this.skuCode = skuCode;
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