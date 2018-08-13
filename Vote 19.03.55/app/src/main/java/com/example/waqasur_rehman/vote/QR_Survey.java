package com.example.waqasur_rehman.vote;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by waqas on 23/07/2017.
 */

public class QR_Survey extends BaseActivity {


    String state ="One Choice";

    ListView listView;

    ArrayList<String> optionList;
    Button add;
    protected EditText question,add_option;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_survey);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        listView = (ListView)findViewById(R.id.list_option_QR);


        add_option = (EditText)findViewById(R.id.Add_option_Qr);

        optionList = new ArrayList<String>();
        question = (EditText)findViewById(R.id.question_QR);


        Switch aSwitch;

        adapter = new ArrayAdapter<String>(this,R.layout.list_view_qr,R.id.txtView_qr,optionList);
        listView.setAdapter(adapter);

        aSwitch = (Switch)findViewById(R.id.multi_choice_QR);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked){

                    state = "Multiple Choice";

                }
                else if(!isChecked){


                    state = "One Choice";
                }
            }
        });



        add=(Button)findViewById(R.id.add_option_button_QR);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(add_option.getText().length()==0){

                    Toast.makeText(QR_Survey.this," Please enter at least 4 option",Toast.LENGTH_SHORT).show();




                }else{
                    String newOption = add_option.getText().toString().toUpperCase();
                    optionList.add(newOption);
                    adapter.notifyDataSetChanged();
                    add_option.setText("");//clear the textfield after entering option
                }
            }
        });






    }


    public void onremove_qr(View view) {

        optionList.remove(listView.getPositionForView(view));
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void GenerateQR(View view) {

        if(question.getText().length()<=0){

            Toast.makeText(QR_Survey.this,"Please enter a Survey Question ", Toast.LENGTH_SHORT).show();
        }else if (optionList.size()<4) {
            Toast.makeText(QR_Survey.this," Please enter at least 4 options", Toast.LENGTH_SHORT).show();


        }else {

            Intent intent = new Intent(QR_Survey.this, QRGenerator.class);
            intent.putStringArrayListExtra("array", optionList);
            intent.putExtra("question", question.getText().toString());
            Log.d(" optionlist", optionList.toString());
            intent.putExtra("state", state.toString());
            startActivity(intent);
            finish();
        }
    }
}
