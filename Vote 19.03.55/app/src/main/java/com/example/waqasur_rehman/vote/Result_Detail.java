package com.example.waqasur_rehman.vote;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.view.View.VISIBLE;

/**
 * Created by waqas on 20/08/2017.
 */

public class Result_Detail extends  BaseActivity {

    protected final String numberofusers = "/add link appropiate php webservice script/"; // number of users
    protected final String employeesvoted = "/add link appropiate php webservice script/"; // employee that have voted

    private result_detail_Adapter Adapter;

    private ArrayList<result_detail_fetched> array = new ArrayList<result_detail_fetched>();
    private ArrayList<result_detail_fetched> array2 = new ArrayList<result_detail_fetched>();

    TextView percentage;
    String id;

    private ProgressDialog dialog;
     ProgressBar progressBar;

    Boolean completed = false;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            id = bundle.getString("ID");
        }

        dialog = new ProgressDialog(Result_Detail.this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();
        percentage = (TextView) findViewById(R.id.percentage);

      progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        progressBar.setVisibility(VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(this);
        //Create volley for getting max number of users for progress bar
        JsonArrayRequest numberofuser = new JsonArrayRequest(numberofusers, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setMax(response.length());
                //parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        result_detail_fetched detail = new result_detail_fetched();

                        detail.setName(obj.getString("Name"));
                        //detail.setEmail(obj.getString("Email"));
                        detail.setEmail(obj.getString("Email"));
                        array.add(detail);

                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }
                getvoteprogress();
                Adapter.notifyDataSetChanged();
                hideDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(numberofuser);



        ListView listView = (ListView) findViewById(R.id.result_detail_listview);


        Adapter = new result_detail_Adapter(this, array2);
        setProgressBarIndeterminate(true);

        listView.setAdapter(Adapter);


    }
    public void getvoteprogress(){

        RequestQueue queue1 = Volley.newRequestQueue(this);

        // volley request for getting all the users that have voted for a specific question.

        final StringRequest employeevoted = new StringRequest(Request.Method.POST, employeesvoted,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {


                            JSONArray json = new JSONArray(response.toString());


                            progressBar.setProgress(json.length());


                            for (int i = 0; i < json.length(); i++) {
                                result_detail_fetched detail2 = new result_detail_fetched();


                                JSONObject obj = json.getJSONObject(i);
                                detail2.setEmail(obj.getString("Email"));
                                detail2.setName(obj.getString("Name"));
                                array2.add(detail2);

                                //Log.d("Array2 value ", array2.toString());

                            }

                            double max = array.size();
                            final double progress = array2.size();
                            Log.d("array", String.valueOf(max));
                            Log.d("array2", String.valueOf(progress));

                            try {
                                double result = (((double) progress / (double) max) * 100);
                                Log.d("result", String.valueOf(result));

                                percentage.setText(String.valueOf(result));
                                completed = false;
                                if (progress == max) {

                                    percentage.setText("Completed");
                                    completed = true;
                                }


                                Button report = (Button) findViewById(R.id.Check_Report);
                                report.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        if (completed) {

                                            Intent intent = new Intent(Result_Detail.this, Report.class);
                                            intent.putExtra("ID", id.toString());
                                            startActivity(intent);

                                        } else {

                                            Toast.makeText(Result_Detail.this, " Voting is Still in progress", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            } catch (ArithmeticException e) {


                            }
                            hideDialog();
                            Adapter.notifyDataSetChanged();

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
                params.put("surveyid", id);
                return params;
            }
        };
        queue1.add(employeevoted);
    }


//    public void onReport(View view) {
//
//
//        Intent intent = new Intent(Result_Detail.this, Report.class);
//        intent.putExtra("ID", id.toString());
//        startActivity(intent);
//
//
//    }

    public void hideDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }


}







