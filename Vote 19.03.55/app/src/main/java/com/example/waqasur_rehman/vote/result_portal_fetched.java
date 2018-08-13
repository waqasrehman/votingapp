package com.example.waqasur_rehman.vote;

/**
 * Created by waqas on 25/07/2017.
 *      * this class hold all the setters and getters that are need to get the  data returned from the database in JSON format

 */

public class result_portal_fetched {

    public String question;
    public int ID;
    public String Name,Email, Vote;


    public String getquestion() {
        return question ;
    }

    public void setquestion(String question) {
        this.question = question;
    }

    public int getID(){
        return  ID;
    }

    public void setID(int ID ){



        this.ID = ID;
    }

    public String getEmail(){


        return Email;
    }

    public void setEmail( String Email){

        this.Email=Email;
    }

    public String getVote(){


        return Vote;
    }

    public void setVote(String Vote){

        this.Vote = Vote;
    }

    public String getName(){

        return Name;
    }

    public void setName(String Name){

        this.Name = Name;
    }

}


