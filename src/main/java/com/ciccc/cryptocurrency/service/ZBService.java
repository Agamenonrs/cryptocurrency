package com.ciccc.cryptocurrency.service;/*
@author Agamenon
*/

import com.ciccc.cryptocurrency.enums.CoinCode;
import com.ciccc.cryptocurrency.exception.MercadoBitcoinException;
import com.ciccc.cryptocurrency.exception.ZBException;
import com.ciccc.cryptocurrency.model.Currency;
import com.ciccc.cryptocurrency.model.Ticker;
import com.ciccc.cryptocurrency.model.ZBTicker;
import com.ciccc.cryptocurrency.util.json.JsonObjectIntegration;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;

@Service
public class ZBService extends ExchangeService {

    private static final String DOMAIN = "http://api.zb.com";
    private static final String API_PATH_TICKER24H = "/data/v1/ticker?market=";

    public ZBService() {
        super();
        clientRest = new ClientRestService();
    }

    @Override
    public Ticker getPrice24hr(Currency currency) throws ZBException {
        if (currency == null || currency.getCode() == null) {
            throw new ZBException("Invalid code.");
        }
        String pairCode = currency.getCode() + "_" + CoinCode.DOLAR_USDT.getCodigo();
        try {
            JsonObjectIntegration jsonObject = JsonObjectIntegration.readFrom(invokeApiMethod(pairCode));
            return new ZBTicker(jsonObject, currency);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String invokeApiMethod(String path) throws Exception {
        try {
            String url = clientRest.generateApiUrlV2(getDomain(), path);
            return clientRest.getResponseFromServerV2(url);
        } catch (MalformedURLException e) {
            throw new ZBException("Internal error: Invalid URL.");
        } catch (IOException e) {
            e.printStackTrace();
            throw new ZBException("Internal error: Failure in connection.");
        }
    }

    protected String getDomain() {
        return DOMAIN + getApiPath();
    }

    protected String getDomain(String param) {
        return DOMAIN + param + getApiPath();
    }

    public String getApiPath() {
        return API_PATH_TICKER24H;
    }
}
