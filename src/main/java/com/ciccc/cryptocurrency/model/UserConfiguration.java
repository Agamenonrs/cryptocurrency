package com.ciccc.cryptocurrency.model;
/*
@author Agamenon
*/

import com.ciccc.cryptocurrency.enums.ExchangeCode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class UserConfiguration  implements Serializable {


    BigDecimal value = BigDecimal.ZERO;
    BigDecimal spreadMin = BigDecimal.ZERO;
    List<Currency> currencies;
    List<ExchangeCode> exchanges;

    public UserConfiguration() {
        this.currencies = new ArrayList<>();
        this.exchanges = new ArrayList<>();
    }

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

    public void clear(){
        setValue(BigDecimal.ZERO);
        setSpreadMin(BigDecimal.ZERO);
        getExchanges().clear();
        getCurrencies().clear();
    }
}
