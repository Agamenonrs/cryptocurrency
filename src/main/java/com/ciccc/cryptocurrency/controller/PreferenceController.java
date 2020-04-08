package com.ciccc.cryptocurrency.controller;
/*
@author Agamenon
*/

import com.ciccc.cryptocurrency.enums.CoinCode;
import com.ciccc.cryptocurrency.enums.ExchangeCode;
import com.ciccc.cryptocurrency.model.UserConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;


@Controller
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class PreferenceController {

    @Autowired
    UserConfiguration UCSession;


    @RequestMapping("/preferences")
    public String preperences(Model model) {

        model.addAttribute("exchanges", ExchangeCode.values());
        model.addAttribute("coins", CoinCode.values());
        model.addAttribute("userConfiguration",UCSession);
        return "preferences";
    }

    @RequestMapping(value="/config", method= RequestMethod.POST)
    public String config(@ModelAttribute UserConfiguration preferences){
        System.out.println(preferences.getValue());
        UCSession.setValue(preferences.getValue());
        UCSession.setSpreadMin(preferences.getSpreadMin());
        UCSession.setCurrencies(preferences.getCurrencies());
        UCSession.setExchanges(preferences.getExchanges());
        return "redirect:/";
    }

    @RequestMapping(value="/clearPreferences", method= RequestMethod.GET)
    public String config(){
        UCSession.clear();
        return "redirect:currencyprices";
    }
}
