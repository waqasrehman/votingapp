package com.example.waqasur_rehman.vote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class result_detail_Adapter extends BaseAdapter {
    private LayoutInflater inflater; // instance of layout inflater
    private Result_Detail result_detail;// instance of Activity
    ArrayList<result_detail_fetched> detail_fetched; // list of item


    public result_detail_Adapter(Result_Detail activity, ArrayList<result_detail_fetched> detail) { // constructor
        this.result_detail = activity;
        this.detail_fetched = detail;

    }

    @Override
    public int getCount() {
        return detail_fetched.size();
    }

    @Override
    public Object getItem(int position) {
        return detail_fetched.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) result_detail.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.result_detail_custom_layout,null);
        }


        TextView Name  = (TextView) convertView.findViewById(R.id.Employee_name);
        TextView Email = (TextView)convertView.findViewById(R.id.Employee_email);
        result_detail_fetched detail = detail_fetched.get(position);
        //
        Name.setText(detail.getName().toUpperCase());
        Email.setText(detail.Email);

        return convertView;
    }

}
