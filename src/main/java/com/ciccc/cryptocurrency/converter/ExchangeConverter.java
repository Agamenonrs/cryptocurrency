package com.ciccc.cryptocurrency.converter;/*
@author Agamenon
*/

import com.ciccc.cryptocurrency.model.Exchange;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ExchangeConverter implements Converter<String, Exchange> {

    @Override
    public Exchange convert(String s) {
        Exchange exchange = null;
        if(s !=null){
            exchange = new Exchange();
            exchange.setCodigo(s);
        }

        return  exchange;

    }
}
