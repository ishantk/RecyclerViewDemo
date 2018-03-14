package com.auribises.recyclerviewdemo.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.auribises.recyclerviewdemo.R;

public class MiscActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    Button button;

    void initViews() {

        linearLayout = new LinearLayout(this);
        button = new Button(this);
        button.setText("Click Me");

        linearLayout.addView(button);

        //setContentView(linearLayout);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showDialog();
                //showDialogWithItems();
                //showCustomDialog();
                //showProgressDialog(true);
            }
        });
    }

    void showDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("This is Title");
        builder.setMessage("This is a Message");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.create().show();

    }

    void showDialogWithItems() {

        String[] items = {"View", "Call", "Message", "Share"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                switch (i) {
                    case 1:
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:+919915571177"));
                        if (ActivityCompat.checkSelfPermission(MiscActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(MiscActivity.this,"Please Grant Permissions",Toast.LENGTH_LONG).show();
                        }else {
                            startActivity(intent);
                        }
                        break;

                    case 2:

                        String phone = "+919915571177";
                        String msg = "Search the candle rather than cursing the darkness!!";

                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phone,null,msg,null,null);

                        break;
                }

            }
        });
        builder.create().show();

    }

    Button btn;

    void showCustomDialog(){

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.layout_dialog);

        btn = dialog.findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        dialog.show();
    }

 /*   ProgressDialog progressDialog = new ProgressDialog(this);
    void showProgressDialog(boolean show){

        if(show) {
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false); // Dialog cannot be dismissed by pressing back key
            progressDialog.show();
        }else{
            progressDialog.dismiss();
        }
    }*/


    TextView txtTitle;
    EditText eTxtName;
    Button btnSubmit;
    ImageView imageView;

    void initMyViews(){
        txtTitle = findViewById(R.id.textViewTitle);
        eTxtName = findViewById(R.id.editTextName);
        btnSubmit = findViewById(R.id.buttonSubmit);
        imageView = findViewById(R.id.imageView);


        Animation animation = AnimationUtils.loadAnimation(this,R.anim.alpha_animation);
        Animation animation1 = AnimationUtils.loadAnimation(this,R.anim.rotate_animation);

        txtTitle.startAnimation(animation);
        eTxtName.startAnimation(animation);
        btnSubmit.startAnimation(animation1);

        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_misc);

        initMyViews();

        //initViews();
    }
}
