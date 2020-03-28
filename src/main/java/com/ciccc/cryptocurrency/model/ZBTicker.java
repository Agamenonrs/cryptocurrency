package com.ciccc.cryptocurrency.model;
/*
@author Agamenon
*/

import com.ciccc.cryptocurrency.enums.ExchangeCode;
import com.ciccc.cryptocurrency.util.json.JsonObjectIntegration;

import java.math.BigDecimal;

public class ZBTicker extends Ticker {

    private final ExchangeCode exchange = ExchangeCode.MBTC;

    public ZBTicker(JsonObjectIntegration jsonObject, Currency currency) {
        super.currency = currency;
        setTicker(jsonObject);
    }

    @Override
    public void setTicker(JsonObjectIntegration jsonObject) {
        JsonObjectIntegration ticketJsonObject = jsonObject.get("ticker").asObject();
        high = new BigDecimal(ticketJsonObject.get("high").asString());
        low = new BigDecimal(ticketJsonObject.get("low").asString());
        vol = new BigDecimal(ticketJsonObject.get("vol").asString());
        last = new BigDecimal(ticketJsonObject.get("last").asString());
        buy = new BigDecimal(ticketJsonObject.get("buy").asString());
        sell = new BigDecimal(ticketJsonObject.get("sell").asString());
        date = Long.valueOf(jsonObject.get("date").asString());
    }
}
