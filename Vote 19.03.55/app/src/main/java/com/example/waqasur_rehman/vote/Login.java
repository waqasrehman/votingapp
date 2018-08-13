package com.example.waqasur_rehman.vote;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
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
 * Created by WAQAS UR-REHMAN on 11/07/2017.
 */

public class Login extends BaseActivity {

    public static final String Default = "N/A"; // hold null value;

    protected EditText Email, Password;

    protected String email, password;

    private final String ServerURL ="/add link appropiate php webservice script/"; // link to serverURL, scripts are provided

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Email = (EditText)findViewById(R.id.email);
        Password = (EditText)findViewById(R.id.password);


        SharedPreferences sharedPreferences = getSharedPreferences("mydata", Context.MODE_PRIVATE);
        String  user = sharedPreferences.getString("Email", Default);
        String usertype = sharedPreferences.getString("user",Default);


        if (user != Default && usertype.equals("employee")) { // if sharedpreference is not empty redirect to main activity and display message



            Intent intent = new Intent(this, Employee_main.class);
            startActivity(intent);
            finish();
            Toast.makeText(this, "Welcome Back " + user.toString(), Toast.LENGTH_LONG).show();



        }

        if (user != Default && usertype.equals("Admin")) { // if sharedpreference is not empty redirect to main activity and display message



            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(this, "Welcome Back " + user.toString(), Toast.LENGTH_LONG).show();





        }



    }

    public void login(View view) {


            email = Email.getText().toString();
            password= Password.getText().toString();

        if(email.equals("")|| password.equals("")){

            Toast.makeText(Login.this, "Email or Password Required",Toast.LENGTH_LONG).show();
            return;

        }
        if ( email.length()<=1 || password.length() <=1){

            Toast.makeText(Login.this, "Email or Password must be more then one Character", Toast.LENGTH_LONG).show();
            return;
        }



            AsyncDataClass asyncRequestObject = new AsyncDataClass();
            asyncRequestObject.execute(ServerURL, email, password);



            }

    private class AsyncDataClass extends AsyncTask<String,Void,String>{


        @Override
        protected String doInBackground(String... params) {
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters,5000);
            HttpConnectionParams.setSoTimeout(httpParameters,5000);


            HttpClient httpClient = new DefaultHttpClient(httpParameters);
            HttpPost httpPost = new HttpPost(params[0]);
            String jResult="";

            try{

                List<NameValuePair>credidentialList = new ArrayList<NameValuePair>(2);
                credidentialList.add(new BasicNameValuePair("email",params[1]));
                credidentialList.add(new BasicNameValuePair("password",params[2]));
                httpPost.setEntity(new UrlEncodedFormEntity(credidentialList));


                HttpResponse response = httpClient.execute(httpPost);
                jResult = inputStreamToString(response.getEntity().getContent()).toString();

            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return jResult;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result.equals("") || result ==null){
                Toast.makeText(Login.this, "Connection to Server Failed", Toast.LENGTH_LONG).show();
                return;
            }
            int response = ParsedJsonObject(result);
            if(response==0){

                Toast.makeText(Login.this, "Invalid Email or Password",Toast.LENGTH_LONG).show();
                return;
            }
            if(response == 1){

                SharedPreferences sharedPreferences = getSharedPreferences("mydata", getApplicationContext().MODE_PRIVATE);
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.putBoolean("mydata",true);
                editor.putString("Email",Email.getText().toString());
                editor.putString("user","employee");

                editor.commit();

                    Toast.makeText(Login.this, " Welcome to Voting App", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Login.this, Employee_main.class);
                    Login.this.startActivity(intent);
                    finish();


                }

                if( response ==2){


                    SharedPreferences sharedPreferences = getSharedPreferences("mydata", getApplicationContext().MODE_PRIVATE);
                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    editor.putBoolean("mydata",true);
                    editor.putString("Email",Email.getText().toString());
                    editor.putString("user","Admin");
                    editor.commit();
                    Toast.makeText(Login.this, " Welcome to Voting App", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    Login.this.startActivity(intent);
                    finish();


                }


            }

        }



    private  StringBuilder inputStreamToString(InputStream content){


        String line ="";
        StringBuilder response = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(content));
        try {

            while ((line = br.readLine()) !=null){
                response.append(line);
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private int ParsedJsonObject (String result){

        JSONObject resposeObject =null;
        int returnedResult =0;

        try{

            resposeObject = new JSONObject(result);

                returnedResult = resposeObject.getInt("success");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnedResult;


    }

    public void Register(View view) {

        Intent intent = new Intent(this, Register.class);
        Login.this.startActivity(intent);
        finish();

    }
}
