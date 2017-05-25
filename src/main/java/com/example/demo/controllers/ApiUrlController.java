package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.beans.ApiUrlInput;

@Controller
public class ApiUrlController {

    @RequestMapping(value = "/apiurlinput", method = RequestMethod.POST)
    public String apiurlinput(ApiUrlInput apiUrlInput, Model model) {

        System.out.println(apiUrlInput.getApiUrlString());

        return "redirect:/";
    }

}
