package com.auribises.recyclerviewdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.auribises.recyclerviewdemo.R;
import com.auribises.recyclerviewdemo.adapter.BookAdapter;
import com.auribises.recyclerviewdemo.model.Book;
import com.auribises.recyclerviewdemo.ui.BookFetcherActivity;
import com.auribises.recyclerviewdemo.utility.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BookFetchService extends Service {
    public BookFetchService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("Service","onCreate");
        Toast.makeText(this,"BookFetchService Created",Toast.LENGTH_LONG).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("Service","onStartCommand");

        Toast.makeText(this,"BookFetchService Started",Toast.LENGTH_LONG).show();


        //fetchBooks();
        new BookTask().execute();

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


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"BookFetchService Destroyed",Toast.LENGTH_LONG).show();
    }


    /*void fetchBooks(){

        Log.i("Service","fetchBooks");

        /*RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Util.URL_BOOKS_FETCH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("Service",response);

                        Toast.makeText(BookFetchService.this,"Books Fetched: "+response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BookFetchService.this,"Error while Fetching Books",Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                        Log.i("Service","error "+error.toString());
                    }
                }
        );

        requestQueue.add(stringRequest);*/



    //}

    class BookTask extends AsyncTask{

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


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
