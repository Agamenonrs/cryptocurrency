package com.ciccc.cryptocurrency.model;/*
@author Agamenon
*/

import com.ciccc.cryptocurrency.enums.ExchangeCode;
import com.ciccc.cryptocurrency.util.json.JsonObjectIntegration;

import java.math.BigDecimal;

public class MercadoBitcoinTicker extends Ticker {

    private final ExchangeCode exchange = ExchangeCode.MBTC;

    /**
     * Constructor based on JSON response.
     *
     * @param jsonObject Trade API JSON response
     */
    public MercadoBitcoinTicker(JsonObjectIntegration jsonObject, Currency currency) {
        super.currency = currency;
        setTicker(jsonObject);
    }

    @Override
    public void setTicker(JsonObjectIntegration jsonObject) {
        high = new BigDecimal(jsonObject.get("high").asString());
        low = new BigDecimal(jsonObject.get("low").asString());
        vol = new BigDecimal(jsonObject.get("vol").asString());
        last = new BigDecimal(jsonObject.get("last").asString());
        buy = new BigDecimal(jsonObject.get("buy").asString());
        sell = new BigDecimal(jsonObject.get("sell").asString());
        date = jsonObject.get("date").asLong();

    }
}
