package com.ciccc.cryptocurrency.service;/*
@author Agamenon
*/

import com.ciccc.cryptocurrency.model.Currency;
import com.ciccc.cryptocurrency.model.Ticker;

public abstract class ExchangeService {

    protected ClientRestService clientRest;

    public abstract  Ticker getPrice24hr(Currency currency) throws Exception ;
}
