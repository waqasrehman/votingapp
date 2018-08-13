package com.example.waqasur_rehman.vote;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

import static com.example.waqasur_rehman.vote.Login.Default;

/**
 * Created by waqas on 29/07/2017.
 */

public class Cast extends BaseActivity {

    String id;
    String serverURL = "link to appropiate serverURL";
    String storevotes_url = " link to appropiate webservice that store votes";

   private  String user,name;
   private ListView listView;

    private EditText question;
    private TextView state;

   private  ArrayList<OptionClass> optioncontains = new ArrayList<>();

   private  OptionAdapter adapter;



ArrayList<String> array = new ArrayList<>();




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cast);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPreferences = getSharedPreferences("mydata", Context.MODE_PRIVATE);
        user = sharedPreferences.getString("Email", Default);
        name = sharedPreferences.getString("name",Default);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            id = bundle.getString("ID");
        }

        question = (EditText)findViewById(R.id.question);

        listView = (ListView)findViewById(R.id.Display_option_listview);

        adapter = new OptionAdapter(Cast.this,optioncontains);

        listView.setAdapter(adapter);

        state = (TextView)findViewById(R.id.question_state);

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, serverURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        // response
                        Log.d("Response", response);

                        try {
                            JSONArray data = new JSONArray(response);
                            String newOption="";

                            for (int i = 0; i < data.length(); i++) {



                                JSONObject obj = data.getJSONObject(i);

                                TextView question = (TextView) findViewById(R.id.survey_question);
                                question.setText(obj.getString("question").toUpperCase());
                                state.setText(obj.getString("state"));



                                JSONArray array = obj.getJSONArray("Voteoption");
                                Log.d(" json array", array.toString());

                                for(int j =0;j<array.length();j++) {

                                    Log.d("Data", array.getString(j).toString());

                                     newOption = array.getString(j).toString().toUpperCase();
                                    optioncontains.add(new OptionClass(newOption,false));



                                }

                            }
                            adapter.notifyDataSetChanged();


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




    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    public void onsubmit(View view) {

        array.clear();
        for(OptionClass op : adapter.getbox()){


            if(op.box){

              array.add(op.option);

            }

        }
        Log.d("Data of Array is  ", array.toString());


        if(state.getText().equals("One Choice")){

            if(array.size() ==1){


                storevotes();

            }else{


                Toast.makeText(Cast.this,"Please choose only one option", Toast.LENGTH_LONG).show();
            }


        }

        if(state.getText().equals("Multiple Choice")){

            if(array.size() ==2){


                storevotes();

            }else{


                Toast.makeText(Cast.this,"Please choose only Two option", Toast.LENGTH_SHORT).show();
            }


        }

    }


    public void storevotes(){

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, storevotes_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        // response
                        Log.d("Response", response);

                        // if casting is successfull return to vote activity

                        Toast.makeText(Cast.this, "Voted Registered Successfully",Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Cast.this, Vote.class);
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
                params.put("surveyid", id.toString());
                params.put("employee", user.toString());
                params.put("name",name.toString());
                params.put("Voted", array.toString());








                return params;
            }
        };
        queue.add(postRequest);


    }
}


