package com.auribises.recyclerviewdemo.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.auribises.recyclerviewdemo.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UserRegistrationActivity extends AppCompatActivity {

    EditText eTxtDateTime;
    Button btnDateTime;

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    String date;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor; // to write

    void initViews(){
        eTxtDateTime = findViewById(R.id.editText);
        btnDateTime = findViewById(R.id.button);

        btnDateTime.setOnClickListener(clickListener);


        Calendar calendar = Calendar.getInstance();
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        int mm = calendar.get(Calendar.MONTH);
        int yy = calendar.get(Calendar.YEAR);

        int hr = calendar.get(Calendar.HOUR_OF_DAY);
        int mi = calendar.get(Calendar.MINUTE);

        datePickerDialog = new DatePickerDialog(this,dateSetListener,yy,mm,dd);
        timePickerDialog = new TimePickerDialog(this,timeSetListener,hr,mi,true);

        // Explore
        //SimpleDateFormat dateFormat = new SimpleDateFormat("");

        sharedPreferences = getSharedPreferences("myPrefs",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        date = sharedPreferences.getString("keyDate","NA");
        eTxtDateTime.setText(date);
    }


    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            datePickerDialog.show();
            //timePickerDialog.show();

            //readFromFile();
        }
    };

    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int y, int m, int d) {

            date = d+"/"+(m+1)+"/"+y;
            eTxtDateTime.setText(date);
            //writeInFile();

            editor.putString("keyDate",date);
            editor.apply(); // Save the Data in XML File
            Toast.makeText(UserRegistrationActivity.this,"Date Time Saved in Preferences",Toast.LENGTH_LONG).show();
        }
    };

    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hr, int mi) {
            String time = hr+" : "+mi;
            eTxtDateTime.setText(time);
        }
    };



    void writeInFile(){
        try {

            // External File | Grant Permissions in Manifest (WRITE_EXTERNAL_STORAGE | READ_EXTERNAL_STORAGE)
            //String path = Environment.getExternalStorageDirectory().getAbsolutePath();
            //FileOutputStream fos = new FileOutputStream(path+"/datetime.txt");

            // Internal File
            FileOutputStream fos = openFileOutput("datetime.txt", MODE_PRIVATE);
            fos.write(date.getBytes());
            fos.close();
            Toast.makeText(this,"Date Time Stamp Saved in File",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    void readFromFile(){
        try {
            FileInputStream fis = openFileInput("datetime.txt");
            InputStreamReader reader = new InputStreamReader(fis);
            BufferedReader buffer = new BufferedReader(reader);

            String date = buffer.readLine();

            eTxtDateTime.setText(date);

            Toast.makeText(this,"Date Time Stamp Saved in File is "+date,Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        initViews();
    }
}
