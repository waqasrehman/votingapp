package com.example.waqasur_rehman.vote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by waqas on 29/08/2017.
 */

public class Schedule_Adapter extends BaseAdapter{

    private LayoutInflater inflater; // instance of layout inflater
    private Schedular schedular;// instance of Activity
    ArrayList<Schedule_fetched> schedule_fetched;

    public Schedule_Adapter(Schedular activity, ArrayList<Schedule_fetched> schedule) { // constructor
        this.schedular = activity;
        this.schedule_fetched = schedule;

    }

    @Override
    public int getCount() {
        return schedule_fetched.size();
    }

    @Override
    public Object getItem(int i) {
        return schedule_fetched.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {


        if (inflater == null) {
            inflater = (LayoutInflater) schedular.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_display_schedules, null);
        }

        TextView name = (TextView) convertView.findViewById(R.id.Name_Meeting);
        TextView date = (TextView)convertView.findViewById(R.id.Date_Meeting);
        TextView meetingid = (TextView)convertView.findViewById(R.id.meetingID);


        Schedule_fetched fetched = schedule_fetched.get(i);

        name.setText(fetched.getName());
        date.setText(fetched.getDate());
        meetingid.setText(fetched.getid());


        return convertView;
    }
}
