package com.ciccc.cryptocurrency.controller;
/*
@author Agamenon
*/

import com.ciccc.cryptocurrency.model.UserConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpSession;


@Controller
@Scope(value = WebApplicationContext.SCOPE_REQUEST)
public class PreferenceController {

    @Autowired
    UserConfiguration userConfiguration;

    @RequestMapping(value="/config", method= RequestMethod.POST)
    public String config(@ModelAttribute UserConfiguration preferences, HttpSession session){
        System.out.println(preferences.getValue());
        userConfiguration = preferences;
        session.setAttribute("userConfiguration", userConfiguration);
        return "redirect:/";
    }
}
