package com.example.waqasur_rehman.vote;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by waqas on 16/07/2017.
 */

public class Employee_main extends BaseActivity {


    public static final String Default = "N/A"; // hold null value;
    String user;

    protected final String employeesname = "add link appropiate php webservice script"; 


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_main);




        SharedPreferences sharedPreferences = getSharedPreferences("mydata", getApplicationContext().MODE_PRIVATE);
        user = sharedPreferences.getString("Email", Default);

        getSupportActionBar().setTitle(user.substring(0,4));


        RequestQueue queue = Volley.newRequestQueue(this);

        // volley request for getting all the users that have voted for a specific question.

        StringRequest employeevoted = new StringRequest(Request.Method.POST, employeesname,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try{


                            JSONArray array = new JSONArray(response.toString());

                            for(int i =0;i<array.length(); i++){

                                JSONObject obj = array.getJSONObject(i);

                                String name = obj.getString("name");
                                SharedPreferences sharedPreferences = getSharedPreferences("mydata", getApplicationContext().MODE_PRIVATE);
                                SharedPreferences.Editor editor= sharedPreferences.edit();
                                editor.putBoolean("mydata",true);
                                editor.putString("name",name);
                                editor.commit();


                                Log.d("User Name",name);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }




                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", "");
                    }
                }
        ) {


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", user.toString());
                return params;
            }
        };
        queue.add(employeevoted);



    }




    public void OnLogoutEmployee(View view) {

        SharedPreferences sharedPreferences = getSharedPreferences("mydata", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.putBoolean("mydata", false);
        editor.clear();
        editor.commit();

        Intent intent = new Intent(Employee_main.this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }

    public void onvote(View view) {

        Intent intent = new Intent(Employee_main.this, Vote.class);
        startActivity(intent);

    }


    public void View_setting(View view) {

        Intent setting = new Intent(Employee_main.this, Setting.class);
        startActivity(setting);
    }

    public void openPlanner(View view) {

        Intent planner = new Intent(Employee_main.this, Schedular.class);
        startActivity(planner);
    }

    public void openInfo(View view) {

        Intent info = new Intent(Employee_main.this, Info.class);
        startActivity(info);


    }

    public void OpenQRGenerator(View view) {

        Intent reader= new Intent(Employee_main.this, QR_reader.class);
        startActivity(reader);


    }
}
