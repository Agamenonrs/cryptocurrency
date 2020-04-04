package com.ciccc.cryptocurrency;/*
@author Agamenon
*/

import com.ciccc.cryptocurrency.controller.CurrencyController;
import com.ciccc.cryptocurrency.enums.ExchangeCode;
import com.ciccc.cryptocurrency.model.*;
import com.ciccc.cryptocurrency.model.Currency;
import com.ciccc.cryptocurrency.service.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CurrencyController.class, MercadoBitcoinService.class,
        BinanceService.class, ZBService.class, OkexService.class, DollarQuotationService.class})
//@WebMvcTest(CurrencyController.class)
public class CryptoCurrencyTest {

    @Autowired
    CurrencyController currencyController;

    public static List<BigDecimal[]> getPrices() {
        /* BigDecimal high BigDecimal low, BigDecimal vol,
                BigDecimal last, BigDecimal buy, BigDecimal sell,
                Long date, Currency currency,
                BigDecimal dollarPrice*/
        return Arrays.asList(new BigDecimal[][] {
                {BigDecimal.ZERO,new BigDecimal("1.0"),new BigDecimal("1.1")},
                {BigDecimal.ZERO,new BigDecimal("1.1"),new BigDecimal("1.2")},
                {BigDecimal.ZERO,new BigDecimal("1.0"),new BigDecimal("1.4")},
                {BigDecimal.ZERO,new BigDecimal("1.2"),new BigDecimal("1.3")},

        });
    }


    @Test
    public void calculateSpread(){
        Currency currency = new Currency();
        currency.setCode("XRP");
        Long dateTime = 1586012550L;

       /* BigDecimal high BigDecimal low, BigDecimal vol,
                BigDecimal last, BigDecimal buy, BigDecimal sell,
                Long date, Currency currency,
                BigDecimal dollarPrice*/
        List prices = getPrices();
        BigDecimal[] mbcValues = (BigDecimal[]) prices.get(0);
        BigDecimal[] binValues = (BigDecimal[]) prices.get(1);
        BigDecimal[] zbValues = (BigDecimal[]) prices.get(2);
        BigDecimal[] okexValues = (BigDecimal[]) prices.get(3);
        Ticker mbtcTicker = new MercadoBitcoinTicker(mbcValues[0],mbcValues[0],mbcValues[0],mbcValues[0],mbcValues[1],mbcValues[2],dateTime,currency,mbcValues[0]);
        Ticker binTicker = new BinanceTicker(binValues[0],binValues[0],binValues[0],binValues[0],binValues[1],binValues[2],dateTime,currency);
        Ticker zbTicker = new ZBTicker(zbValues[0],zbValues[0],zbValues[0],zbValues[0],zbValues[1],zbValues[2],dateTime,currency);
        Ticker okexTicker = new OkexTicker(okexValues[0],okexValues[0],okexValues[0],okexValues[0],okexValues[1],okexValues[2],dateTime,currency);

        List<Ticker> tickerList = new ArrayList<>();
        tickerList.add(mbtcTicker);
        tickerList.add(binTicker);
        tickerList.add(zbTicker);
        tickerList.add(okexTicker);
        List<Opportunity> opportunities = new ArrayList<>();

        currencyController.addOportunities(null,opportunities,currency,tickerList);
        Assert.assertEquals(new BigDecimal("1.0"),opportunities.get(0).getBuy().getBuy());
        Assert.assertEquals(new BigDecimal("1.4"),opportunities.get(0).getSell().getSell());
        Assert.assertEquals(ExchangeCode.MBTC,opportunities.get(0).getBuy().getExchange());
        Assert.assertEquals(ExchangeCode.ZB,opportunities.get(0).getSell().getExchange());

    }
}
