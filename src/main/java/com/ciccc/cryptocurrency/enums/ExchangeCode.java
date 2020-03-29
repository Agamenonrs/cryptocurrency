package com.ciccc.cryptocurrency.enums;/*
@author Agamenon
*/

public enum ExchangeCode {

    MBTC("Mercado Bitcoin"),
    BINC("Binance"),
    OKEX("OKEX"),
    ZB("ZB");

    private String exchangeName;

    ExchangeCode( String exchangeName ){
        this.exchangeName = exchangeName;
    }

    public String getExchangeName() {
        return exchangeName;
    }
}
