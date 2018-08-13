package com.example.waqasur_rehman.vote;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by waqas on 25/08/2017.
 */

public class Email extends  BaseActivity {


    Session session = null;
    ProgressDialog dialog =null;

    Context context;

    EditText recipe, subject, message;

    String recipient, sub, tmessage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            context= this;


        final Button send = (Button)findViewById(R.id.SendMessage_);

        recipe = (EditText)findViewById(R.id.emailvalue_);
        subject = (EditText)findViewById(R.id.subjectvalue_);
        message = (EditText)findViewById(R.id.Message_);



        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                recipient = recipe.getText().toString();
                sub = subject.getText().toString();
                tmessage= message.getText().toString();


                Properties properties = new Properties();

                properties.put("mail.smtp.host","smtp.gmail.com");
                properties.put("mail.smtp.socketFactory.port","465");
                properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
                properties.put("mail.smtp.auth","true");
                properties.put("mail.smtp.port","465");

                session = Session.getDefaultInstance(properties, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("email you wanna sent email from ","password to that email ");

                    }
                });

                dialog = ProgressDialog.show(context," ", "Sending Mail...", true);

                RetreiveTask task = new RetreiveTask();

                task.execute();


            }

             class RetreiveTask extends  AsyncTask<String, Void,String>{
                 @Override
                 protected String doInBackground(String... strings) {



                     try{


                         Message msg = new MimeMessage(session);
                         msg.setFrom(new InternetAddress("email you wanna sent email from"));

                         msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(recipient));
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
                     Toast.makeText(Email.this," Message Sent", Toast.LENGTH_SHORT).show();
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


