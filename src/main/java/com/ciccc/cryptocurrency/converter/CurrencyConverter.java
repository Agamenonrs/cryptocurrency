package com.ciccc.cryptocurrency.converter;
/*
@author Agamenon
*/

import com.ciccc.cryptocurrency.model.Currency;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CurrencyConverter implements Converter<String,Currency> {

    @Override
    public Currency convert(String s) {
        Currency c = null;
        if(s != null){
            c = new Currency();
            c.setCode(s);
        }
        return c;
    }
}
