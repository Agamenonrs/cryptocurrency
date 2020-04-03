package com.ciccc.cryptocurrency.controller;

import com.ciccc.cryptocurrency.enums.CoinCode;
import com.ciccc.cryptocurrency.enums.ExchangeCode;
import com.ciccc.cryptocurrency.model.Currency;
import com.ciccc.cryptocurrency.model.Opportunity;
import com.ciccc.cryptocurrency.model.Ticker;
import com.ciccc.cryptocurrency.model.UserConfiguration;
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
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class CoinController {

	@Autowired
	private MercadoBitcoinService mercadoBitcoinApi;

	@Autowired
	private BinanceService binanceApi;

	@Autowired
	private ZBService zbApi;

	@Autowired
	private OkexService okexService;

	@Autowired
	private DollarQuotationService dollarQuotationService;

	private List<Ticker> tickers;
	
	@RequestMapping("/")
	public String index(HttpSession session){
		UserConfiguration userConfiguration = (UserConfiguration) session.getAttribute("userConfiguration");
		if(userConfiguration != null){
			System.out.println("INDEX USERCONFIGURATION " + userConfiguration.getValue());
		}else{
			System.out.println("USER CONFIGURATION NULL");
		}

		return "index";
	}

	@RequestMapping("/preferences")
	public String preperences(Model model){
		model.addAttribute("exchanges", ExchangeCode.values());
		model.addAttribute("coins", CoinCode.values());
		model.addAttribute("userConfiguration", new UserConfiguration());
		return "preferences";
	}

	@RequestMapping(value="/ticker24h", method= RequestMethod.GET)
	public ResponseEntity<List<Ticker>> find() {
		tickers = new ArrayList<>();
		try {
			Currency currency = new Currency();
			currency.setCode("XRP");
			Ticker mbc = mercadoBitcoinApi.getPrice24hr(currency);
			Ticker bin = binanceApi.getPrice24hr(currency);
			Ticker zb = zbApi.getPrice24hr(currency);
			Ticker okex = okexService.getPrice24hr(currency);
			tickers.add(mbc);
			tickers.add(bin);
			tickers.add(zb);
			tickers.add(okex);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok().body(tickers);
	}

	@RequestMapping(value="/dollarQuotation", method= RequestMethod.GET)
	public ResponseEntity<BigDecimal> dollarQuotation() {
		BigDecimal value= BigDecimal.ZERO;
		try {
		     value = dollarQuotationService.getBuyQuotation();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok().body(value);
	}

	@RequestMapping(value="/currencyprices", method= RequestMethod.GET)
	public String listaCurrencyPrices(Model model,HttpSession session) {
		List<Ticker> tickers = new ArrayList<>();
		List<Opportunity> opportunities = new ArrayList<>();
		System.out.println("========== loading page ============");
		UserConfiguration userConfiguration = (UserConfiguration) session.getAttribute("userConfiguration");
		if(userConfiguration != null){
			System.out.println("CURRENCY PRICES USERCONFIGURATION " + userConfiguration.getValue());
		}else{
			System.out.println("USER CONFIGURATION NULL");
		}
		try {
			for (CoinCode code : CoinCode.values()){
				if(code.equals(CoinCode.DOLAR_USDT) || code.equals(CoinCode.REAL_BRL))
					continue;


				Currency currency = new Currency();
				currency.setCode(code.getCode());
				Ticker mbc = mercadoBitcoinApi.getPrice24hr(currency);
				Ticker bin = binanceApi.getPrice24hr(currency);
				Ticker zb = zbApi.getPrice24hr(currency);
				Ticker okex = okexService.getPrice24hr(currency);
				tickers.add(mbc);
				tickers.add(bin);
				tickers.add(zb);
				tickers.add(okex);

				List<Ticker> tempTicker = tickers.stream()
						.filter(c-> c.getCurrency().equals(currency))
						.collect(Collectors.toList());


				Ticker minBuy = tempTicker.get(0);
				Ticker maxSell = tempTicker.get(0);
				for(int i = 1 ; i < tempTicker.size(); i++){
					Ticker tickerAux = tempTicker.get(i);
					if(!tickerAux.getExchange().equals(minBuy.getExchange())){
						if (minBuy.getBuy().compareTo(tickerAux.getBuy()) > 0){
							minBuy = tickerAux;
						}
						if(maxSell.getSell().compareTo(tickerAux.getSell()) < 0){
							maxSell = tickerAux;
						}
					}
				}
				Opportunity o = new Opportunity(currency,minBuy,maxSell);
				opportunities.add(o);
			}



			model.addAttribute("tickers", tickers);
			model.addAttribute("opportunities", opportunities);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "currencylist";
	}
}
