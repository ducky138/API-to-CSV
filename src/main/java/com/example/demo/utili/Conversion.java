package com.example.demo.utili;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import org.apache.tomcat.util.codec.binary.Base64;
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

    public StringBuilder convertStoreJSONToCSV(JSONObject obj) {
        StringBuilder sb = new StringBuilder(
                "ThirdPartyID,Active?,LocationName,URL,IsInternet,LocationType,ResponsivenessRating,Username_Email,Country,Address1,Address2,Address3,Address4,InternationalAddress,City,State_Province,Zip_PostalCode,ContactName,ContactPosition,ContactEmail,ContactPhone,ContactFax,ContactMobile,FacebookPageId,GeocodeSourceID,LastGeocodingAttempt,LeadManager,StoreLocator,TimeZoneID,MondayOpen,MondayClose,TuesdayOpen,TuesdayClose,WednesdayOpen,WednesdayClose,ThursdayOpen,ThursdayClose,FridayOpen,FridayClose,SaturdayOpen,SaturdayClose,SundayOpen,SundayClose,Latitude,Longitude,ImageURL,Store ID:att:1557,Location Name French:att:1558,Store Type:att:1559,pcPlus:att:1560,Bilingual:att:1561,Store Phone Number:att:1562,Store Fax Number:att:1563,Store Email:att:1564,Banner Id:att:1565,Banner Name:att:1566,Banner Logo URL:att:1567,Banner Logo URL French:att:1568,Pharmacy License Number:att:1569,Pharmacy Language:att:1570,Parking Available:att:1571,Food Available:att:1572,At Least Midnight:att:1573,Charity Name:att:1574,Contact Salutation:att:1575,Contact License Number:att:1576,Associate2 Name:att:1577,Associate2 Salutation:att:1578,Associate3 Name:att:1579,Associate3 Salutation:att:1580,Department Names:att:1581,Department Names French:att:1582,Services Names:att:1583,Services Names French:att:1584,Health Services Names:att:1585,Health Services Names French:att:1586,Brands Names:att:1587,Brands Names French:att:1588,Department-Pharmacy:att:1589,Department-Dietician:att:1590,DD-Practitioner Email:att:1591,DD-Practitioner Photo:att:1592,DD-Practitioner Profile:att:1593,DD-Practitioner Profile French:att:1594,DD-Medical Category:att:1595,DD-Medical Category French:att:1596,DD-Custom Department Name:att:1597,DD-Custom Department Name French:att:1598,DD-Phone Extension:att:1599,DD-Description:att:1600,DD-Phone Number:att:1601,DD-Fax Number:att:1602,DD-Description French:att:1603,DD-Department Id:att:1604,DD-Manager Name:att:1605,DD-Manager Photo:att:1606,DD-Department Name:att:1607,DD-Department Name French:att:1608,DDP-Pharmacy Name:att:1609,DDP-Pharmacy French Name:att:1610,DDP-Pharmacy Legal Name:att:1611,DDP-Pharmacy Legal French Name:att:1612,DDP-Pharmacy Business Owner:att:1613,DDP-Pharmacy Email:att:1614,DDP-Pharmacy Manager License:att:1615,DDP-Pharmacy College Logo:att:1616,DDP-Pharmacy College Url:att:1617,DDP-Pharmacy College Street:att:1618,DDP-Pharmacy College City:att:1619,DDP-Pharmacy College Province:att:1620,DDP-Pharmacy College Postal Code:att:1621,DDP-Pharmacy College Phone Number:att:1622,DDP-Pharmacy College Fax Number:att:1623,DDP-Pharmacy College License:att:1624,DDP-Pharmacy Accreditation Number:att:1625,DDP-Pharmacy College Name:att:1626,DDP-Pharmacy Delivery:att:1627,DDP-Pharmacy Delivery Charge:att:1628,DDP-Pharmacy Delivery Amount:att:1629,DDP-Pharmacy Delivery Hours:att:1630,Department-Garden Center:att:1631,Department-Optical:att:1632,Department-Medical Clinic:att:1633,Department-Alcohol:att:1659,placeholder1:att:1661,placeholder2:att:1662,placeholder3:att:1663,placeholder4:att:1664,placeholder5:att:1665,placeholder6:att:1666,placeholder7:att:1667,placeholder8:att:1668,placeholder9:att:1669,placeholder10:att:1670,placeholder11:att:1671,placeholder12:att:1672,placeholder13:att:1673,placeholder14:att:1674,placeholder15:att:1675,Brooklyn:att:1660,franchiseeBio:att:1887,franchiseeImage:att:1888,Banners-SDM:cat:87810,HOME HEALTH CARE:cat:87811,MURALE:cat:87812,PHARMAPRIX:cat:87813,SHOPPERS DRUG MART:cat:87814,SHOPPERS SIMPLY PHARMACY:cat:87815,Banners-LCL:cat:87816,Atlantic Superstore:cat:87817,AXEP:cat:87818,Bloor Street Market:cat:87819,Box:cat:87820,Club Entrepôt:cat:87821,Dominion:cat:87822,Extra Foods:cat:87823,Fortinos:cat:87824,Freshmart:cat:87825,Freshmart R&W:cat:87826,Independent City Market:cat:87827,Joe:cat:87828,L'Intermarche:cat:87829,L'Intermarche international:cat:87830,Loblaws:cat:87831,Loblaws Great Food:cat:87832,LUCKY DOLLAR FOODS:cat:87833,Maxi:cat:87834,Maxi & Cie:cat:87835,No Frills:cat:87836,Provigo:cat:87837,Real Canadian Liquor Store:cat:87838,Real Canadian Superstore:cat:87839,Real Canadian Wholesale Club:cat:87840,Save Easy:cat:87841,SHOP EASY FOODS:cat:87842,Superstore:cat:87843,SUPER VALU FOODS:cat:87844,Valu-mart:cat:87845,Wholesale Club:cat:87846,Your Independent Grocer:cat:87847,Zehrs:cat:87848,Zehrs Great Food:cat:87849,PC Plus:cat:87850,Grocery Services:cat:87855,Bakery:cat:87856,Beer and Wine:cat:87857,Canteen:cat:87858,Catering:cat:87859,Extended Services:cat:87860,Canada Post Office:cat:87861,Dry Cleaners:cat:87862,Parking:cat:87863,Dentist:cat:87864,Photo Lab:cat:87865,Pharmacy Services:cat:87866,Beauty Boutique:cat:87867,Medical Clinic:cat:87868,Health & Beauty:cat:87869,Joe Fresh Cosmetics:cat:87870,Brands:cat:87871,Anna Sui:cat:87872,Annabelle:cat:87873,Art Deco:cat:87874,Avène:cat:87875,Benefit Cosmetics:cat:87876,Bio Beauté:cat:87877,Biotherm:cat:87878,Bourjois:cat:87879,Chanel Cosmetics:cat:87880,Chanel Fragrance:cat:87881,Clarins:cat:87882,Clarisonic:cat:87883,Clinique:cat:87884,Cover FX:cat:87885,Cover Girl:cat:87886,D & G:cat:87887,Darphin:cat:87888,Dior:cat:87889,DKNY:cat:87890,Dr. Hauschka:cat:87891,DuWop:cat:87892,Elizabeth Arden:cat:87893,Essence:cat:87894,Estée Lauder:cat:87895,Etival:cat:87896,Fekkai:cat:87897,Footner:cat:87898,Fusion Beauty:cat:87899,Gosh:cat:87900,Guerlain:cat:87901,Joe Fresh:cat:87902,Korres:cat:87903,L’Occitane:cat:87904,La Roche-Posay:cat:87905,Lab Series:cat:87906,Lancôme:cat:87907,Lierac:cat:87908,Lise Watier:cat:87909,L'Oreal Paris:cat:87910,Marcelle:cat:87911,Maybelline:cat:87912,Murad:cat:87913,N°7:cat:87914,Nacara:cat:87915,NeoStrata:cat:87916,Nuxe:cat:87917,NYC New York Color:cat:87918,NYX:cat:87919,Origins:cat:87920,Orlane:cat:87921,Philosophy:cat:87922,Physicians Formula:cat:87923,Phyto:cat:87924,Prevage:cat:87925,Pur Minerals:cat:87926,Quo:cat:87927,RapidLash:cat:87928,Reversa:cat:87929,Revlon:cat:87930,Rimmel London:cat:87931,RoC:cat:87932,Rodial:cat:87933,Shiseido:cat:87934,Skin Solutions:cat:87935,Soap & Glory:cat:87936,St. Tropez:cat:87937,Stila:cat:87938,StriVectin:cat:87939,Urban Decay:cat:87940,Vasanti:cat:87941,Vichy:cat:87942,Yes To!:cat:87943,Yves Saint Laurent:cat:87944,Pharmacy:cat:88301,Pharmacy delivery:cat:88302,Dietician:cat:88303,Family Vision Center:cat:88304,Garden centre:cat:88305,Gas Bar:cat:88306,Gift cards:cat:88307,Joe Fresh Mens:cat:88308,Joe Fresh Womens:cat:88309,Liquor Store:cat:88310,PC Cooking School:cat:88311,PC Financial:cat:88312,PC Home:cat:88313,The Mobile Shop:cat:88314,The Wine Shop:cat:88315,Cheese wall:cat:88345,Dairy:cat:88346,Deli:cat:88347,Ethnic Foods:cat:88349,Frozen:cat:88350,Juice Bar:cat:88351,Meat:cat:88352,Organics:cat:88353,PC black label:cat:88354,Salad bar:cat:88355,Seafood:cat:88356,Sushi bar:cat:88357,Tea emporium:cat:88358,President's Choice:cat:89149\n");

        sb.append("hello");

        return sb;
    }

}
