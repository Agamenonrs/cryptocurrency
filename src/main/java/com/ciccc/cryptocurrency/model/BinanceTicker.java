package com.ciccc.cryptocurrency.model;
/*
@author Agamenon
*/

import com.ciccc.cryptocurrency.enums.ExchangeCode;
import com.ciccc.cryptocurrency.util.json.JsonObjectIntegration;

import java.math.BigDecimal;

public class BinanceTicker extends Ticker {


    public BinanceTicker(JsonObjectIntegration jsonObject, Currency currency) {
        super.currency = currency;
        exchange = ExchangeCode.BINC;
        setTicker(jsonObject);
    }

    @Override
    public void setTicker(JsonObjectIntegration jsonObject) {
        high = new BigDecimal(jsonObject.get("highPrice").asString()).setScale(8);
        low = new BigDecimal(jsonObject.get("lowPrice").asString()).setScale(8);
        vol = new BigDecimal(jsonObject.get("volume").asString()).setScale(8);
        last = new BigDecimal(jsonObject.get("lastPrice").asString()).setScale(8);
        buy = new BigDecimal(jsonObject.get("bidPrice").asString()).setScale(8);
        sell = new BigDecimal(jsonObject.get("askPrice").asString()).setScale(8);
        date = jsonObject.get("closeTime").asLong();
    }
}
