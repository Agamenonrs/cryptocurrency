package com.ciccc.cryptocurrency.model;
/*
@author Agamenon
*/

import com.ciccc.cryptocurrency.enums.ExchangeCode;

import java.math.BigDecimal;
import java.util.List;

public class UserConfiguration {

    BigDecimal value;
    BigDecimal spreadMin;
    List<Currency> currencies;
    List<ExchangeCode> exchanges;

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getSpreadMin() {
        return spreadMin;
    }

    public void setSpreadMin(BigDecimal spreadMin) {
        this.spreadMin = spreadMin;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public List<ExchangeCode> getExchanges() {
        return exchanges;
    }

    public void setExchanges(List<ExchangeCode> exchanges) {
        this.exchanges = exchanges;
    }
}
