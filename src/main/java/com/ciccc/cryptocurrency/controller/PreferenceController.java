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


@Controller
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class PreferenceController {


    @RequestMapping("/preferences")
    public String preperences(Model model, HttpSession session) {
        UserConfiguration userConfiguration = (UserConfiguration) session.getAttribute("userConfiguration");

        model.addAttribute("exchanges", ExchangeCode.values());
        model.addAttribute("coins", CoinCode.values());
        model.addAttribute("userConfiguration",
                userConfiguration != null ? userConfiguration : new UserConfiguration());
        return "preferences";
    }

    @RequestMapping(value="/config", method= RequestMethod.POST)
    public String config(@ModelAttribute UserConfiguration preferences, HttpSession session){
        System.out.println(preferences.getValue());
        session.setAttribute("userConfiguration", preferences);
        return "redirect:/";
    }
}
