package com.ciccc.cryptocurrency.model;
/*
@author Agamenon
*/

public class Opportunity {

    Currency currency;
    Ticker buy ;
    Ticker sell;

    public Opportunity(Currency currency, Ticker buy, Ticker sell) {
        this.currency = currency;
        this.buy = buy;
        this.sell = sell;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Ticker getBuy() {
        return buy;
    }

    public Ticker getSell() {
        return sell;
    }
}
