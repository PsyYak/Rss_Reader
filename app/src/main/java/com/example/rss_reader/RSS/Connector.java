package com.example.rss_reader.RSS;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Connector {

    public static Object connect(String urlAddress){

        try{
            URL url = new URL(urlAddress);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Properties
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);
            connection.setDoInput(true);

            return connection;


        }catch (MalformedURLException e){
            e.printStackTrace();
            return ErrorTracker.WRONG_URL_FORMAT;
        }catch(IOException e){
            e.printStackTrace();
            return  ErrorTracker.CONNECTION_ERROR;
        }
    }


}
