package com.example.waqasur_rehman.vote;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

/**
 * Created by waqas on 23/07/2017.
 */

public class Schedule_Planner extends BaseActivity {

    TextView date;

    TextView time;
    private TimePickerDialog.OnTimeSetListener timePickerDialog;

    protected final String storeSchedule_url="/add link appropiate php webservice script/";

    private ProgressDialog dialog;

    EditText name;
    EditText desc;

    Button Date;
    Button Time, createSchedule , viewschedule;

        Context context;
    int year, month, day, hour, min;

    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_planner);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;

        date =(TextView)findViewById(R.id.Date_);
        time = (TextView)findViewById(R.id.Time_);

        Date = (Button)findViewById(R.id.Date_button);
        Time =(Button)findViewById(R.id.time_button);
        createSchedule =(Button)findViewById(R.id.create_);

        name  = (EditText)findViewById(R.id.Name_);
        desc =(EditText)findViewById(R.id.Desc_);

        viewschedule = (Button)findViewById(R.id.viewschedule);

        viewschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Schedule_Planner.this, Attending.class);
                startActivity(intent);
            }
        });

        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();

                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dialog = new DatePickerDialog(Schedule_Planner.this, android.R.style.Theme_Holo_Dialog_MinWidth,dateSetListener,year,month,day);
                dialog.show();


            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {



                month = month +1;

                String date1 = month + "/"+ day + "/"+ year;

                date.setText(date1);


            }
        };

        Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar =Calendar.getInstance();
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                min = calendar.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(Schedule_Planner.this,android.R.style.Theme_Holo_Dialog_MinWidth,timePickerDialog, hour,min, android.text.format.DateFormat.is24HourFormat(Schedule_Planner.this));
                dialog.show();


            }
        });

        timePickerDialog = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int Hour, int MIN) {


                time.setText(Hour +":"+MIN);

            }
        };

        createSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new ProgressDialog(Schedule_Planner.this);
                dialog.setMessage("Creating Schedule......");
                dialog.show();
                storeschedules();
            }
        });

    }
public void storeschedules(){



    RequestQueue queue = Volley.newRequestQueue(Schedule_Planner.this);


    final StringRequest postRequest = new StringRequest(Request.Method.POST, storeSchedule_url,
            new Response.Listener<String>() {


                @Override
                public void onResponse(String response) {

                    hideDialog();
                    // response
                    Log.d("Response", response);

                    Toast.makeText(Schedule_Planner.this,"Schedule Created Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Schedule_Planner.this, MainActivity.class);
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
                params.put("Date", date.getText().toString());
                params.put("Time", time.getText().toString());
                params.put("Name", name.getText().toString());
                params.put("Desc", desc.getText().toString());

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
