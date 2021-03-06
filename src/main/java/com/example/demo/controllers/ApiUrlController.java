package com.example.demo.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.xml.sax.SAXException;

import com.example.demo.beans.ApiUrlInput;
import com.example.demo.utili.Conversion;

@Controller
public class ApiUrlController {

    @Autowired
    ServletContext context;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String apiurlinput(ApiUrlInput apiUrlInput, Model model, HttpServletRequest request, HttpServletResponse response) {

        // System.out.println(apiUrlInput.getApiUrlString());
        Conversion c = new Conversion();

        request.getSession().setAttribute("error", "");

        try {
            c.returnCSV(apiUrlInput.getApiUrlString());

            String fileName = "LCL-CSV.csv";

            System.out.println("Downloading file :- " + fileName);

            // String downloadFolder = context.getRealPath("src/main/resources/static/csv/");
            String downloadFolder = "src/main/resources/static/csv/";
            Path file = Paths.get(downloadFolder, fileName);
            // Check if file exists
            if (Files.exists(file)) {
                // set content type
                response.setContentType("text/csv");
                // add response header
                response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
                try {
                    // copies all bytes from a file to an output stream
                    Files.copy(file, response.getOutputStream());
                    // flushes output stream
                    response.getOutputStream().flush();
                } catch (IOException e) {
                    System.out.println("Error :- " + e.getMessage());
                }
            } else {
                System.out.println("Sorry File not found!!!!");
            }

        } catch (JSONException | ParseException | IOException | SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            String error = e.toString();
            // model.addAttribute("error", error); // DOESNT WORK BECAUSE REDIRECT WILL REFRESH IT SO MODEL ATTRIBUTES ARE GONE?
            request.getSession().setAttribute("error", error);
            return "redirect:/";
            // return "index";
        }

        return null;
    }

}
