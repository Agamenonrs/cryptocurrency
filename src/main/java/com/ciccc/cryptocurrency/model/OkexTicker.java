package com.ciccc.cryptocurrency.model;/*
@author Agamenon
*/

import com.ciccc.cryptocurrency.enums.ExchangeCode;
import com.ciccc.cryptocurrency.util.json.JsonObjectIntegration;

import java.math.BigDecimal;

public class OkexTicker extends Ticker {

    public OkexTicker(JsonObjectIntegration jsonObject,Currency currency){
        super.currency = currency;
        exchange = ExchangeCode.OKEX;
        setTicker(jsonObject);
    }

    @Override
    public void setTicker(JsonObjectIntegration jsonObject) {
        this.high = new BigDecimal(jsonObject.get("high_24h").asString());
        this.low = new BigDecimal(jsonObject.get("low_24h").asString());
        this.vol = new BigDecimal(jsonObject.get("base_volume_24h").asString());
        this.last = new BigDecimal(jsonObject.get("last").asString());
        this.buy = new BigDecimal(jsonObject.get("bid").asString());
        this.sell = new BigDecimal(jsonObject.get("ask").asString());
        //format "timestamp":"2020-03-29T19:23:43.373Z"
        //this.date = Long.valueOf(jsonObject.get("timestamp").toString());
    }
}
