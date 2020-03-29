package com.ciccc.cryptocurrency.model;
/*
@author Agamenon
*/

import com.ciccc.cryptocurrency.enums.ExchangeCode;
import com.ciccc.cryptocurrency.util.json.JsonObjectIntegration;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class MercadoBitcoinTicker extends Ticker {

    BigDecimal dollarPrice = BigDecimal.ZERO;

    /**
     * Constructor based on JSON response.
     *
     * @param jsonObject Trade API JSON response
     */
    public MercadoBitcoinTicker(JsonObjectIntegration jsonObject, Currency currency, BigDecimal dollarPrice) { super.currency = currency;
        exchange = ExchangeCode.MBTC;
        this.dollarPrice = dollarPrice;
        setTicker(jsonObject);
    }

    @Override
    public void setTicker(JsonObjectIntegration jsonObject) {
        high = new BigDecimal(jsonObject.get("high").asString()).divide(dollarPrice, MathContext.DECIMAL128).setScale(8, RoundingMode.HALF_EVEN);
        low = new BigDecimal(jsonObject.get("low").asString()).divide(dollarPrice,MathContext.DECIMAL128).setScale(8, RoundingMode.HALF_EVEN);
        vol = new BigDecimal(jsonObject.get("vol").asString()).divide(dollarPrice,MathContext.DECIMAL128).setScale(8, RoundingMode.HALF_EVEN);
        last = new BigDecimal(jsonObject.get("last").asString()).divide(dollarPrice,MathContext.DECIMAL128).setScale(8, RoundingMode.HALF_EVEN);
        buy = new BigDecimal(jsonObject.get("buy").asString()).divide(dollarPrice,MathContext.DECIMAL128).setScale(8, RoundingMode.HALF_EVEN);
        sell = new BigDecimal(jsonObject.get("sell").asString()).divide(dollarPrice,MathContext.DECIMAL128).setScale(8, RoundingMode.HALF_EVEN);
        date = jsonObject.get("date").asLong();

    }
}
