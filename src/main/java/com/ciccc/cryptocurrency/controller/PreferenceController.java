package com.ciccc.cryptocurrency.controller;
/*
@author Agamenon
*/

import com.ciccc.cryptocurrency.model.UserConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PreferenceController {

    @RequestMapping(value="/config", method= RequestMethod.POST)
    public String config(@ModelAttribute UserConfiguration preferences){
        System.out.println(preferences.getValue());
        return "redirect:/";
    }
}
