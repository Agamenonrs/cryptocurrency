package com.ciccc.cryptocurrency.model;
/*
@author Agamenon
*/

import com.ciccc.cryptocurrency.enums.ExchangeCode;
import com.ciccc.cryptocurrency.util.json.JsonObjectIntegration;

import java.math.BigDecimal;

public class ZBTicker extends Ticker {

    public ZBTicker(JsonObjectIntegration jsonObject, Currency currency) {
        super.currency = currency;
        exchange = ExchangeCode.ZB;
        setTicker(jsonObject);
    }

    @Override
    public void setTicker(JsonObjectIntegration jsonObject) {
        JsonObjectIntegration ticketJsonObject = jsonObject.get("ticker").asObject();
        high = new BigDecimal(ticketJsonObject.get("high").asString()).setScale(8);
        low = new BigDecimal(ticketJsonObject.get("low").asString()).setScale(8);
        vol = new BigDecimal(ticketJsonObject.get("vol").asString()).setScale(8);
        last = new BigDecimal(ticketJsonObject.get("last").asString()).setScale(8);
        buy = new BigDecimal(ticketJsonObject.get("buy").asString()).setScale(8);
        sell = new BigDecimal(ticketJsonObject.get("sell").asString()).setScale(8);
        date = Long.valueOf(jsonObject.get("date").asString());
    }
}
