package com.ciccc.cryptocurrency.enums;
/*
@author Agamenon
*/

public enum CoinCode {
    DOLAR_USDT("USDT"),
    REAL_BRL("BRL"),
    BITCOIN("BTC"),
    ETHEREUM("ETH"),
    LITECOIN("LTC"),
    XRP("XRP");

    String code;

    CoinCode(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
