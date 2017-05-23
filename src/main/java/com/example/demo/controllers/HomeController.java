package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.beans.ApiUrlInput;

@Controller
public class HomeController {
	
	@RequestMapping(value = "/")
	public String index(Model model){
		
		ApiUrlInput apiUrlInput = new ApiUrlInput(); 
		
		model.addAttribute("apiUrlInput", apiUrlInput);
		
		return "index";
	}

}
