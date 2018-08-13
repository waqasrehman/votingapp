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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import ezvcard.Ezvcard;

import static com.example.waqasur_rehman.vote.Login.Default;

/**
 * Created by waqas on 29/07/2017.
 */

public class QR_reader extends BaseActivity {


    String question;
    String options;
    String state;
    private  String user,name;
    String storevotes_url = "/add link appropiate php webservice script/"; // script that stores users that have voted

TextView question_qr,state_qr;
    ListView listView;
    private  OptionAdapter adapter;
    private  ArrayList<OptionClass> optioncontains = new ArrayList<>();
    ArrayList<String> array = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_reader);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPreferences = getSharedPreferences("mydata", Context.MODE_PRIVATE);
        user = sharedPreferences.getString("Email", Default);
        name = sharedPreferences.getString("name",Default);

        IntentIntegrator  intentIntegrator = new IntentIntegrator(this);

        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        intentIntegrator.setPrompt("Scan");
        intentIntegrator.setCameraId(0);
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setBarcodeImageEnabled(false);
        intentIntegrator.initiateScan();



        question_qr = (TextView)findViewById(R.id.survey_question_qr);

        listView = (ListView)findViewById(R.id.Display_option_listview_qr);

        adapter = new OptionAdapter(QR_reader.this,optioncontains);

        listView.setAdapter(adapter);

        state_qr = (TextView)findViewById(R.id.question_state_qr);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result !=null){

            if(result.getContents()==null){


                Toast.makeText(this, " you cancelled the scanning ",Toast.LENGTH_SHORT).show();

            }else{

                String newOption="";

                try {
                    JSONArray jsonArray = new JSONArray(result.getContents());


                    for(int i=0;i<jsonArray.length();i++){

                        JSONObject  object  = jsonArray.getJSONObject(i);

                        question = object.getString("question");
                        //options = object.getString("options");

                        question_qr.setText(question);

                        state = object.getString("state");

                        state_qr.setText(state);

                        //Log.d("question", question);
                        //Log.d("option", options);
                        //Log.d("state", state);



                        JSONArray array = object.getJSONArray("options");
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





                //Log.d("state", result.getContents());


                //Toast.makeText(this,result.getContents(),Toast.LENGTH_LONG).show();
            }

        }else{

            super.onActivityResult(requestCode,resultCode,data);
        }

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }


    public void onsubmit_qr(View view) {

        array.clear();
        for(OptionClass op : adapter.getbox()){


            if(op.box){

                array.add(op.option);

            }

        }
        Log.d("Data of Array is  ", array.toString());


        if(state_qr.getText().equals("One Choice")){

            if(array.size() ==1){


                storevotes();

            }else{


                Toast.makeText(this,"Please choose only one option", Toast.LENGTH_LONG).show();
            }


        }

        if(state_qr.getText().equals("Multiple Choice")){

            if(array.size() ==2){


                storevotes();

            }else{


                Toast.makeText(this,"Please choose only Two option", Toast.LENGTH_SHORT).show();
            }


        }

    }

    private void storevotes() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, storevotes_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        // response
                        Log.d("Response", response);

                        // if casting is successfull return to vote activity

                        Toast.makeText(QR_reader.this,"Voted Registered Successfully",Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(QR_reader.this, Employee_main.class);
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


                Random rand = new Random();

                int id = rand.nextInt(100)+1;



                params.put("surveyid", String.valueOf(id));
                params.put("employee", user.toString());
                params.put("name",name.toString());
                params.put("Voted", array.toString());








                return params;
            }
        };
        queue.add(postRequest);


    }
}
