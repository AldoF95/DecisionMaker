package com.example.decisionapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * main class
 * Question class
 * attributes: questionName, questionText, questionOptions
 * methods: setter and getters, getOptionLen(), addOption(), removeOption(), getOption()
 * constructor: Question(name, text)
 */
public class Questions implements Serializable {
    private String questionName;
    private String questionText;
    private ArrayList<String> questionOptions = new ArrayList<String>();

    public Questions(){}

    /**
     * constructor
     * @param name
     * @param text
     */
    public Questions(String name, String text){
        this.questionName = name;
        this.questionText = text;
    }

    public void setQuestionName(String name){this.questionName = name;}
    public void setQuestionText(String text){this.questionText = text;}
    public String getQuestionName(){return this.questionName;}
    public String getQuestionText(){return this.questionText;}

    public void addOption(String option){
        this.questionOptions.add(option);
    }

    /**
     * return array of options
     * @return
     */
    public ArrayList<String> getOption(){
        return this.questionOptions;
    }

    public int getOptionsLen(){
        return this.questionOptions.size();
    }

    public void removeOption(String option){
        this.questionOptions.remove(option);
    }
}
