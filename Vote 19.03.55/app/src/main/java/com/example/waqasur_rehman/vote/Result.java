package com.example.waqasur_rehman.vote;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.waqasur_rehman.vote.Login.Default;

/**
 * Created by waqas on 19/08/2017.
 */

public class Result extends  BaseActivity {

    private ProgressDialog dialog;

    private ListView listView;
    private result_portal_adapter Adapter;

    private ArrayList<result_portal_fetched> array = new ArrayList<result_portal_fetched>();

    private String  ID;
    private int numberofuser;
    String user;



    protected final String serverURL="//";





    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

        SharedPreferences sharedPreferences = getSharedPreferences("mydata", Context.MODE_PRIVATE);
        user = sharedPreferences.getString("Email", Default);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        listView = (ListView) findViewById(R.id.result_listview);



        Adapter = new result_portal_adapter(this, array);
        listView.setAdapter(Adapter);



        dialog = new ProgressDialog(Result.this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();

        RequestQueue queue = Volley.newRequestQueue(this);


        //Create volley request obj for getting all the question that exist in the database
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(serverURL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                hideDialog();
                //parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        result_portal_fetched question = new result_portal_fetched();
                        question.setquestion(obj.getString("question"));
                        question.setID(obj.getInt("ID"));
                        array.add(question);




                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }
                //Numberofusers();
                //EmployeesVoted();
                Adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonArrayRequest);




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                view.setSelected(true);

                ID = ((TextView)view.findViewById(R.id.result_question_id)).getText().toString();

                    Intent intent = new Intent(Result.this, Result_Detail.class);
                intent.putExtra("ID", ID.toString());
                startActivity(intent);
            }

            @SuppressWarnings("unused")
            public void onClick(View v) {

            }


        });


    }


    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }


    public void hideDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

}

