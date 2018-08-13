package com.example.waqasur_rehman.vote;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by waqas on 31/08/2017.
 */

public class Detail extends  BaseActivity {


    private Detail_Adapter adapter;
String id;
ListView listView;

    String getattending_url = "link to webservice that get the number of people that have selected attending";

    private ArrayList<DetailClass> arrayList = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);


        listView = (ListView)findViewById(R.id.detail_listview);

        adapter = new Detail_Adapter(Detail.this,arrayList);

        listView.setAdapter(adapter);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            id = bundle.getString("meetingid");
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, getattending_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        // response
                        Log.d("Response", response);

                        try {
                            JSONArray data = new JSONArray(response);

                            for (int i = 0; i < data.length(); i++) {

                                JSONObject obj = data.getJSONObject(i);


                                    DetailClass get = new DetailClass();

                                get.setEmployeeName(obj.getString("name"));
                                get.setEmail(obj.getString("email"));


                                arrayList.add(get);
                                }

                            } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();


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
                params.put("meetingid", id.toString());
                return params;
            }
        };
        queue.add(postRequest);

    }

}
