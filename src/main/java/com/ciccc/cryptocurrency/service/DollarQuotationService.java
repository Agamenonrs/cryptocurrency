package com.ciccc.cryptocurrency.service;/*
@author Agamenon
*/

import com.ciccc.cryptocurrency.util.json.JsonArray;
import com.ciccc.cryptocurrency.util.json.JsonObjectIntegration;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class DollarQuotationService {

    private static final String DOMAIN = "http://economia.awesomeapi.com.br";
    private static final String PATH_USD_BRL = "/USD-BRL/1";

    ClientRestService clientRest;
    private BigDecimal sell;
    private BigDecimal buy;

    public DollarQuotationService() {
        clientRest = new ClientRestService();
    }

    public BigDecimal getBuyQuotation(){
        URL url = null;
        String response = "";
        try {
            url = clientRest.generateApiUrl(getDomain(),"");
            HttpURLConnection conn = clientRest.getHttpGetConnection(url);
            clientRest.getRequestToServer(conn);
            response =  clientRest.getResponseFromServer(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonArray array = JsonArray.readFrom(response);
        JsonObjectIntegration jsonObject = array.get(0).asObject();

        BigDecimal value = new BigDecimal(jsonObject.get("bid").asString());
        return value;

    }

    public void getQuotation(){
        URL url = null;
        String response = "";
        try {
            url = clientRest.generateApiUrl(getDomain(),"");
            HttpURLConnection conn = clientRest.getHttpGetConnection(url);
            clientRest.getRequestToServer(conn);
            response =  clientRest.getResponseFromServer(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonArray array = JsonArray.readFrom(response);
        JsonObjectIntegration jsonObject = array.get(0).asObject();

        buy = new BigDecimal(jsonObject.get("bid").asString());
        sell = new BigDecimal(jsonObject.get("ask").asString());
    }



    public BigDecimal getSell() {
        return sell;
    }

    public BigDecimal getBuy() {
        return buy;
    }

    protected String getDomain() {
        return DOMAIN + PATH_USD_BRL;
    }
}
