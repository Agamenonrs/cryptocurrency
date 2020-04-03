package com.ciccc.cryptocurrency.enums;
/*
@author Agamenon
*/

public enum CoinCode {
    DOLAR_USDT("USDT", false),
    REAL_BRL("BRL",false),
    BITCOIN("BTC",true),
    ETHEREUM("ETH",true),
    LITECOIN("LTC",true),
    XRP("XRP",true);

    String code;
    boolean crypto;

    CoinCode(String code,boolean crypto ){
        this.code = code;
        this.crypto = crypto;
    }

    public String getCode() {
        return code;
    }

    public boolean isCrypto() {
        return crypto;
    }
}
