package com.example.waqasur_rehman.vote;

/**
 * Created by waqas on 08/08/2017.
 */

public class message_fetched {


    public String  recipient;
    public int MID;

    public String emMessage;




    public String getRecipient(){

        return recipient;
    }

    public void setRecipient(String recipient){

        this.recipient=recipient;
    }



    public int getMID(){

        return MID;
    }

    public void setMID(int MID){

        this.MID=MID;
    }

    public String getemMessage(){

        return emMessage;
    }

    public void setemMessage(String emMessage){

        this.emMessage=emMessage;
    }

}
