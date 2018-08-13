package com.example.waqasur_rehman.vote;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.waqasur_rehman.vote.Login.Default;

/**
 * Created by waqas on 29/08/2017.
 */

public class schedule_detail extends BaseActivity {

        String id, name , user;
    TextView Name,Time, Date, Description;

    String getscheduledetail = "add link appropiate php webservice script ";//schedule detail
    String confirmschedule_url = "add link appropiate php webservice script"; //confirm schedule
    protected final String getconfirmmeeting="add link appropiate php webservice script ";//check meeting



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPreferences = getSharedPreferences("mydata", Context.MODE_PRIVATE);
        user = sharedPreferences.getString("Email", Default);
        name = sharedPreferences.getString("name",Default);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            id = bundle.getString("meetingid");
        }


        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, getscheduledetail,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        // response
                        Log.d("Response", response);

                        try {
                            JSONArray data = new JSONArray(response);

                            for (int i = 0; i < data.length(); i++) {



                                JSONObject obj = data.getJSONObject(i);

                                 Name = (TextView) findViewById(R.id.Name_detail);
                                Name.setText(obj.getString("Name"));
                                 Date =(TextView)findViewById(R.id.date_detail);
                                Date.setText(obj.getString("Date"));
                                 Time = (TextView)findViewById(R.id.time_detail);
                                Time.setText(obj.getString("Time"));
                                 Description = (TextView)findViewById(R.id.desc_detail);
                                Description.setText(obj.getString("Description"));



                            }

                            Button attend = (Button)findViewById(R.id.Attend_);

                            Button notattending = (Button)findViewById(R.id.notAttending_);

                            notattending.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                     Intent intent = new Intent(schedule_detail.this, Employee_main.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });


                            attend.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    Checkformeeting();

                                }


                            });

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
                params.put("ID", id.toString());
                return params;
            }
        };
        queue.add(postRequest);






    }

    public void  confirmschedule(){


        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, confirmschedule_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        // response
                        Log.d("Response", response);
                        Toast.makeText(schedule_detail.this," Meeting Confirmed", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(schedule_detail.this, Employee_main.class);
                        startActivity(intent);
                        finish();

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
                params.put("ID", id.toString());
                params.put("name",name.toString());
                params.put("user", user.toString());
                params.put("Confirm", "Attending");
                params.put("meetingname", Name.getText().toString());

                return params;
            }
        };
        queue.add(postRequest);

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }


    public void  Checkformeeting(){


        RequestQueue queue = Volley.newRequestQueue(this);


        StringRequest postRequest = new StringRequest(Request.Method.POST, getconfirmmeeting,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        // response
                        Log.d("Response", response);

                        if(response.trim().equals("Attending")){


                            Toast.makeText(schedule_detail.this, "You are already Attending this meeting ",Toast.LENGTH_SHORT).show();



                        }
                        if(response.trim().equals("NotAttending")){

                            confirmschedule();

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
                params.put("employee", user);
                params.put("meetingid", id.toString());
                return params;
            }
        };
        queue.add(postRequest);






    }
}