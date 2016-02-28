package com.bookmarketer.nw.bookmarket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Rafay on 27/02/2016.
 */
public class httpManager {

    public static String getData(String uri) {
        URL url = null;
        BufferedReader br=null;
        try {
            url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            StringBuilder sb= new StringBuilder();
            br=new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line;
            while((line = br.readLine())!=null){
                sb.append(line + "\n");
            }

            return sb.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return "";
    }
}
