package com.ciccc.cryptocurrency;

import com.ciccc.cryptocurrency.model.Currency;
import com.ciccc.cryptocurrency.model.Ticker;
import com.ciccc.cryptocurrency.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
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
	public String index(){
		return "index";
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
	public String listaCurrencyPrices(Model model) {
		List<Ticker> tickers = new ArrayList<>();

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

			Ticker minBuy = tickers.get(0);
			Ticker maxSell = tickers.get(0);
			for(int i = 1 ; i < tickers.size(); i++){
				Ticker tickerAux = tickers.get(i);
				if(!tickerAux.getExchange().equals(minBuy.getExchange())){
					if (minBuy.getBuy().compareTo(tickerAux.getBuy()) > 0){
						minBuy = tickerAux;
					}
					if(maxSell.getSell().compareTo(tickerAux.getSell()) < 0){
						maxSell = tickerAux;
					}
				}
			}

			model.addAttribute("tickers", tickers);
			model.addAttribute("minBuy", minBuy);
			model.addAttribute("maxSell", maxSell);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "currencylist";
	}
}
