package com.example.waqasur_rehman.vote;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import java.util.zip.Inflater;

import static com.example.waqasur_rehman.vote.Login.Default;

/**
 * Created by waqas on 23/07/2017.
 */

public class Vote extends BaseActivity {

    private ProgressDialog dialog;

    private ListView listView;
    private display_question_Adapter adapter;

    private ArrayList<question_fetched> array = new ArrayList<question_fetched>();
    Inflater inflater;

   String  ID;

    String user;

    int employeevoted;
    Boolean exist = false;




    protected final String serverURL="//";
    protected final String uservoted_url="//";



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vote);

        SharedPreferences sharedPreferences = getSharedPreferences("mydata", Context.MODE_PRIVATE);
        user = sharedPreferences.getString("Email", Default);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        listView = (ListView) findViewById(R.id.display_Survey);
        adapter = new display_question_Adapter(this, array);
        listView.setAdapter(adapter);



        dialog = new ProgressDialog(Vote.this);
        dialog.setMessage("Loading...");
        dialog.show();

        RequestQueue queue = Volley.newRequestQueue(this);


        //Create volley request obj
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(serverURL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                hideDialog();
                //parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        question_fetched question = new question_fetched();
                        question.setquestion(obj.getString("question"));
                        question.setID(obj.getInt("ID"));
                        array.add(question);
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
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
                    // get the ID of the textview every time listview it clicked.
                ID = ((TextView)view.findViewById(R.id.question_id)).getText().toString();

                checkforVotes(view);

            }

            @SuppressWarnings("unused")
            public void onClick(View v) {

            }


        });


    }

    private void checkforVotes(final View v) {


        RequestQueue queue = Volley.newRequestQueue(this);


        StringRequest postRequest = new StringRequest(Request.Method.POST, uservoted_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        // response
                        Log.d("Response", response);

                        if(response.trim().equals("Voted")){


                            Toast.makeText(Vote.this, "You have Already voted for this",Toast.LENGTH_SHORT).show();

                            RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.relative_layout);
                            layout.setBackgroundColor(Color.parseColor("#d2f9f6"));



                        }
                        if(response.trim().equals("NotVoted")){

                            Intent intent = new Intent(Vote.this, Cast.class);
                            intent.putExtra("ID", ID);
                            startActivity(intent);

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
                params.put("surveyid",ID);
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




    public void hideDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

}
