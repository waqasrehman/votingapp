package com.example.waqasur_rehman.vote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class result_portal_adapter extends BaseAdapter {
    private LayoutInflater inflater; // instance of layout inflater
    private Result result;// instance of Activity
    ArrayList<result_portal_fetched> question_fetched; // list of item


    public result_portal_adapter(Result activity, ArrayList<result_portal_fetched> question) { // constructor
        this.result = activity;
        this.question_fetched = question;

    }

    @Override
    public int getCount() {
        return question_fetched.size();
    }

    @Override
    public Object getItem(int position) {
        return question_fetched.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) result.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custome_layout_result, null);
        }


        TextView Question = (TextView) convertView.findViewById(R.id.question_result);
        TextView ID = (TextView)convertView.findViewById(R.id.result_question_id);
        result_portal_fetched question = question_fetched.get(position);
        //
        Question.setText(question.getquestion().toUpperCase());
        ID.setText(String.valueOf(question.getID()));

        return convertView;
    }

}
