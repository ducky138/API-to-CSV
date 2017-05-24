package com.example.demo.utili;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import org.apache.tomcat.util.codec.binary.Base64;
import org.xml.sax.SAXException;

public class Conversion {

    Utilis u = new Utilis();

    private String userId;
    private String password;

    public void setCredentials() {

        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("src/main/resources/credentials.properties");

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            this.userId = prop.getProperty("userId");
            this.password = prop.getProperty("password");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public InputStream retrieveLCLApitoInputStream(String apiUrl)
            throws SAXException, MalformedURLException, IOException {

        setCredentials();
        URL url = new URL(apiUrl);
        URLConnection uc = url.openConnection();
        String userpass = userId + ":" + password;
        String basicAuth = "Basic " + new String(new Base64().encode(userpass.getBytes()));
        uc.setRequestProperty("Authorization", basicAuth);
        InputStream in = uc.getInputStream();

        // System.out.println(u.convertStreamToString(in));

        return in;

    }

}
