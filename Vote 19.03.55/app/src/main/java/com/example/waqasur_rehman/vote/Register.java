package com.example.waqasur_rehman.vote;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WAQAS UR-REHMAN on 12/07/2017.
 */

public class Register extends BaseActivity{



        protected EditText name, password, email;
        protected String Email,Password, Name;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private final String serverURL = "/add link appropiate php webservice script/";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.register);


        name =(EditText)findViewById(R.id.Name_);
        email = (EditText)findViewById(R.id.email_register);
        password = (EditText)findViewById(R.id.password_register);




    }


    public void OnSubmit(View view) {

        Name = name.getText().toString();
        Password = password.getText().toString();
        Email = email.getText().toString();


        if (Name.equals("") || Password.equals("") || Email.equals("")){

            Toast.makeText(Register.this, "Please fill in the required fields",Toast.LENGTH_LONG).show();

        }else
        if(Name.length() <3){

            Toast.makeText(Register.this,"Name must be more then 3 character",Toast.LENGTH_LONG ).show();

        }else
        if(Password.length() <5){
            Toast.makeText(Register.this,"Password must be more then 5 character",Toast.LENGTH_LONG ).show();


        }else
        if(Email.length() <6){
            Toast.makeText(Register.this,"Email must be more then 5 character",Toast.LENGTH_LONG ).show();


        }else
        if (!Patterns.EMAIL_ADDRESS.matcher(Email.toString()).matches()){
            Toast.makeText(Register.this,"Please Enter a Valid Email Address",Toast.LENGTH_LONG ).show();
        }else{

            AsyncDataClass asynDataClass = new AsyncDataClass();
            asynDataClass.execute(serverURL, Name,Password,Email);

        }



    }

    private class AsyncDataClass extends AsyncTask<String, Void, String>{


        @Override
        protected String doInBackground(String... params) {

            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
            HttpConnectionParams.setSoTimeout(httpParameters, 5000);

            HttpClient httpClient = new DefaultHttpClient(httpParameters);
            HttpPost httpPost = new HttpPost(params[0]);

            String jsonpacket = "";

            try{
                List<NameValuePair> credidentiallist = new ArrayList<NameValuePair>(2);
                credidentiallist.add(new BasicNameValuePair("name", params[1]));
                credidentiallist.add(new BasicNameValuePair("password", params[2]));
                credidentiallist.add(new BasicNameValuePair("email", params[3]));
                httpPost.setEntity(new UrlEncodedFormEntity(credidentiallist));

                HttpResponse response = httpClient.execute(httpPost);
                jsonpacket = inputStreamToString (response.getEntity().getContent()).toString();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return jsonpacket;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            if (result.equals("") ||result == null){

                Toast.makeText(Register.this, "Connection to the server failed",Toast.LENGTH_LONG).show();
                return;
            }

            int jsonresponse = ParsedJsonObject(result);
            if(jsonresponse ==0){

                Toast.makeText(Register.this,"Invalid information Entered, Please Try Again ",Toast.LENGTH_LONG).show();
                return;
            }

            if(jsonresponse ==1){

                Toast.makeText(Register.this," Registeration Successfull",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
                finish();


            }
        }
    }

    private StringBuilder inputStreamToString(InputStream content){

        String line ="";

        StringBuilder response = new StringBuilder();

        BufferedReader br =new BufferedReader(new InputStreamReader(content));
        try{


            while ((line = br.readLine()) !=null){

                response.append(line);

            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;

    }

    private int ParsedJsonObject(String result){

        JSONObject responseObject = null;

        int response =0;

        try{

            responseObject = new JSONObject(result);
            response = responseObject.getInt("success");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }
}
