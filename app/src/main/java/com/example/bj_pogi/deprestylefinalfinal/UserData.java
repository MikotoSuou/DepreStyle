package com.example.bj_pogi.deprestylefinalfinal;

public class UserData {
    private String title;
    private String log;
    private String date;
    private String time;

    public UserData(){

    }


    public UserData(String title, String log, String date, String time) {
        this.title = title;
        this.log = log;
        this.date = date;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
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
