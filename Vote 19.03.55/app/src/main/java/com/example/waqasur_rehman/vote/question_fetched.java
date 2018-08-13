package com.example.waqasur_rehman.vote;

/**
 * Created by waqas on 25/07/2017.
 *      * this class hold all the setters and getters that are need to get the  data returned from the database in JSON format

 */

public class question_fetched {

        public String question;
        public int ID;


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


    }


