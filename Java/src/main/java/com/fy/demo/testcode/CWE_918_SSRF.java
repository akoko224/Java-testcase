package com.fy.demo.testcode;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/CWE_918_SSRF")
public class CWE_918_SSRF {
    private final static Logger logger = LoggerFactory.getLogger(CWE_918_SSRF.class);

    public static boolean isHttp(String url) {
        return url.startsWith("http://") || url.startsWith("https://");
    }

    @GetMapping("/urlConnection/vuln")
    public String URLConnectionSec(String url) {
        // Decline not http/https protocol
        if (!isHttp(url)) {
            return "[-] SSRF check failed";
        }
        return URLConnection(url);
    }


    public static String URLConnection(String url) {
        try {
            URL u = new URL(url);
            URLConnection urlConnection = u.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream())); //send request
            // BufferedReader in = new BufferedReader(new InputStreamReader(u.openConnection().getInputStream()));
            String inputLine;
            StringBuilder html = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                html.append(inputLine);
            }
            in.close();
            return html.toString();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return e.getMessage();
        }
    }


    public static String HttpURLConnection(String url) {
        try {
            URL u = new URL(url);
            URLConnection urlConnection = u.openConnection();
            HttpURLConnection conn = (HttpURLConnection) urlConnection;
            // Many HttpURLConnection methods can send http request, such as getResponseCode, getHeaderField
            InputStream is = conn.getInputStream();  // send request
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String inputLine;
            StringBuilder html = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                html.append(inputLine);
            }
            in.close();
            return html.toString();
        } catch (IOException e) {
            logger.error(e.getMessage());
            return e.getMessage();
        }
    }


}
