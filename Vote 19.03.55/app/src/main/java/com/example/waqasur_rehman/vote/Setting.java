package com.example.waqasur_rehman.vote;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import static com.example.waqasur_rehman.vote.Login.Default;

/**
 * Created by waqas on 29/07/2017.
 */

public class Setting extends BaseActivity {


    EditText current, newpass;


    String user, curpass, npass;

    TextView email_user;

    protected final String changepass_url="add link appropiate php webservice script ";

    private ProgressDialog dialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPreferences = getSharedPreferences("mydata", Context.MODE_PRIVATE);
        user = sharedPreferences.getString("Email", Default);


        current = (EditText)findViewById(R.id.current_pass);
        newpass = (EditText)findViewById(R.id.newpass);

        final Button changepass = (Button)findViewById(R.id.change_pass_button);

        email_user = (TextView)findViewById(R.id.email_user);
        email_user.setText(user);

        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog = new ProgressDialog(Setting.this);
                dialog.setMessage("Sending Request......");
                dialog.show();

                curpass = current.getText().toString();
                npass= newpass.getText().toString();


                    changepass();

            }


        });





    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    private void changepass(){

        RequestQueue queue = Volley.newRequestQueue(Setting.this);


        StringRequest postRequest = new StringRequest(Request.Method.POST, changepass_url,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {

                            hideDialog();
                        // response
                        Log.d("Response", response);

                            Toast.makeText(Setting.this," Password changed Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Setting.this, Employee_main.class);
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
                params.put("employee", user);
                params.put("currentpass",curpass);
                params.put("newpass", npass);
                return params;
            }
        };
        queue.add(postRequest);

    }
    public void hideDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

}
