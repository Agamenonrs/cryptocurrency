package com.ciccc.cryptocurrency.enums;
/*
@author Agamenon
*/

public enum CoinCode {
    DOLAR_USDT("USDT"),
    REAL_BRL("BRL");

    String code;

    CoinCode(String code){
        this.code = code;
    }

    public String getCodigo() {
        return code;
    }
}
