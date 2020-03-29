package com.ciccc.cryptocurrency.model;/*
@author Agamenon
*/

import com.ciccc.cryptocurrency.enums.ExchangeCode;
import com.ciccc.cryptocurrency.util.json.JsonObjectIntegration;

import java.math.BigDecimal;

public abstract class Ticker {

    protected BigDecimal high;
    protected BigDecimal low;
    protected BigDecimal vol;
    protected BigDecimal last;
    protected BigDecimal buy;
    protected BigDecimal sell;
    protected Long date;
    protected Currency currency;
    protected ExchangeCode exchange;


    public abstract void setTicker(JsonObjectIntegration jsonObject);

    public ExchangeCode getExchange() {
        return exchange;
    }

    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public BigDecimal getVol() {
        return vol;
    }

    public BigDecimal getLast() {
        return last;
    }

    public BigDecimal getBuy() {
        return buy;
    }

    public BigDecimal getSell() {
        return sell;
    }

    public Long getDate() {
        return date;
    }

    //public EnumExchange getExchange();


}
