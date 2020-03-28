package com.ciccc.cryptocurrency.service;/*
@author Agamenon
*/

import com.ciccc.cryptocurrency.enums.CoinCode;
import com.ciccc.cryptocurrency.exception.BinanceException;
import com.ciccc.cryptocurrency.model.Currency;
import com.ciccc.cryptocurrency.model.Ticker;
import com.ciccc.cryptocurrency.model.BinanceTicker;
import com.ciccc.cryptocurrency.util.json.JsonObjectIntegration;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

@Service
public class BinanceService extends ExchangeService {

    private static final String DOMAIN = "https://api.binance.com";
    private static final String API_PATH_TICKER24H = "/api/v1/ticker/24hr";

    public BinanceService() {
        super();
        clientRest = new ClientRestService();
    }

    @Override
    public Ticker getPrice24hr(Currency currency) throws BinanceException{
        if (currency == null || currency.getCode() == null) {
            throw new BinanceException("Invalid currency code.");
        }
        Map<String,String> params = new HashMap<String,String>();
        params.put("symbol", currency.getCode()+ CoinCode.DOLAR_USDT.getCodigo());
        String url = assemblyUrl(params);
        JsonObjectIntegration jsonObject = JsonObjectIntegration.readFrom(invokeApiMethodV2(url));
        JsonObjectIntegration ticketJsonObject = jsonObject.asObject();
        return new BinanceTicker(jsonObject,currency);

    }

    private String assemblyUrl(Map<String, String> params)
            throws BinanceException {
        StringBuffer url = new StringBuffer("");
        if(!params.isEmpty()){
            url.append("?");
        }
        int i = 0;
        for(String key : params.keySet()) {
            if(i>0) {
                url.append("&");
            }
            url.append(key);
            url.append("=");
            url.append(params.get(key));
        }

        return url.toString();
    }

    private String invokeApiMethodV2(String params) throws BinanceException {
        try {
            String url = clientRest.generateApiUrlV2(getDomain(),params);
            return clientRest.getResponseFromServerV2(url);
        } catch (MalformedURLException e) {
            throw new BinanceException("Internal error: Invalid URL.");
        } catch (IOException e) {
            e.printStackTrace();
            throw new BinanceException("Internal error: Failure in connection.");
        }
    }

    protected String getDomain() {
        return DOMAIN + getApiPath();
    }

    public String getApiPath() {
        return API_PATH_TICKER24H;
    }
}
