package com.example.waqasur_rehman.vote;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

/**
 * Created by waqas on 22/08/2017.
 */

public class Report extends  BaseActivity {


    PieChart pieChart;

    String id;


    public report_Adapter adapter;

    Inflater inflater;

    ListView listView;
    String emailstring ="";

    private ArrayList<report_class> data = new ArrayList<report_class>();


    protected final String votes = "/add link appropiate php webservice script/"; 
    protected final String getreportdetail = "/add link appropiate php webservice script/"; // get report detail
    Map<String, Float> mapcount = new HashMap<>();

    private ArrayList<String> array = new ArrayList<String>();


    private List<String> keys = new ArrayList<>(mapcount.size());
    private List<Float> values = new ArrayList<>(mapcount.size());
        View view;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pieChart = (PieChart) findViewById(R.id.pie_chart);

        pieChart.setDescription("Votes Result");
        pieChart.setRotationEnabled(true);
        pieChart.setCenterText("Votes Pie Chart");
        pieChart.setHoleRadius(50f);
        pieChart.setTransparentCircleAlpha(0);



        calculateoccurances();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            id = bundle.getString("ID");


        }
        listView = (ListView) findViewById(R.id.report_listview);
        adapter = new report_Adapter(this, data);
        listView.setAdapter(adapter);





        RequestQueue queue1 = Volley.newRequestQueue(this);

        // volley request for getting all the users that have voted for a specific question.

        final StringRequest employeevoted = new StringRequest(Request.Method.POST, votes,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {


                            JSONArray json = new JSONArray(response.toString());

                            for (int i = 0; i < json.length(); i++) {

                                array.add(json.getString(i));
//                                Log.d("Json Value", json.getString(i));
//                                Log.d("Json Value", String.valueOf(array.size()));
                            }
                            calculateoccurances();

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
                params.put("surveyid", id);
                Log.d(" survey id ", id.toString());
                return params;
            }
        };
        queue1.add(employeevoted);




/// volley reguest for all the relevant regarding each employee  e..g what they voted and thier email etc

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, getreportdetail,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        // response
                         Log.d("Response", response);
                        try {
                            JSONArray json = new JSONArray(response.trim());


                            for (int i = 0; i < json.length(); i++) {


                                JSONObject obj =json.getJSONObject(i);

                                report_class getdata = new report_class();
                                getdata.setEmail(obj.getString("employee").trim());
                                getdata.setName(obj.getString("name").trim());
                                getdata.setVote(obj.getString("Voted"));

                                emailstring = getdata.getEmail();
                                data.add(getdata);

                            }

                            sendmessage();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        adapter.notifyDataSetChanged();


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


    }


    public void calculateoccurances() {

        Collections.sort(array);
        String last = null;
        float n = 0;


        //Log.d(" values calculate : ", array.toString());

        for (String w : array) {
            //Log.d("w values", w.toString());

            if (w.equals(last)) {

                n++;

            } else {


                if (last != null) {

                    mapcount.put(last, n);

                }
                last = w;
                n = 1;

            }
            //Log.d("last value", last.toString() +" count "+ String.valueOf(n));
            mapcount.put(last, n);
        }

        //mapcount.values().removeAll(Collections.singleton(null));

        Log.d("Occurance", mapcount.toString() + "\n");

        for (Map.Entry<String, Float> entry : mapcount.entrySet()) {

            keys.add(entry.getKey());
            values.add(entry.getValue());


        }
        // Log.d(" Keys ", keys.toString());
        // Log.d(" values ", values.toString());

        ArrayList<PieEntry> key = new ArrayList<>();

        for (int i = 0; i < values.size(); i++) {

            Float value = ((values.get(i) / Float.valueOf(array.size()) * 100));


            key.add(new PieEntry(value, "(%)"));

        }

        PieDataSet pieDataSet = new PieDataSet(key, "");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueLineColor(9);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.MAGENTA);


        pieDataSet.setColors(colors);
        pieDataSet.setValueTextSize(12f);
        pieDataSet.setValueTextColor(Color.WHITE);
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);


        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                int pos1 = e.toString().indexOf("(sum): ");
                String num = e.toString().substring(pos1 + 7);

                Double num1 = Double.valueOf(num);

                Double val = ((num1 * array.size()) / 100);

                Log.d(" val value ", val.toString());
                String reverted = String.valueOf(val);

                for (int i = 0; i < values.size(); i++) {


                    if (values.get(i) == Float.parseFloat(reverted)) {
                        pos1 = i;
                        break;
                    }
                }
                String Voted = keys.get(pos1);
                Toast.makeText(Report.this, "Number of employee voted " + Voted + " : " + val + "\n" + "Percentage(%):" + num, Toast.LENGTH_LONG).show();
            }


            @Override
            public void onNothingSelected() {

            }
        });


    }




    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void sendmessage(){

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {




                TextView  employee = (TextView)view.findViewById(R.id.id_report);

                Intent intent = new Intent(Report.this, Message.class);

                intent.putExtra("employee",employee.getText().toString());
                intent.putExtra("surveyid", id);
                startActivity(intent);

            }
        });



    }


}




