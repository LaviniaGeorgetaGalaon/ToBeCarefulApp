package com.example.myapplication.model;

public class PostTextModel {
    private String tip,pastile,doctor;

    public PostTextModel(String tip, String pastile, String doctor) {
        this.tip = tip;
        this.pastile = pastile;
        this.doctor = doctor;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getPastile() {
        return pastile;
    }

    public void setPastile(String pastile) {
        this.pastile = pastile;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }
}
