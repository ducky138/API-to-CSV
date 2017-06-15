package com.example.demo.controllers;

import java.io.IOException;
import java.text.ParseException;

import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.xml.sax.SAXException;

import com.example.demo.beans.ApiUrlInput;
import com.example.demo.utili.Conversion;

@Controller
public class ApiUrlController {

    @RequestMapping(value = "/apiurlinput", method = RequestMethod.POST)
    public String apiurlinput(ApiUrlInput apiUrlInput, Model model) {

        // System.out.println(apiUrlInput.getApiUrlString());
        Conversion c = new Conversion();
        try {
            c.returnCSV(apiUrlInput.getApiUrlString());
            System.out.println("got here");

        } catch (JSONException | ParseException | IOException | SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "redirect:/";
    }

}
