package com.example.waqasur_rehman.vote;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import java.util.List;

/**
 * Created by waqas on 29/07/2017.
 */

public class Schedular extends BaseActivity {

    private Schedule_Adapter adapter;
    ListView listView;
    private ProgressDialog dialog;

    String meetingid;
    private ArrayList<Schedule_fetched> arrayList = new ArrayList<Schedule_fetched>();

    protected final String getschedule_url="add link appropiate php webservice script";// get schedule

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedular);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.scheduler_listview);
        adapter = new Schedule_Adapter(this, arrayList);
        listView.setAdapter(adapter);

        dialog = new ProgressDialog(Schedular.this);
        dialog.setMessage("Loading...");
        dialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);


        //Create volley request obj
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(getschedule_url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                hideDialog();
                //parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Schedule_fetched fetched = new Schedule_fetched();
                        fetched.setName(obj.getString("Name"));
                        fetched.setDate(obj.getString("Date"));
                         fetched.setid( obj.getString("MeetingID"));
                        arrayList.add(fetched);

                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            meetingid= ((TextView)view.findViewById(R.id.meetingID)).getText().toString();


                            Intent intent = new Intent(Schedular.this, schedule_detail.class );

                            intent.putExtra("meetingid", meetingid.toString());

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
