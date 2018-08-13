package com.example.waqasur_rehman.vote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by waqas on 29/08/2017.
 */

public class Detail_Adapter extends BaseAdapter {

    private LayoutInflater inflater; // instance of layout inflater
    private Detail detail;// instance of Activity
    ArrayList<DetailClass> fetch; // list of item


    public Detail_Adapter(Detail activity, ArrayList<DetailClass> detail) { // constructor
        this.detail = activity;
        this.fetch = detail;

    }




    @Override
    public int getCount() {
        return fetch.size();
    }

    @Override
    public Object getItem(int i) {
        return fetch.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        if (inflater == null) {
            inflater = (LayoutInflater) detail.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.detail_custom, null);
        }

        TextView Name = (TextView)convertView.findViewById(R.id.NameID);
        TextView Email  = (TextView)convertView.findViewById(R.id.EmailID);

        DetailClass get = fetch.get(i);

        Name.setText(get.getEmployeeName());
        Email.setText(get.getEmail());

        return convertView;
    }
}
