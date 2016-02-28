package com.bookmarketer.nw.bookmarket;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Rafay on 27/02/2016.
 */
public class httpManager {

    String results="";

    public static String getData(RequestPackage p) {

        String uri = p.getUri();
        BufferedReader br=null;
        if(p.getMethod().equals("GET")){
            uri+="?"+p.getEncodedParams();
        }

        try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(p.getMethod());

            if(p.getMethod().equals("POST")){
                con.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
               writer.write(p.getEncodedParams());
                writer.flush();
            }
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

        return null;
    }
}
