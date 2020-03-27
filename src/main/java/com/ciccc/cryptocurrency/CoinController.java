package com.ciccc.cryptocurrency;

import com.ciccc.cryptocurrency.model.Currency;
import com.ciccc.cryptocurrency.model.Ticker;
import com.ciccc.cryptocurrency.service.BinanceService;
import com.ciccc.cryptocurrency.service.MercadoBitcoinService;
import com.ciccc.cryptocurrency.service.ZBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CoinController {

	@Autowired
	private MercadoBitcoinService mercadoBitcoinApi;

	@Autowired
	BinanceService binanceApi;

	@Autowired
	ZBService zbApi;
	
	@RequestMapping("/")
	public String index(){
		return "index";
	}

	@RequestMapping(value="/ticker24h", method= RequestMethod.GET)
	public ResponseEntity<List<Ticker>> find() {
		List<Ticker> tickers = new ArrayList<>();

		try {
			Currency currency = new Currency();
			currency.setCode("XRP");
			Ticker mbc = mercadoBitcoinApi.getPrice24hr(currency);
			Ticker bin = binanceApi.getPrice24hr(currency);
			Ticker zb = zbApi.getPrice24hr(currency);
			tickers.add(mbc);
			tickers.add(bin);
			tickers.add(zb);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok().body(tickers);
	}
}
