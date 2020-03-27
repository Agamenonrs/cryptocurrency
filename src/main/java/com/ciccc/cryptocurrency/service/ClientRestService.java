package com.ciccc.cryptocurrency.service;/*
@author Agamenon
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import com.ciccc.cryptocurrency.enums.HttpMethods;
import org.springframework.web.client.RestTemplate;

public class ClientRestService {

    public URL generateApiUrl(String dominio,String params) throws MalformedURLException {
        URL url = new URL(dominio + params);
        return url;
    }

    public String generateApiUrlV2(String dominio,String params) throws MalformedURLException {
        return dominio + params;
    }
    public HttpsURLConnection getHttpsGetConnection(URL url) throws IOException {
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod(HttpMethods.GET.name());
        conn.setRequestProperty("Content-Type", "application/json");

        return conn;
    }

    public HttpURLConnection getHttpGetConnection(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(HttpMethods.GET.name());
        conn.setRequestProperty("Content-Type", "application/json");

        return conn;
    }

    public void getRequestToServer(HttpsURLConnection conn) throws IOException {
        conn.getResponseCode();
    }

    public void getRequestToServer(HttpURLConnection conn) throws IOException {
        conn.getResponseCode();
    }

    public String getResponseFromServerV2(String url) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, //
                String.class);
        return response;
    }

    public String getResponseFromServer(HttpsURLConnection conn) throws IOException {
        String responseStr = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } catch (IOException e) {
            if (conn.getErrorStream() != null) {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
        }
        StringBuilder sb = new StringBuilder();
        String line = null;

        if (reader != null) {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        }

        responseStr = sb.toString();
        return responseStr;
    }
    public String getResponseFromServer(HttpURLConnection conn) throws IOException {
        String responseStr = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } catch (IOException e) {
            if (conn.getErrorStream() != null) {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
        }
        StringBuilder sb = new StringBuilder();
        String line = null;

        if (reader != null) {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        }

        responseStr = sb.toString();
        return responseStr;
    }
}
