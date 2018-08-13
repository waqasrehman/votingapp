package com.example.waqasur_rehman.vote;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by waqas on 25/08/2017.
 */

public class report_Adapter extends BaseAdapter {

    private LayoutInflater inflater; // instance of layout inflater
    private Report report;// instance of Activity
    ArrayList<report_class> data_fetched; // list of item


    public report_Adapter(Report activity, ArrayList<report_class> data) { // constructor
        this.report = activity;
        this.data_fetched = data;

        Log.d(" adapter data ", data.toString());

    }


    @Override
    public int getCount() {
        return data_fetched.size();
    }

    @Override
    public Object getItem(int i) {
        return data_fetched.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {


        if (inflater == null) {
            inflater = (LayoutInflater) report.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.report_custom_layout, null);
        }

        TextView Name = (TextView)convertView.findViewById(R.id.name_report);
        TextView ID = (TextView)convertView.findViewById(R.id.id_report);
        TextView vote = (TextView)convertView.findViewById(R.id.voted_report);


        report_class  data   =data_fetched.get(i);

        Name.setText(data.getName());
        ID.setText(data.getEmail());
        vote.setText(data.getVote());

        return convertView;
    }
}
