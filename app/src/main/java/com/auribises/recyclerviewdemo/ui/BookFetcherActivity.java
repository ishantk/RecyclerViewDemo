package com.auribises.recyclerviewdemo.ui;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.auribises.recyclerviewdemo.R;
import com.auribises.recyclerviewdemo.model.Book;
import com.auribises.recyclerviewdemo.utility.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class BookFetcherActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Book> bookList;

    void initViews(){
        recyclerView = findViewById(R.id.recyclerView);
        bookList = new ArrayList<>();
    }

    boolean isInternetConnected(){

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return (networkInfo!=null && networkInfo.isConnected());

    }

    void fetchBooks(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Util.URL_BOOKS_FETCH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray(Util.BOOK_ARRAY);

                            String p="",n="",a="";

                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject jObj = jsonArray.getJSONObject(i);

                                p = jObj.getString("price");
                                n = jObj.getString("name");
                                a = jObj.getString("author");

                                Book book = new Book(p,n,a);
                                bookList.add(book);
                            }

                            Toast.makeText(BookFetcherActivity.this,"Books Fetched Successfully !!",Toast.LENGTH_LONG).show();

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BookFetcherActivity.this,"Error while Fetching Books",Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                }
        );

        requestQueue.add(stringRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_fetcher);
        initViews();

        if(isInternetConnected())
            fetchBooks();
        else
            Toast.makeText(this,"Please Connect to the Internet and Try Again",Toast.LENGTH_LONG).show();
    }
}
