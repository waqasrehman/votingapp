package com.example.waqasur_rehman.vote;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by waqas on 29/08/2017.
 */

public class Message extends BaseActivity {

    Session session = null;
    ProgressDialog dialog =null;

    Context context;

    EditText recipe, subject, message;

    String recipient, sub, tmessage;

String email, id;

    protected final String getquestion = "add link appropiate php webservice script"; // script to get the question user need to vote for


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendmessage);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context= this;


        final Button send = (Button)findViewById(R.id.SendMessagebutton_);




        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            email = bundle.getString("employee");
            id = bundle.getString("surveyid");

        }

        recipe = (EditText)findViewById(R.id.emailvalue_);
        subject = (EditText)findViewById(R.id.subjectvalue_);
        message = (EditText)findViewById(R.id.messagevalue_);

        recipe.setText(email);



        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, getquestion,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        // response
                        Log.d("Response", response);
                        try {
                            JSONArray json = new JSONArray(response.trim());


                            for (int i = 0; i < json.length(); i++) {


                                JSONObject obj =json.getJSONObject(i);

                                subject.setText("Regarding Voting for survey" + ", "+obj.getString("question"));

                                Log.d(" subject", subject.getText().toString());

                            }



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
                params.put("surveyid", id.toString());
                return params;
            }
        };
        queue.add(postRequest);




        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                recipient = recipe.getText().toString();
               // sub = subject.setText("");
                sub= subject.getText().toString();
                tmessage= message.getText().toString();

                Properties properties = new Properties();

                properties.put("mail.smtp.host","smtp.gmail.com");
                properties.put("mail.smtp.socketFactory.port","465");
                properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
                properties.put("mail.smtp.auth","true");
                properties.put("mail.smtp.port","465");

                class GMailAuthenticator extends Authenticator {
                    String user;
                    String pw;
                    public GMailAuthenticator (String username, String password)
                    {
                        super();
                        this.user = username;
                        this.pw = password;
                    }
                    public PasswordAuthentication getPasswordAuthentication()
                    {
                        return new PasswordAuthentication(user, pw);
                    }
                }


                session = Session.getDefaultInstance(properties, new GMailAuthenticator("officialvotingapp@gmail.com","Khan@1994") {

                });

                dialog = ProgressDialog.show(context," ", "Sending Message...", true);

                RetreiveTask task = new RetreiveTask();

                task.execute();


            }

            class RetreiveTask extends AsyncTask<String, Void,String> {
                @Override
                protected String doInBackground(String... strings) {



                    try{


                        javax.mail.Message msg = new MimeMessage(session);
                        msg.setFrom(new InternetAddress("officialvotingapp@gmail.com"));

                        msg.setRecipients(javax.mail.Message.RecipientType.TO,InternetAddress.parse(recipient));
                        msg.setSubject(sub);
                        msg.setContent(tmessage, "text/html; charset=utf-8");
                        Transport.send(msg);
                    } catch (AddressException e) {
                        e.printStackTrace();
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(String s) {


                    dialog.dismiss();
                    recipe.setText("");
                    subject.setText("");
                    message.setText("");
                    Toast.makeText(Message.this," Message Sent", Toast.LENGTH_SHORT).show();

                    Intent  intent = new Intent(Message.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

        });




    }


    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }



}
