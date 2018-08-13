package com.example.waqasur_rehman.vote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by waqas on 17/08/2017.
 */

public class OptionAdapter  extends BaseAdapter {


    private Context ctx;

   private  LayoutInflater inflater;

     private ArrayList<OptionClass> options;


    public OptionAdapter(Context context, ArrayList<OptionClass> option){


        ctx = context;
        options= option;


    }


    @Override
    public int getCount() {


        return  options.size();
    }

    @Override
    public Object getItem(int position) {
        return options.get(position);
    }

    @Override
    public long getItemId(int position) {


        return position;
    }

    @Override
    public View getView(int position, View Contextview, ViewGroup parent) {

        View convertview = Contextview;

        if (inflater == null) {
            inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertview == null) {
            convertview = inflater.inflate(R.layout.list_view_option, null);
        }


        OptionClass choice = getoption(position);

        ((TextView)convertview.findViewById(R.id.option_listview_text)).setText(choice.option);
        CheckBox checkBox =convertview.findViewById(R.id.check_box);


        checkBox.setOnCheckedChangeListener(mychecklist);
        checkBox.setTag(position);
        checkBox.setChecked(choice.box);

        return convertview;
    }



    OptionClass getoption(int position){

        return ((OptionClass)getItem(position));
    }


    ArrayList<OptionClass> getbox() {


        ArrayList<OptionClass> box = new ArrayList<>();

        for (OptionClass op : options) {

            if (op.box) {
                box.add(op);
            }else if (!op.box){

                box.remove(op);
            }

        }

            return box;

        }

            CompoundButton.OnCheckedChangeListener mychecklist = new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonview, boolean isChecked) {

                    getoption((Integer)buttonview.getTag()).box=isChecked;

                }
            };



}
