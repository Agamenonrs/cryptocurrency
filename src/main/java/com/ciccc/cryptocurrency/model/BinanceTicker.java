package com.ciccc.cryptocurrency.model;
/*
@author Agamenon
*/

import com.ciccc.cryptocurrency.enums.ExchangeCode;
import com.ciccc.cryptocurrency.util.json.JsonObjectIntegration;

import java.math.BigDecimal;

public class BinanceTicker extends Ticker {

    private final ExchangeCode exchange = ExchangeCode.BINC;

    public BinanceTicker(JsonObjectIntegration jsonObject, Currency currency) {
        super.currency = currency;
        setTicker(jsonObject);
    }

    @Override
    public void setTicker(JsonObjectIntegration jsonObject) {
        high = new BigDecimal(jsonObject.get("highPrice").asString());
        low = new BigDecimal(jsonObject.get("lowPrice").asString());
        vol = new BigDecimal(jsonObject.get("volume").asString());
        last = new BigDecimal(jsonObject.get("lastPrice").asString());
        buy = new BigDecimal(jsonObject.get("bidPrice").asString());
        sell = new BigDecimal(jsonObject.get("askPrice").asString());
        date = jsonObject.get("closeTime").asLong();
    }
}
