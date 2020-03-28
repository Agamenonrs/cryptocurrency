package com.ciccc.cryptocurrency.service;/*
@author Agamenon
*/

import com.ciccc.cryptocurrency.exception.MercadoBitcoinException;
import com.ciccc.cryptocurrency.model.Currency;
import com.ciccc.cryptocurrency.model.Ticker;
import com.ciccc.cryptocurrency.model.MercadoBitcoinTicker;
import com.ciccc.cryptocurrency.util.json.JsonObjectIntegration;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;

@Service
public class MercadoBitcoinService extends ExchangeService {

    private static final String DOMAIN = "https://www.mercadobitcoin.net";
    private static final String API_PATH = "/api/";

    public MercadoBitcoinService() throws MercadoBitcoinException {
        clientRest = new ClientRestService();
    }

    public String getApiPath() {
        return API_PATH;
    }

    @Override
    public Ticker getPrice24hr(Currency currency) throws Exception {
        String url = assemblyUrl(currency, "ticker");
        JsonObjectIntegration jsonObject = JsonObjectIntegration.readFrom(invokeApiMethod(url));
        JsonObjectIntegration ticketJsonObject = jsonObject.get("ticker").asObject();
        return new MercadoBitcoinTicker(ticketJsonObject, currency);
    }

    private String assemblyUrl(Currency currency, String method, String ... pathParams) throws MercadoBitcoinException {
        if (currency == null || currency.getCode() == null) {
            throw new MercadoBitcoinException("Invalid coin pair.");
        }

        StringBuffer url = new StringBuffer(currency.getCode());
        url.append("/");
        url.append(method);
        url.append("/");

        for (String pathParam : pathParams) {
            url.append(pathParam);
        }

        return url.toString();
    }

    private String invokeApiMethod(String path) throws MercadoBitcoinException {
        try {
            String url = clientRest.generateApiUrlV2(getDomain(), path);
            return clientRest.getResponseFromServerV2(url);
        } catch (MalformedURLException e) {
            throw new MercadoBitcoinException("Internal error: Invalid URL.");
        } catch (IOException e) {
            e.printStackTrace();
            throw new MercadoBitcoinException("Internal error: Failure in connection.");
        }
    }

    protected String getDomain() {
        return DOMAIN + getApiPath();
    }

}
