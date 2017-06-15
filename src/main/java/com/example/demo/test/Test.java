package com.example.demo.test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;

import org.json.JSONException;
import org.xml.sax.SAXException;

import com.example.demo.utili.Conversion;

public class Test {

    public static void main(String[] args) throws MalformedURLException, SAXException, IOException, JSONException, ParseException {

        Conversion c = new Conversion();
        // Utilis u = new Utilis();
        //
        // String s = "7:00 AM - 11:00 PM";
        // String pattern = "(.*)-(.*)";
        // Pattern r = Pattern.compile(pattern);
        //
        // Matcher m = r.matcher(s);
        // if (m.find()) {
        // System.out.println("Found value:" + m.group(1).trim());
        // System.out.println("Found value:" + m.group(2).trim());
        // } else {
        // System.out.println("NO MATCH");
        // }

        // InputStream in = c.retrieveLCLApitoInputStream("https://www.pcplus.ca/rest/loyalty/v6/store/5000008");
        // JSONObject obj = c.retrieveStoreJSONObject(in);

        // System.out.println(obj.get("operatingHours"));

        // System.out.println(c.createOperatingHoursMap(obj.getJSONArray(("operatingHours"))));
        // System.out.println(c.createOperatingHoursMap(obj.getJSONArray(("operatingHours"))).get("Monday")[0]);

        // System.out.println(c.convertStoreJSONToCSV(obj));

        c.returnCSV("https://www.pcplus.ca/rest/loyalty/v6/store/5000008");

    }

}
