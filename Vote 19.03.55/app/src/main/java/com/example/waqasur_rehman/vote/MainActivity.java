package com.example.waqasur_rehman.vote;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends BaseActivity {



    public static final String Default = "N/A"; // hold null value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        SharedPreferences sharedPreferences = getSharedPreferences("mydata", getApplicationContext().MODE_PRIVATE);
        String user = sharedPreferences.getString("Email", Default);

        getSupportActionBar().setTitle(user);


    }



    public void upload_UI(View view) {

        Intent intent = new Intent(MainActivity.this, Upload.class);
        startActivity(intent);


    }

    public void ViewResult(View view) {

        Intent intent = new Intent(MainActivity.this, Result.class);
        startActivity(intent);


    }

    public void openPlanner(View view) {

        Intent intent = new Intent(MainActivity.this,Schedule_Planner.class);
        startActivity(intent);

    }


    public void openInfo(View view) {

        Intent intent = new Intent(MainActivity.this, Info.class);
        startActivity(intent);

    }
    public void OpenQRGenerator(View view) {

        Intent intent = new Intent(MainActivity.this,QR_Survey.class);
        startActivity(intent);

    }

    public void onLogout(View view) {


        SharedPreferences sharedPreferences = getSharedPreferences("mydata", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();

        Intent intent = new Intent(MainActivity.this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }


    public void onEmail(View view) {

        Intent intent = new Intent(MainActivity.this,Email.class);
        startActivity(intent);
    }
}
