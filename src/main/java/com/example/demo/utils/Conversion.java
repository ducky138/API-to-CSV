package com.example.demo.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.Authenticator;
import java.net.ContentHandler;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.util.FileCopyUtils;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class Conversion {
	
	private String userId;
	private String password;
	
	public void setCredentials(){
		
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
	
	public void retrieveApitoXML(String apiUrl) throws SAXException, MalformedURLException, IOException{
		
		setCredentials();
		URL url = new URL(apiUrl);
		URLConnection uc = url.openConnection();
		String userpass = userId + ":" + password;
		String basicAuth = "Basic " + new String(new Base64().encode(userpass.getBytes()));
		uc.setRequestProperty ("Authorization", basicAuth);
		InputStream in = uc.getInputStream();
		
		System.out.println(convertStreamToString(in));
		
		
	
	}
	
	public String convertStreamToString(InputStream is) throws IOException { 
	    return new String(FileCopyUtils.copyToByteArray(is), StandardCharsets.UTF_8);
	}

}
