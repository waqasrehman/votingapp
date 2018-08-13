package com.example.waqasur_rehman.vote;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by WAQAS UR-REHMAN on 12/07/2017.
 */

public class Upload extends BaseActivity{



    protected EditText question,add_option;


    private final String serverURL = "//";


    ListView listView;

    ArrayList<String> optionList;

    ArrayAdapter<String> adapter;
    Button add;


    Switch aSwitch;

    String state ="One Choice";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.upload);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView)findViewById(R.id.list_option);


        add_option = (EditText)findViewById(R.id.Add_option);

        optionList = new ArrayList<String>();
        question = (EditText)findViewById(R.id.question);

        adapter = new ArrayAdapter<String>(this,R.layout.list_view_question,R.id.txtView,optionList);
        listView.setAdapter(adapter);

        aSwitch = (Switch)findViewById(R.id.multi_choice);

            aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                    if (isChecked){

                        state = "Multiple Choice";

                    }
                    else if(!isChecked){


                        state = "One Choice";
                    }
                }
            });



        add=(Button)findViewById(R.id.add_option_button);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    if(add_option.getText().length()==0){


                    }else {
                        String newOption = add_option.getText().toString().toUpperCase();
                        optionList.add(newOption);
                        adapter.notifyDataSetChanged();
                        add_option.setText("");//clear the textfield after entering option
                    }
            }
        });




    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }


    public void onremove(View view) {

        optionList.remove(listView.getPositionForView(view));
        adapter.notifyDataSetChanged();
    }

    public void uploadQustion(View view) {


        if(listView.getCount() == 0){

            Toast.makeText(this,"Please Provide some options",Toast.LENGTH_SHORT).show();
        }
        if (listView.getCount() >0 && listView.getCount() < 3 ){

            Toast.makeText(this, "Please Enter Atleast Three Options ",Toast.LENGTH_SHORT).show();
        }


        // send the values to the server to be stored

        RequestQueue queue = Volley.newRequestQueue(this);


        final StringRequest postRequest = new StringRequest(Request.Method.POST, serverURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        // response
                        Log.d("Response", response);

                        Toast.makeText(Upload.this,state+" Survey Uploaded Successfully",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Upload.this, MainActivity.class);
                        startActivity(intent);


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

                JSONArray array = new JSONArray(optionList);



                params.put("question",question.getText().toString());
                Log.d("Question", question.getText().toString());
                params.put("Voteoption",array.toString());
                params.put("state",state);
                Log.d("data", array.toString());






                return params;

            }
        };
        queue.add(postRequest);
    }


    }

