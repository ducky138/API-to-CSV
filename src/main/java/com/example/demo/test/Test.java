package com.example.demo.test;

import java.io.IOException;
import java.net.MalformedURLException;

import org.xml.sax.SAXException;

import com.example.demo.utils.Conversion;

public class Test {

	public static void main(String[] args) throws MalformedURLException, SAXException, IOException {
		
		Conversion c = new Conversion();
//		c.setCredentials();
		c.retrieveApitoXML("https://www.pcplus.ca/rest/loyalty/v6/store/5000008");

	}

}
