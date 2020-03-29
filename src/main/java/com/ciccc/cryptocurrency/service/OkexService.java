package com.ciccc.cryptocurrency.service;/*
@author Agamenon
*/

import com.ciccc.cryptocurrency.enums.CoinCode;
import com.ciccc.cryptocurrency.model.Currency;
import com.ciccc.cryptocurrency.model.OkexTicker;
import com.ciccc.cryptocurrency.model.Ticker;
import com.ciccc.cryptocurrency.util.json.JsonObjectIntegration;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class OkexService extends ExchangeService {

    private static final String DOMAIN = "https://www.okex.com/";
    private static final String API_PATH_TICKER24H = "api/spot/v3/instruments/";

    public OkexService() {
        super();
        clientRest = new ClientRestService();
    }


    @Override
    public Ticker getPrice24hr(Currency currency) throws Exception {
        if (currency == null || currency.getCode() == null) {
            throw new Exception("Código da moeda invalido.");
        }
        String url = getDomain() + currency.getCode() + "-" + CoinCode.DOLAR_USDT.getCode() + "/ticker";
        JsonObjectIntegration jsonObject = JsonObjectIntegration.readFrom(invokeApiMethod(url));
        JsonObjectIntegration ticketJsonObject = jsonObject.asObject();
        OkexTicker ticker = new OkexTicker(ticketJsonObject,currency);
        return ticker;
    }

    private String invokeApiMethod(String params) throws Exception {
        try {
            URL url = clientRest.generateApiUrl("", params);
            HttpsURLConnection conn = clientRest.getHttpsGetConnection(url);
            clientRest.getRequestToServer(conn);
            return clientRest.getResponseFromServer(conn);
        } catch (MalformedURLException e) {
            throw new MalformedURLException();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
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
