package com.example.waqasur_rehman.vote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class display_question_Adapter extends BaseAdapter {
    private LayoutInflater inflater; // instance of layout inflater
    private Vote vote;// instance of Activity
    ArrayList<question_fetched> question_fetched; // list of item


    public display_question_Adapter(Vote activity, ArrayList<question_fetched> question) { // constructor
        this.vote = activity;
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
            inflater = (LayoutInflater) vote.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_layout, null);
        }

        TextView Question = (TextView) convertView.findViewById(R.id.question);
        TextView ID = (TextView)convertView.findViewById(R.id.question_id);

        question_fetched question = question_fetched.get(position);
        //
        Question.setText(question.getquestion().toUpperCase());
        ID.setText(String.valueOf(question.getID()));

        return convertView;
    }

}
