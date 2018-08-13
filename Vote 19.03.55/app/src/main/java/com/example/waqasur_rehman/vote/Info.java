package com.example.waqasur_rehman.vote;

import android.os.Bundle;

/**
 * Created by waqas on 23/07/2017.
 */

public class Info extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }




}
