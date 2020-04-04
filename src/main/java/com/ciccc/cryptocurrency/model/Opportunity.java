package com.ciccc.cryptocurrency.model;
/*
@author Agamenon
*/

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Opportunity {

    private Currency currency;
    private Ticker buy;
    private Ticker sell;
    private BigDecimal spread;
    private BigDecimal spreadPercentage;
    private BigDecimal spreadValue;

    public Opportunity(UserConfiguration userConfiguration,Currency currency, Ticker buy, Ticker sell) {
        this.currency = currency;
        this.buy = buy;
        this.sell = sell;
        if(userConfiguration != null){
            setSpreadValue(userConfiguration.getValue());
        }

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

    public BigDecimal getSpread() {
        spread = BigDecimal.ZERO;
        if (buy != null && sell != null)
            spread = sell.getSell().subtract(buy.getBuy()).setScale(8);

        return spread;
    }

    public BigDecimal getSpreadPercentage() {
        spreadPercentage = BigDecimal.ZERO;
        if (buy != null) {
            spreadPercentage =
                    getSpread().divide(buy.getBuy(), MathContext.DECIMAL128)
                            .multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_EVEN);
        }
        return spreadPercentage;
    }

    public void setSpreadValue(BigDecimal investiment) {
        spreadValue = BigDecimal.ZERO;
        if (investiment !=null) {
                   spreadValue = getSpreadPercentage().divide(new BigDecimal("100"), MathContext.DECIMAL128)
                            .multiply(investiment).setScale(2, RoundingMode.HALF_EVEN);
        }
    }

    public BigDecimal getSpreadValue() {
        return spreadValue;
    }
}
