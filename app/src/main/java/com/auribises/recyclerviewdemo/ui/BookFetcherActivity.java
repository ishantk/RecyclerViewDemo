package com.auribises.recyclerviewdemo.ui;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.auribises.recyclerviewdemo.service.BookFetchIntentService;
import com.auribises.recyclerviewdemo.service.BookFetchService;
import com.auribises.recyclerviewdemo.utility.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class BookFetcherActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Book> bookList;
    BookAdapter bookAdapter;

    ResponseReceiver responseReceiver;

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

                            bookAdapter = new BookAdapter(BookFetcherActivity.this,R.layout.list_item,bookList);

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BookFetcherActivity.this);
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(BookFetcherActivity.this,2);

                            recyclerView.setLayoutManager(linearLayoutManager);
                            //recyclerView.setLayoutManager(gridLayoutManager);

                            recyclerView.setAdapter(bookAdapter);


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

        /*if(isInternetConnected())
            fetchBooks();
        else
            Toast.makeText(this,"Please Connect to the Internet and Try Again",Toast.LENGTH_LONG).show();
        */

        responseReceiver = new ResponseReceiver();

        IntentFilter filter = new IntentFilter();
        filter.addAction("response.rcvd");
        //filter.addAction("any.action.of.your.choice");
        //filter.addAction(Intent.ACTION_BATTERY_LOW);

        registerReceiver(responseReceiver,filter);

        if(isInternetConnected()){

            Intent intent = new Intent(BookFetcherActivity.this,BookFetchIntentService.class);
            startService(intent);

            //stopService(intent);

        }else{
            Toast.makeText(this,"Please Connect to the Internet and Try Again",Toast.LENGTH_LONG).show();
        }
    }

    class ResponseReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            String response = intent.getStringExtra("keyResponse");
            Toast.makeText(BookFetcherActivity.this,"Response: "+response,Toast.LENGTH_LONG).show();


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

                bookAdapter = new BookAdapter(BookFetcherActivity.this,R.layout.list_item,bookList);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BookFetcherActivity.this);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(BookFetcherActivity.this,2);

                recyclerView.setLayoutManager(linearLayoutManager);
                //recyclerView.setLayoutManager(gridLayoutManager);

                recyclerView.setAdapter(bookAdapter);

                showNotification();


            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }


    void showNotification(){

        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        String chId = "myChannelId";

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(chId,"MyChannel",NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,chId);
        builder.setSmallIcon(android.R.drawable.ic_menu_add);
        builder.setContentTitle("This is Title");
        builder.setContentText("This is Text of Notification");

        Intent intent = new Intent(BookFetcherActivity.this,UserRegistrationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,101,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        // Style Should be a BigTextStyle if you wish to add buttons
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText("This is Big Text"));
        builder.addAction(android.R.drawable.ic_menu_add,"Add",pendingIntent);
        builder.addAction(android.R.drawable.ic_menu_delete,"Delete",pendingIntent);


        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();

        notificationManager.notify(101,notification);

    }

}
