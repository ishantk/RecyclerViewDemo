package com.auribises.recyclerviewdemo.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.auribises.recyclerviewdemo.utility.Util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class BookFetchIntentService extends IntentService {

    public BookFetchIntentService() {
        super("BookFetchIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.i("Service","onHandleIntent");

        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(Util.URL_BOOKS_FETCH);

            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line = "";

            while((line = bufferedReader.readLine()) != null){
                response.append(line+"\n");
            }

            Log.i("Service",response.toString());

            Intent responseIntent = new Intent("response.rcvd");
            responseIntent.putExtra("keyResponse",response.toString());
            sendBroadcast(responseIntent);


        }catch (Exception e){
            e.printStackTrace();
        }


    }


}
