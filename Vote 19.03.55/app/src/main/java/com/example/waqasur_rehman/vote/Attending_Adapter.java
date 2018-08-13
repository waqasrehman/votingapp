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

public class Attending_Adapter extends BaseAdapter {

    private LayoutInflater inflater; // instance of layout inflater
    private Attending attending;// instance of Activity
    ArrayList<AttendingClass> fetch; // list of item


    public Attending_Adapter(Attending activity, ArrayList<AttendingClass> detail) { // constructor
        this.attending = activity;
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
            inflater = (LayoutInflater) attending.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custome_attending_layout, null);
        }

        TextView Name = (TextView)convertView.findViewById(R.id.meetingName);

        AttendingClass get = fetch.get(i);

        Name.setText(get.getName());

        return convertView;
    }
}
