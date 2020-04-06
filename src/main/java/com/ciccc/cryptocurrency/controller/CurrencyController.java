package com.ciccc.cryptocurrency.controller;

import com.ciccc.cryptocurrency.enums.CoinCode;
import com.ciccc.cryptocurrency.enums.ExchangeCode;
import com.ciccc.cryptocurrency.model.*;
import com.ciccc.cryptocurrency.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class CurrencyController {

    @Autowired
    private MercadoBitcoinService mercadoBitcoinService;

    @Autowired
    private BinanceService binanceService;

    @Autowired
    private ZBService zbService;

    @Autowired
    private OkexService okexService;

    @Autowired
    private DollarQuotationService dollarQuotationService;

    private List<Ticker> tickerList;

    @RequestMapping("/")
    public String index(HttpSession session) {
        UserConfiguration userConfiguration = (UserConfiguration) session.getAttribute("userConfiguration");
        if (userConfiguration != null) {
            System.out.println("INDEX USERCONFIGURATION " + userConfiguration.getValue());
        } else {
            System.out.println("USER CONFIGURATION NULL");
        }

        return "index";
    }

    @RequestMapping(value = "/ticker24h", method = RequestMethod.GET)
    public ResponseEntity<List<Ticker>> find() {
        tickerList = new ArrayList<>();
        try {
            Currency currency = new Currency();
            currency.setCode("XRP");
            Ticker mbc = mercadoBitcoinService.getPrice24hr(currency);
            Ticker bin = binanceService.getPrice24hr(currency);
            Ticker zb = zbService.getPrice24hr(currency);
            Ticker okex = okexService.getPrice24hr(currency);
            tickerList.add(mbc);
            tickerList.add(bin);
            tickerList.add(zb);
            tickerList.add(okex);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(tickerList);
    }

    @RequestMapping(value = "/dollarQuotation", method = RequestMethod.GET)
    public ResponseEntity<BigDecimal> dollarQuotation() {
        BigDecimal value = BigDecimal.ZERO;
        try {
            value = dollarQuotationService.getBuyQuotation();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(value);
    }

    @RequestMapping(value = "/currencyprices", method = RequestMethod.GET)
    public String listaCurrencyPrices(Model model, HttpSession session) {
        List<Ticker> tickers = new ArrayList<>();
        List<Opportunity> opportunities = new ArrayList<>();
        System.out.println("========== loading page ============");
        UserConfiguration userConfiguration = (UserConfiguration) session.getAttribute("userConfiguration");
        if (userConfiguration != null) {
            System.out.println("CURRENCY PRICES USERCONFIGURATION " + userConfiguration.getValue());
        } else {
            userConfiguration = new UserConfiguration();
            System.out.println("USER CONFIGURATION NULL");
        }
        try {
            List<Currency> currencies = new ArrayList<>();
            List<ExchangeCode> exchangeCodes = null;
            if (userConfiguration.getCurrencies().size() > 0) {
                currencies = userConfiguration.getCurrencies();
                if(userConfiguration.getExchanges() != null &&
                        userConfiguration.getExchanges().size() > 0)
                exchangeCodes = userConfiguration.getExchanges();
            } else {
                for (CoinCode code : CoinCode.values()) {
                    Currency c = new Currency();
                    c.setCode(code.getCode());
                    currencies.add(c);
                }
                exchangeCodes = Arrays.asList(ExchangeCode.values()) ;

            }
            for (Currency currency : currencies) {
                if (currency.getCode().equals(CoinCode.DOLAR_USDT.getCode())
                        || currency.getCode().equals(CoinCode.REAL_BRL.getCode()))
                    continue;

                if(exchangeCodes.contains(ExchangeCode.MBTC)){
                    addTicker(mercadoBitcoinService,tickers,currency);
                }
                if(exchangeCodes.contains(ExchangeCode.BINC)){
                    addTicker(binanceService,tickers,currency);
                }
                if(exchangeCodes.contains(ExchangeCode.ZB)){
                    addTicker(zbService,tickers,currency);
                }
                if(exchangeCodes.contains(ExchangeCode.OKEX)){
                    addTicker(okexService,tickers,currency);
                }

                //Just for test
                //loadTickerTest(tickers);

                List<Ticker> tempTicker = tickers.stream()
                        .filter(c -> c.getCurrency().equals(currency))
                        .collect(Collectors.toList());


                addOportunities(userConfiguration,opportunities, currency, tempTicker);
            }


            model.addAttribute("tickers", tickers);
            model.addAttribute("opportunities", opportunities);
            model.addAttribute("userConfiguration", userConfiguration);

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("tickers", tickers);
            model.addAttribute("opportunities", opportunities);
            model.addAttribute("userConfiguration", userConfiguration);
        }
        return "currencylist";
    }

    public void addOportunities(UserConfiguration userConfiguration, List<Opportunity> opportunities, Currency currency, List<Ticker> tempTicker) {
        if(tempTicker == null || tempTicker.isEmpty())
            return;

        Ticker minBuy = tempTicker.get(0);
        Ticker maxSell = tempTicker.get(0);
        for (int i = 1; i < tempTicker.size(); i++) {
            Ticker tickerAux = tempTicker.get(i);
            if (!tickerAux.getExchange().equals(minBuy.getExchange())) {
                if (minBuy.getBuy().compareTo(tickerAux.getBuy()) > 0) {
                    minBuy = tickerAux;
                }
                if (maxSell.getSell().compareTo(tickerAux.getSell()) < 0) {
                    maxSell = tickerAux;
                }
            }
        }
        Opportunity o = new Opportunity(userConfiguration, currency, minBuy, maxSell);
        if(userConfiguration.getSpreadMin() != null
                && userConfiguration.getSpreadMin().doubleValue() > 0 ){
            if(o.getSpreadPercentage().compareTo(userConfiguration.getSpreadMin()) >=0){
                opportunities.add(o);
            }
        }else{
            opportunities.add(o);
        }

    }

    private void addTicker(ExchangeService service, List<Ticker> tickers, Currency currency){
        try {
            Ticker zb = service.getPrice24hr(currency);
            tickers.add(zb);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    private void  loadTickerTest(List<Ticker> tickers){
        Currency currency = new Currency();
        currency.setCode("XRP");
        Long dateTime = 1586012550L;
        List<BigDecimal[]> prices = Arrays.asList(new BigDecimal[][] {
                {BigDecimal.ZERO,new BigDecimal("1.0"),new BigDecimal("1.1")},
                {BigDecimal.ZERO,new BigDecimal("1.1"),new BigDecimal("1.2")},
                {BigDecimal.ZERO,new BigDecimal("1.0"),new BigDecimal("1.4")},
                {BigDecimal.ZERO,new BigDecimal("1.2"),new BigDecimal("1.3")},

        });
        BigDecimal[] mbcValues = (BigDecimal[]) prices.get(0);
        BigDecimal[] binValues = (BigDecimal[]) prices.get(1);
        BigDecimal[] zbValues = (BigDecimal[]) prices.get(2);
        BigDecimal[] okexValues = (BigDecimal[]) prices.get(3);
        Ticker mbtcTicker = new MercadoBitcoinTicker(mbcValues[0],mbcValues[0],mbcValues[0],mbcValues[0],mbcValues[1],mbcValues[2],dateTime,currency,mbcValues[0]);
        Ticker binTicker = new BinanceTicker(binValues[0],binValues[0],binValues[0],binValues[0],binValues[1],binValues[2],dateTime,currency);
        Ticker zbTicker = new ZBTicker(zbValues[0],zbValues[0],zbValues[0],zbValues[0],zbValues[1],zbValues[2],dateTime,currency);
        Ticker okexTicker = new OkexTicker(okexValues[0],okexValues[0],okexValues[0],okexValues[0],okexValues[1],okexValues[2],dateTime,currency);

        tickers.add(mbtcTicker);
        tickers.add(binTicker);
        tickers.add(zbTicker);
        tickers.add(okexTicker);
    }
}
