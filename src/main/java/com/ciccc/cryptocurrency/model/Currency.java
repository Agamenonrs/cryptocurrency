package com.ciccc.cryptocurrency.model;/*
@author Agamenon
*/

import java.io.Serializable;
import java.math.BigDecimal;

public class Currency implements Serializable {

    private Integer id;
    private String name;
    private String code;
    private BigDecimal price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
