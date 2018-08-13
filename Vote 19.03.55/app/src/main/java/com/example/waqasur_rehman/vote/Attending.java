package com.example.waqasur_rehman.vote;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by waqas on 29/08/2017.
 */

public class Attending extends  BaseActivity {
    String meetingid;

    private ListView listView;
    private ProgressDialog dialog;

    protected final String getconfirmmeeting="link to appropiate webservice that provide data about who is attending the meeting";

    private ArrayList<AttendingClass> arrayList = new ArrayList<>();

    ArrayList<AttendingClass> result = new ArrayList<>(arrayList);

    private  Attending_Adapter  adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attending);

        listView = (ListView)findViewById(R.id.attending_listview);

        adapter = new Attending_Adapter(Attending.this,result);

        listView.setAdapter(adapter);

        dialog = new ProgressDialog(Attending.this);
        dialog.setMessage("Loading...");
        dialog.show();

        RequestQueue queue = Volley.newRequestQueue(this);


        //Create volley request obj
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(getconfirmmeeting, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                hideDialog();
                //parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        AttendingClass get = new AttendingClass();
                        get.setName(obj.getString("meetingname"));
                        meetingid = obj.getString("meetingid");


                        arrayList.add(get);
                        Set<String> titles = new HashSet<String>();

                        for( AttendingClass name : arrayList ) {
                            if( titles.add( name.getName())) {
                                    result.add(name);
                            }


                        }






                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }


                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            Intent intent = new Intent(Attending.this, Detail.class);
                                intent.putExtra("meetingid", meetingid);
                            startActivity(intent);

                        }
                    });
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonArrayRequest);




    }


    public void hideDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }



}
