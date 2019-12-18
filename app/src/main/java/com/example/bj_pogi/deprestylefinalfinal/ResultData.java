package com.example.bj_pogi.deprestylefinalfinal;

public class ResultData {
    private String score;
    private String result;
    private String date;
    private String time;

    public ResultData(){

    }

    public ResultData(String score, String result, String date, String time) {
        this.score = score;
        this.result = result;
        this.date = date;
        this.time = time;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
