package conf;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import static ninja.SecureFilter.USERNAME;
import static org.apache.http.protocol.HTTP.USER_AGENT;

public class EpitechApi {
    private String user_name;
    private String user_password;
    private String connection;
    private String notifications;


    private String executeGet(final String https_url, final String proxyName, final int port) {
        String ret = "";

        URL url;
        try {

            HttpsURLConnection con;
            url = new URL(https_url);

            if (proxyName.isEmpty()) {
                System.out.println("url = " + https_url);
                con = (HttpsURLConnection) url.openConnection();
                System.out.println("cuefjojcdjs");
            } else {
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyName, port));
                con = (HttpsURLConnection) url.openConnection(proxy);
                Authenticator authenticator = new Authenticator() {
                    public PasswordAuthentication getPasswordAuthentication() {
                        return (new PasswordAuthentication(user_name, user_password.toCharArray()));
                    }
                };
                Authenticator.setDefault(authenticator);
            }

            ret = con.getContent().toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("command work = " + ret);
        return ret;
    }


    private static String getHTML(String urlToRead) throws Exception {
        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlToRead);
            System.out.println(urlToRead);
            System.out.println(url.toString());
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            System.out.println("conn init");
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            System.out.println("command work");
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();

            return result.toString();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    private String sendGet(String url) throws Exception {

        //String url = "http://www.google.com/search?q=developer";

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);

        // add request header
        request.addHeader("User-Agent", USER_AGENT);

        HttpResponse response = client.execute(request);

        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " +
                response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        System.out.println(result.toString());
        return result.toString();
    }

    private String sendPost(String url, String urlParameters) throws Exception {

        //url = "https://selfsolve.apple.com/wcResults.do";
        URL obj = new URL(url);
        System.out.println("url = " + url);
        System.out.println("urlParameters = " + urlParameters);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        //String urlParameters= "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
//        return(response.toString());
        return response.toString();
    }

    public EpitechApi(String name, String password) {
        user_name = name;
        user_password = password;
        connection = "test";
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public String getConnection() {
        return connection;
    }

    public String getNotifications() {
        return notifications;
    }

    public String POSTT(String url, String param1, String param2, String param3) {
        try {
            String httpsURL = "https://intra.epitech.eu/";

            String query = "login=" + URLEncoder.encode("joseph.demersseman@epitech.eu", "UTF-8");
            query += "&";
            query += "password=" + URLEncoder.encode("=bdl2SL^", "UTF-8");

            URL myurl = new URL(httpsURL);
            HttpsURLConnection con = (HttpsURLConnection) myurl.openConnection();
            con.setRequestMethod("POST");

            con.setRequestProperty("Content-length", String.valueOf(query.length()));
            con.setRequestProperty("Content-Type", "application/x-www- form-urlencoded");
            con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0;Windows98;DigExt)");
            con.setDoOutput(true);

            con.setDoInput(true);

            DataOutputStream output = new DataOutputStream(con.getOutputStream());


            output.writeBytes(query);

            output.close();

            DataInputStream input = new DataInputStream(con.getInputStream());


            for (int c = input.read(); c != -1; c = input.read())
                System.out.print((char) c);
            input.close();

            System.out.println("Resp Code:" + con.getResponseCode());
            System.out.println("Resp Message:" + con.getResponseMessage());
            return con.getResponseMessage();
        } catch (Exception ex) {
            System.out.println("error = " + ex);
        }
        return null;
    }

    public void init() {
        notifications = "MARCHE PAS";
        connection = "MARCHE PAS";

        //connection = executeGet("https://intra.epitech.eu/user/notification/message?format=json","",80);
        System.out.println(connection);
        System.out.println(notifications);


        try {
            System.out.println("");
            System.out.println("");
            System.out.println("");
            System.out.println("connection test");
            connection = POSTT("https://intra.epitech.eu/", "&login=" + user_name, "&password=" + "test", "&remember_me=on&format=json");
            System.out.println("connection success");
            //           if (connection != null) {
            //           notifications = getHTML("https://intra.epitech.eu?&format=json");
//            }
            System.out.println("connection = " + connection);
            System.out.println("notification = " + notifications);
            URL url = new URL("https://intra.epitech.eu/?format=json");
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();
            System.out.println("is = " + is.toString());

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            System.out.println("command work");
            String line;
            StringBuffer result = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            System.out.println("result = " + result.toString());

        } catch (Exception ex) {
            System.err.println("exeption = " + ex.getMessage());
        }
    }

    public void newModule() {

    }
}
