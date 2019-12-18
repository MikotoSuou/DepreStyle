package com.example.bj_pogi.deprestylefinalfinal;

public class Item {


    private String question, choices;

    public Item(String question, String choices) {
        this.question = question;
        this.choices = choices;
    }

    public String getQuestion() {
        return question;
    }

    public String getChoices() {
        return choices;
    }
}
