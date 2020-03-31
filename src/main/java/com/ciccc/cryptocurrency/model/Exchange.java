package com.ciccc.cryptocurrency.model;
/*
@author Agamenon
*/

import java.io.Serializable;
import java.util.Objects;

public class Exchange implements Serializable {

    private String name;
    private String codigo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exchange exchange = (Exchange) o;
        return codigo.equals(exchange.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
