package com.example.demo.utili;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tomcat.util.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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

    public JSONObject retrieveStoreJSONObject(InputStream in) throws IOException, JSONException {
        JSONObject obj = u.convertInputStreamToJSON(in);
        return obj.getJSONObject("store");
    }

    public Matcher dateRegex(String s) {

        // String s = "7:00 AM - 11:00 PM";
        String pattern = "(.*)-(.*)";
        Pattern r = Pattern.compile(pattern);

        Matcher m = r.matcher(s);
        // if (m.find()) {
        // System.out.println("Found value:" + m.group(1).trim());
        // System.out.println("Found value:" + m.group(2).trim());
        // } else {
        // System.out.println("NO MATCH");
        // }
        return m;
    }

    public HashMap<String, String[]> createOperatingHoursMap(JSONArray arr) throws JSONException, ParseException {
        HashMap<String, String[]> map = new HashMap<String, String[]>();
        // map.put("Sunday", new String[2]);
        // map.put("Monday", new String[2]);
        // map.put("Tuesday", new String[2]);
        // map.put("Wednesday", new String[2]);
        // map.put("Thursday", new String[2]);
        // map.put("Friday", new String[2]);
        // map.put("Saturday", new String[2]);

        System.out.println(arr);

        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);

            // regex parse for the hours
            String pattern = "(.*)-(.*)";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher((String) obj.get("hours"));

            String[] hours = new String[2];
            if (m.find()) {
                // convert time to 24 hour
                SimpleDateFormat date12Format = new SimpleDateFormat("hh:mm a");
                SimpleDateFormat date24Format = new SimpleDateFormat("HH:mm");
                String openingTime = date24Format.format(date12Format.parse(m.group(1).trim()));
                String closingTime = date24Format.format(date12Format.parse(m.group(2).trim()));
                if (openingTime.substring(0, 1).equals("0")) {
                    openingTime = openingTime.substring(1);
                }
                hours[0] = openingTime;
                hours[1] = closingTime;
            }
            map.put((String) obj.get("day"), hours);
        }
        return map;
    }

    public StringBuilder convertStoreJSONToCSV(JSONObject obj) throws JSONException, ParseException {

        HashMap<String, String[]> map = createOperatingHoursMap(obj.getJSONArray("operatingHours"));

        StringBuilder sb = new StringBuilder(
                "ThirdPartyID,Active?,LocationName,URL,IsInternet,LocationType,ResponsivenessRating,Username_Email,Country,Address1,Address2,Address3,Address4,InternationalAddress,City,State_Province,Zip_PostalCode,ContactName,ContactPosition,ContactEmail,ContactPhone,ContactFax,ContactMobile,FacebookPageId,GeocodeSourceID,LastGeocodingAttempt,LeadManager,StoreLocator,TimeZoneID,MondayOpen,MondayClose,TuesdayOpen,TuesdayClose,WednesdayOpen,WednesdayClose,ThursdayOpen,ThursdayClose,FridayOpen,FridayClose,SaturdayOpen,SaturdayClose,SundayOpen,SundayClose,Latitude,Longitude,ImageURL,Store ID:att:1557,Location Name French:att:1558,Store Type:att:1559,pcPlus:att:1560,Bilingual:att:1561,Store Phone Number:att:1562,Store Fax Number:att:1563,Store Email:att:1564,Banner Id:att:1565,Banner Name:att:1566,Banner Logo URL:att:1567,Banner Logo URL French:att:1568,Pharmacy License Number:att:1569,Pharmacy Language:att:1570,Parking Available:att:1571,Food Available:att:1572,At Least Midnight:att:1573,Charity Name:att:1574,Contact Salutation:att:1575,Contact License Number:att:1576,Associate2 Name:att:1577,Associate2 Salutation:att:1578,Associate3 Name:att:1579,Associate3 Salutation:att:1580,Department Names:att:1581,Department Names French:att:1582,Services Names:att:1583,Services Names French:att:1584,Health Services Names:att:1585,Health Services Names French:att:1586,Brands Names:att:1587,Brands Names French:att:1588,Department-Pharmacy:att:1589,Department-Dietician:att:1590,DD-Practitioner Email:att:1591,DD-Practitioner Photo:att:1592,DD-Practitioner Profile:att:1593,DD-Practitioner Profile French:att:1594,DD-Medical Category:att:1595,DD-Medical Category French:att:1596,DD-Custom Department Name:att:1597,DD-Custom Department Name French:att:1598,DD-Phone Extension:att:1599,DD-Description:att:1600,DD-Phone Number:att:1601,DD-Fax Number:att:1602,DD-Description French:att:1603,DD-Department Id:att:1604,DD-Manager Name:att:1605,DD-Manager Photo:att:1606,DD-Department Name:att:1607,DD-Department Name French:att:1608,DDP-Pharmacy Name:att:1609,DDP-Pharmacy French Name:att:1610,DDP-Pharmacy Legal Name:att:1611,DDP-Pharmacy Legal French Name:att:1612,DDP-Pharmacy Business Owner:att:1613,DDP-Pharmacy Email:att:1614,DDP-Pharmacy Manager License:att:1615,DDP-Pharmacy College Logo:att:1616,DDP-Pharmacy College Url:att:1617,DDP-Pharmacy College Street:att:1618,DDP-Pharmacy College City:att:1619,DDP-Pharmacy College Province:att:1620,DDP-Pharmacy College Postal Code:att:1621,DDP-Pharmacy College Phone Number:att:1622,DDP-Pharmacy College Fax Number:att:1623,DDP-Pharmacy College License:att:1624,DDP-Pharmacy Accreditation Number:att:1625,DDP-Pharmacy College Name:att:1626,DDP-Pharmacy Delivery:att:1627,DDP-Pharmacy Delivery Charge:att:1628,DDP-Pharmacy Delivery Amount:att:1629,DDP-Pharmacy Delivery Hours:att:1630,Department-Garden Center:att:1631,Department-Optical:att:1632,Department-Medical Clinic:att:1633,Department-Alcohol:att:1659,placeholder1:att:1661,placeholder2:att:1662,placeholder3:att:1663,placeholder4:att:1664,placeholder5:att:1665,placeholder6:att:1666,placeholder7:att:1667,placeholder8:att:1668,placeholder9:att:1669,placeholder10:att:1670,placeholder11:att:1671,placeholder12:att:1672,placeholder13:att:1673,placeholder14:att:1674,placeholder15:att:1675,Brooklyn:att:1660,franchiseeBio:att:1887,franchiseeImage:att:1888,Banners-SDM:cat:87810,HOME HEALTH CARE:cat:87811,MURALE:cat:87812,PHARMAPRIX:cat:87813,SHOPPERS DRUG MART:cat:87814,SHOPPERS SIMPLY PHARMACY:cat:87815,Banners-LCL:cat:87816,Atlantic Superstore:cat:87817,AXEP:cat:87818,Bloor Street Market:cat:87819,Box:cat:87820,Club Entrepôt:cat:87821,Dominion:cat:87822,Extra Foods:cat:87823,Fortinos:cat:87824,Freshmart:cat:87825,Freshmart R&W:cat:87826,Independent City Market:cat:87827,Joe:cat:87828,L'Intermarche:cat:87829,L'Intermarche international:cat:87830,Loblaws:cat:87831,Loblaws Great Food:cat:87832,LUCKY DOLLAR FOODS:cat:87833,Maxi:cat:87834,Maxi & Cie:cat:87835,No Frills:cat:87836,Provigo:cat:87837,Real Canadian Liquor Store:cat:87838,Real Canadian Superstore:cat:87839,Real Canadian Wholesale Club:cat:87840,Save Easy:cat:87841,SHOP EASY FOODS:cat:87842,Superstore:cat:87843,SUPER VALU FOODS:cat:87844,Valu-mart:cat:87845,Wholesale Club:cat:87846,Your Independent Grocer:cat:87847,Zehrs:cat:87848,Zehrs Great Food:cat:87849,PC Plus:cat:87850,Grocery Services:cat:87855,Bakery:cat:87856,Beer and Wine:cat:87857,Canteen:cat:87858,Catering:cat:87859,Extended Services:cat:87860,Canada Post Office:cat:87861,Dry Cleaners:cat:87862,Parking:cat:87863,Dentist:cat:87864,Photo Lab:cat:87865,Pharmacy Services:cat:87866,Beauty Boutique:cat:87867,Medical Clinic:cat:87868,Health & Beauty:cat:87869,Joe Fresh Cosmetics:cat:87870,Brands:cat:87871,Anna Sui:cat:87872,Annabelle:cat:87873,Art Deco:cat:87874,Avène:cat:87875,Benefit Cosmetics:cat:87876,Bio Beauté:cat:87877,Biotherm:cat:87878,Bourjois:cat:87879,Chanel Cosmetics:cat:87880,Chanel Fragrance:cat:87881,Clarins:cat:87882,Clarisonic:cat:87883,Clinique:cat:87884,Cover FX:cat:87885,Cover Girl:cat:87886,D & G:cat:87887,Darphin:cat:87888,Dior:cat:87889,DKNY:cat:87890,Dr. Hauschka:cat:87891,DuWop:cat:87892,Elizabeth Arden:cat:87893,Essence:cat:87894,Estée Lauder:cat:87895,Etival:cat:87896,Fekkai:cat:87897,Footner:cat:87898,Fusion Beauty:cat:87899,Gosh:cat:87900,Guerlain:cat:87901,Joe Fresh:cat:87902,Korres:cat:87903,L’Occitane:cat:87904,La Roche-Posay:cat:87905,Lab Series:cat:87906,Lancôme:cat:87907,Lierac:cat:87908,Lise Watier:cat:87909,L'Oreal Paris:cat:87910,Marcelle:cat:87911,Maybelline:cat:87912,Murad:cat:87913,N°7:cat:87914,Nacara:cat:87915,NeoStrata:cat:87916,Nuxe:cat:87917,NYC New York Color:cat:87918,NYX:cat:87919,Origins:cat:87920,Orlane:cat:87921,Philosophy:cat:87922,Physicians Formula:cat:87923,Phyto:cat:87924,Prevage:cat:87925,Pur Minerals:cat:87926,Quo:cat:87927,RapidLash:cat:87928,Reversa:cat:87929,Revlon:cat:87930,Rimmel London:cat:87931,RoC:cat:87932,Rodial:cat:87933,Shiseido:cat:87934,Skin Solutions:cat:87935,Soap & Glory:cat:87936,St. Tropez:cat:87937,Stila:cat:87938,StriVectin:cat:87939,Urban Decay:cat:87940,Vasanti:cat:87941,Vichy:cat:87942,Yes To!:cat:87943,Yves Saint Laurent:cat:87944,Pharmacy:cat:88301,Pharmacy delivery:cat:88302,Dietician:cat:88303,Family Vision Center:cat:88304,Garden centre:cat:88305,Gas Bar:cat:88306,Gift cards:cat:88307,Joe Fresh Mens:cat:88308,Joe Fresh Womens:cat:88309,Liquor Store:cat:88310,PC Cooking School:cat:88311,PC Financial:cat:88312,PC Home:cat:88313,The Mobile Shop:cat:88314,The Wine Shop:cat:88315,Cheese wall:cat:88345,Dairy:cat:88346,Deli:cat:88347,Ethnic Foods:cat:88349,Frozen:cat:88350,Juice Bar:cat:88351,Meat:cat:88352,Organics:cat:88353,PC black label:cat:88354,Salad bar:cat:88355,Seafood:cat:88356,Sushi bar:cat:88357,Tea emporium:cat:88358,President's Choice:cat:89149\n");

        sb.append("LCL000" + obj.get("storeNumber") + ","); // ThirdPartyID
        sb.append(","); // Active?
        sb.append(obj.get("storeName") + ","); // LocationName
        sb.append(","); // URL
        sb.append(","); // IsInternet
        sb.append(","); // LocationType
        sb.append(","); // ResponsivenessRating
        sb.append(","); // Username_Email
        sb.append(((JSONObject) obj.get("address")).get("country") + ","); // Country
        sb.append(((JSONObject) obj.get("address")).get("addressLine1") + ","); // Address1
        sb.append(","); // Address2
        sb.append(","); // Address3
        sb.append(","); // Address4
        sb.append(","); // InternationalAddress
        sb.append(((JSONObject) obj.get("address")).get("city") + ","); // City
        sb.append(((JSONObject) obj.get("address")).get("province") + ","); // State_Province
        sb.append(((JSONObject) obj.get("address")).get("postalCode") + ","); // Zip_PostalCode
        sb.append(","); // ContactName
        sb.append(","); // ContactPosition
        sb.append(","); // ContactEmail
        sb.append(obj.get("phoneNumber") + ","); // ContactPhone
        sb.append(obj.get("faxNumber") + ","); // ContactFax
        sb.append(","); // ContactMobile
        sb.append(","); // FacebookPageId
        sb.append(","); // GeocodeSourceID
        sb.append(","); // LastGeocodingAttempt
        sb.append(","); // LeadManager
        sb.append(","); // StoreLocator
        sb.append(","); // TimeZoneID
        sb.append(map.get("Monday")[0] + ","); // MondayOpen
        sb.append(map.get("Monday")[1] + ","); // MondayClose
        sb.append(map.get("Tuesday")[0] + ","); // TuesdayOpen
        sb.append(map.get("Tuesday")[1] + ","); // TuesdayClose
        sb.append(map.get("Wednesday")[0] + ","); // WednesdayOpen
        sb.append(map.get("Wednesday")[1] + ","); // WednesdayClose
        sb.append(map.get("Thursday")[0] + ","); // ThursdayOpen
        sb.append(map.get("Thursday")[1] + ","); // ThursdayClose
        sb.append(map.get("Friday")[0] + ","); // FridayOpen
        sb.append(map.get("Friday")[1] + ","); // FridayClose
        sb.append(map.get("Saturday")[0] + ","); // SaturdayOpen
        sb.append(map.get("Saturday")[1] + ","); // SaturdayClose
        sb.append(map.get("Sunday")[0] + ","); // SundayOpen
        sb.append(map.get("Sunday")[1] + ","); // SundayClose
        sb.append(","); // Latitude
        sb.append(","); // Longitude
        sb.append(","); // ImageURL
        sb.append(","); // Store ID:att:1557
        sb.append(","); // Location Name French:att:1558
        sb.append(","); // Store Type:att:1559
        sb.append(","); // pcPlus:att:1560
        sb.append(","); // Bilingual:att:1561
        sb.append(","); // Store Phone Number:att:1562
        sb.append(","); // Store Fax Number:att:1563
        sb.append(","); // Store Email:att:1564
        sb.append(","); // Banner Id:att:1565
        sb.append(","); // Banner Name:att:1566
        sb.append(","); // Banner Logo URL:att:1567
        sb.append(","); // Banner Logo URL French:att:1568
        sb.append(","); // Pharmacy License Number:att:1569
        sb.append(","); // Pharmacy Language:att:1570
        sb.append(","); // Parking Available:att:1571
        sb.append(","); // Food Available:att:1572
        sb.append(","); // At Least Midnight:att:1573
        sb.append(","); // Charity Name:att:1574
        sb.append(","); // Contact Salutation:att:1575
        sb.append(","); // Contact License Number:att:1576
        sb.append(","); // Associate2 Name:att:1577
        sb.append(","); // Associate2 Salutation:att:1578
        sb.append(","); // Associate3 Name:att:1579
        sb.append(","); // Associate3 Salutation:att:1580
        sb.append(","); // Department Names:att:1581
        sb.append(","); // Department Names French:att:1582
        sb.append(","); // Services Names:att:1583
        sb.append(","); // Services Names French:att:1584
        sb.append(","); // Health Services Names:att:1585
        sb.append(","); // Health Services Names French:att:1586
        sb.append(","); // Brands Names:att:1587
        sb.append(","); // Brands Names French:att:1588
        sb.append(","); // Department-Pharmacy:att:1589
        sb.append(","); // Department-Dietician:att:1590
        sb.append(","); // DD-Practitioner Email:att:1591
        sb.append(","); // DD-Practitioner Photo:att:1592
        sb.append(","); // DD-Practitioner Profile:att:1593
        sb.append(","); // DD-Practitioner Profile French:att:1594
        sb.append(","); // DD-Medical Category:att:1595
        sb.append(","); // DD-Medical Category French:att:1596
        sb.append(","); // DD-Custom Department Name:att:1597
        sb.append(","); // DD-Custom Department Name French:att:1598
        sb.append(","); // DD-Phone Extension:att:1599
        sb.append(","); // DD-Description:att:1600
        sb.append(","); // DD-Phone Number:att:1601
        sb.append(","); // DD-Fax Number:att:1602
        sb.append(","); // DD-Description French:att:1603
        sb.append(","); // DD-Department Id:att:1604
        sb.append(","); // DD-Manager Name:att:1605
        sb.append(","); // DD-Manager Photo:att:1606
        sb.append(","); // DD-Department Name:att:1607
        sb.append(","); // DD-Department Name French:att:1608
        sb.append(","); // DDP-Pharmacy Name:att:1609
        sb.append(","); // DDP-Pharmacy French Name:att:1610
        sb.append(","); // DDP-Pharmacy Legal Name:att:1611
        sb.append(","); // DDP-Pharmacy Legal French Name:att:1612
        sb.append(","); // DDP-Pharmacy Business Owner:att:1613
        sb.append(","); // DDP-Pharmacy Email:att:1614
        sb.append(","); // DDP-Pharmacy Manager License:att:1615
        sb.append(","); // DDP-Pharmacy College Logo:att:1616
        sb.append(","); // DDP-Pharmacy College Url:att:1617
        sb.append(","); // DDP-Pharmacy College Street:att:1618
        sb.append(","); // DDP-Pharmacy College City:att:1619
        sb.append(","); // DDP-Pharmacy College Province:att:1620
        sb.append(","); // DDP-Pharmacy College Postal Code:att:1621
        sb.append(","); // DDP-Pharmacy College Phone Number:att:1622
        sb.append(","); // DDP-Pharmacy College Fax Number:att:1623
        sb.append(","); // DDP-Pharmacy College License:att:1624
        sb.append(","); // DDP-Pharmacy Accreditation Number:att:1625
        sb.append(","); // DDP-Pharmacy College Name:att:1626
        sb.append(","); // DDP-Pharmacy Delivery:att:1627
        sb.append(","); // DDP-Pharmacy Delivery Charge:att:1628
        sb.append(","); // DDP-Pharmacy Delivery Amount:att:1629
        sb.append(","); // DDP-Pharmacy Delivery Hours:att:1630
        sb.append(","); // Department-Garden Center:att:1631
        sb.append(","); // Department-Optical:att:1632
        sb.append(","); // Department-Medical Clinic:att:1633
        sb.append(","); // Department-Alcohol:att:1659
        sb.append(","); // placeholder1:att:1661
        sb.append(","); // placeholder2:att:1662
        sb.append(","); // placeholder3:att:1663
        sb.append(","); // placeholder4:att:1664
        sb.append(","); // placeholder5:att:1665
        sb.append(","); // placeholder6:att:1666
        sb.append(","); // placeholder7:att:1667
        sb.append(","); // placeholder8:att:1668
        sb.append(","); // placeholder9:att:1669
        sb.append(","); // placeholder10:att:1670
        sb.append(","); // placeholder11:att:1671
        sb.append(","); // placeholder12:att:1672
        sb.append(","); // placeholder13:att:1673
        sb.append(","); // placeholder14:att:1674
        sb.append(","); // placeholder15:att:1675
        sb.append(","); // Brooklyn:att:1660
        sb.append(","); // franchiseeBio:att:1887
        sb.append(","); // franchiseeImage:att:1888
        sb.append(","); // Banners-SDM:cat:87810
        sb.append(","); // HOME HEALTH CARE:cat:87811
        sb.append(","); // MURALE:cat:87812
        sb.append(","); // PHARMAPRIX:cat:87813
        sb.append(","); // SHOPPERS DRUG MART:cat:87814
        sb.append(","); // SHOPPERS SIMPLY PHARMACY:cat:87815
        sb.append(","); // Banners-LCL:cat:87816
        sb.append(","); // Atlantic Superstore:cat:87817
        sb.append(","); // AXEP:cat:87818
        sb.append(","); // Bloor Street Market:cat:87819
        sb.append(","); // Box:cat:87820
        sb.append(","); // Club Entrepôt:cat:87821
        sb.append(","); // Dominion:cat:87822
        sb.append(","); // Extra Foods:cat:87823
        sb.append(","); // Fortinos:cat:87824
        sb.append(","); // Freshmart:cat:87825
        sb.append(","); // Freshmart R&W:cat:87826
        sb.append(","); // Independent City Market:cat:87827
        sb.append(","); // Joe:cat:87828
        sb.append(","); // L'Intermarche:cat:87829
        sb.append(","); // L'Intermarche international:cat:87830
        sb.append(","); // Loblaws:cat:87831
        sb.append(","); // Loblaws Great Food:cat:87832
        sb.append(","); // LUCKY DOLLAR FOODS:cat:87833
        sb.append(","); // Maxi:cat:87834
        sb.append(","); // Maxi & Cie:cat:87835
        sb.append(","); // No Frills:cat:87836
        sb.append(","); // Provigo:cat:87837
        sb.append(","); // Real Canadian Liquor Store:cat:87838
        sb.append(","); // Real Canadian Superstore:cat:87839
        sb.append(","); // Real Canadian Wholesale Club:cat:87840
        sb.append(","); // Save Easy:cat:87841
        sb.append(","); // SHOP EASY FOODS:cat:87842
        sb.append(","); // Superstore:cat:87843
        sb.append(","); // SUPER VALU FOODS:cat:87844
        sb.append(","); // Valu-mart:cat:87845
        sb.append(","); // Wholesale Club:cat:87846
        sb.append(","); // Your Independent Grocer:cat:87847
        sb.append(","); // Zehrs:cat:87848
        sb.append(","); // Zehrs Great Food:cat:87849
        sb.append(","); // PC Plus:cat:87850
        sb.append(","); // Grocery Services:cat:87855
        sb.append(","); // Bakery:cat:87856
        sb.append(","); // Beer and Wine:cat:87857
        sb.append(","); // Canteen:cat:87858
        sb.append(","); // Catering:cat:87859
        sb.append(","); // Extended Services:cat:87860
        sb.append(","); // Canada Post Office:cat:87861
        sb.append(","); // Dry Cleaners:cat:87862
        sb.append(","); // Parking:cat:87863
        sb.append(","); // Dentist:cat:87864
        sb.append(","); // Photo Lab:cat:87865
        sb.append(","); // Pharmacy Services:cat:87866
        sb.append(","); // Beauty Boutique:cat:87867
        sb.append(","); // Medical Clinic:cat:87868
        sb.append(","); // Health & Beauty:cat:87869
        sb.append(","); // Joe Fresh Cosmetics:cat:87870
        sb.append(","); // Brands:cat:87871
        sb.append(","); // Anna Sui:cat:87872
        sb.append(","); // Annabelle:cat:87873
        sb.append(","); // Art Deco:cat:87874
        sb.append(","); // Avène:cat:87875
        sb.append(","); // Benefit Cosmetics:cat:87876
        sb.append(","); // Bio Beauté:cat:87877
        sb.append(","); // Biotherm:cat:87878
        sb.append(","); // Bourjois:cat:87879
        sb.append(","); // Chanel Cosmetics:cat:87880
        sb.append(","); // Chanel Fragrance:cat:87881
        sb.append(","); // Clarins:cat:87882
        sb.append(","); // Clarisonic:cat:87883
        sb.append(","); // Clinique:cat:87884
        sb.append(","); // Cover FX:cat:87885
        sb.append(","); // Cover Girl:cat:87886
        sb.append(","); // D & G:cat:87887
        sb.append(","); // Darphin:cat:87888
        sb.append(","); // Dior:cat:87889
        sb.append(","); // DKNY:cat:87890
        sb.append(","); // Dr. Hauschka:cat:87891
        sb.append(","); // DuWop:cat:87892
        sb.append(","); // Elizabeth Arden:cat:87893
        sb.append(","); // Essence:cat:87894
        sb.append(","); // Estée Lauder:cat:87895
        sb.append(","); // Etival:cat:87896
        sb.append(","); // Fekkai:cat:87897
        sb.append(","); // Footner:cat:87898
        sb.append(","); // Fusion Beauty:cat:87899
        sb.append(","); // Gosh:cat:87900
        sb.append(","); // Guerlain:cat:87901
        sb.append(","); // Joe Fresh:cat:87902
        sb.append(","); // Korres:cat:87903
        sb.append(","); // L’Occitane:cat:87904
        sb.append(","); // La Roche-Posay:cat:87905
        sb.append(","); // Lab Series:cat:87906
        sb.append(","); // Lancôme:cat:87907
        sb.append(","); // Lierac:cat:87908
        sb.append(","); // Lise Watier:cat:87909
        sb.append(","); // L'Oreal Paris:cat:87910
        sb.append(","); // Marcelle:cat:87911
        sb.append(","); // Maybelline:cat:87912
        sb.append(","); // Murad:cat:87913
        sb.append(","); // N°7:cat:87914
        sb.append(","); // Nacara:cat:87915
        sb.append(","); // NeoStrata:cat:87916
        sb.append(","); // Nuxe:cat:87917
        sb.append(","); // NYC New York Color:cat:87918
        sb.append(","); // NYX:cat:87919
        sb.append(","); // Origins:cat:87920
        sb.append(","); // Orlane:cat:87921
        sb.append(","); // Philosophy:cat:87922
        sb.append(","); // Physicians Formula:cat:87923
        sb.append(","); // Phyto:cat:87924
        sb.append(","); // Prevage:cat:87925
        sb.append(","); // Pur Minerals:cat:87926
        sb.append(","); // Quo:cat:87927
        sb.append(","); // RapidLash:cat:87928
        sb.append(","); // Reversa:cat:87929
        sb.append(","); // Revlon:cat:87930
        sb.append(","); // Rimmel London:cat:87931
        sb.append(","); // RoC:cat:87932
        sb.append(","); // Rodial:cat:87933
        sb.append(","); // Shiseido:cat:87934
        sb.append(","); // Skin Solutions:cat:87935
        sb.append(","); // Soap & Glory:cat:87936
        sb.append(","); // St. Tropez:cat:87937
        sb.append(","); // Stila:cat:87938
        sb.append(","); // StriVectin:cat:87939
        sb.append(","); // Urban Decay:cat:87940
        sb.append(","); // Vasanti:cat:87941
        sb.append(","); // Vichy:cat:87942
        sb.append(","); // Yes To!:cat:87943
        sb.append(","); // Yves Saint Laurent:cat:87944
        sb.append(","); // Pharmacy:cat:88301
        sb.append(","); // Pharmacy delivery:cat:88302
        sb.append(","); // Dietician:cat:88303
        sb.append(","); // Family Vision Center:cat:88304
        sb.append(","); // Garden centre:cat:88305
        sb.append(","); // Gas Bar:cat:88306
        sb.append(","); // Gift cards:cat:88307
        sb.append(","); // Joe Fresh Mens:cat:88308
        sb.append(","); // Joe Fresh Womens:cat:88309
        sb.append(","); // Liquor Store:cat:88310
        sb.append(","); // PC Cooking School:cat:88311
        sb.append(","); // PC Financial:cat:88312
        sb.append(","); // PC Home:cat:88313
        sb.append(","); // The Mobile Shop:cat:88314
        sb.append(","); // The Wine Shop:cat:88315
        sb.append(","); // Cheese wall:cat:88345
        sb.append(","); // Dairy:cat:88346
        sb.append(","); // Deli:cat:88347
        sb.append(","); // Ethnic Foods:cat:88349
        sb.append(","); // Frozen:cat:88350
        sb.append(","); // Juice Bar:cat:88351
        sb.append(","); // Meat:cat:88352
        sb.append(","); // Organics:cat:88353
        sb.append(","); // PC black label:cat:88354
        sb.append(","); // Salad bar:cat:88355
        sb.append(","); // Seafood:cat:88356
        sb.append(","); // Sushi bar:cat:88357
        sb.append(","); // Tea emporium:cat:88358
        sb.append(","); // President's Choice:cat:89149

        return sb;
    }

}
