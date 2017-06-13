package com.example.demo.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import com.example.demo.utili.Conversion;
import com.example.demo.utili.Utilis;

public class Test {

    public static void main(String[] args) throws MalformedURLException, SAXException, IOException, JSONException {

        Conversion c = new Conversion();
        Utilis u = new Utilis();

        InputStream in = c.retrieveLCLApitoInputStream("https://www.pcplus.ca/rest/loyalty/v6/store/5000008");
        JSONObject obj = c.retrieveStoreJSONObject(in);

        System.out.println(obj);

        System.out.println(obj.get("storeNumber"));
        System.out.println(c.convertStoreJSONToCSV(obj));

        // System.out.println("hi");

    }

}
