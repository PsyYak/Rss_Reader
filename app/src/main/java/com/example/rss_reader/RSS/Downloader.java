package com.example.rss_reader.RSS;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;


public class Downloader extends AsyncTask<Void,Void,Object> {

    @SuppressLint("StaticFieldLeak")
    Context context;
    String urlAddress;
    @SuppressLint("StaticFieldLeak")
    ListView listView;


    ProgressDialog progressDialog;


    public Downloader(Context context, String urlAddress, ListView listView) {
        this.context = context;
        this.urlAddress = urlAddress;
        this.listView = listView;

    }


    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Fetch Data");
        progressDialog.setMessage("Fetching.. please wait");
        progressDialog.show();

    }

    @Override
    protected Object doInBackground(Void... params) {
        return this.downloadData();
    }

    @Override
    protected void onPostExecute(Object data) {
        super.onPostExecute(data);
        progressDialog.dismiss();
        if(data.toString().startsWith("Error")){
            Toast.makeText(context,data.toString(),Toast.LENGTH_SHORT).show();

        }else{
           // Parse RSS Data
            new RSSParser(context, (InputStream) data,listView).execute();

        }

    }

    private Object downloadData(){

        Object connection = Connector.connect(urlAddress);
        if(connection.toString().startsWith("Error")){
            return connection.toString();
        }

        try{
            HttpURLConnection con = (HttpURLConnection) connection;
            int responseCode = con.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK){
                InputStream inputStream = new BufferedInputStream(con.getInputStream());
                return inputStream;
            }
            return ErrorTracker.RESPONSE_ERROR+con.getResponseMessage();

        }catch (IOException e){
            e.printStackTrace();
            return ErrorTracker.IO_ERROR;
        }






    }
}
