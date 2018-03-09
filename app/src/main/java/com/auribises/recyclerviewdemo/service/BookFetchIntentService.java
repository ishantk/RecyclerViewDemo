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


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class BookFetchIntentService extends IntentService {

    public BookFetchIntentService() {
        super("BookFetchIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

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

        }catch (Exception e){
            e.printStackTrace();
        }

        new BookTask().execute();
        Log.i("Service","onHandleIntent");
    }

    class BookTask extends AsyncTask {

        StringBuilder response = new StringBuilder();

        @Override
        protected Object doInBackground(Object[] objects) {

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

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Log.i("Service",response.toString());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("Service",response.toString());
        }
    }

}
