package com.example.team6project.tab_clinic;

public class Clinic {
    String name, position, phone_number;
    String[] time;
    String type;

    public Clinic(String name, String position, String phone_number , String[] time, String type){
        this.name = name;
        this.position = position;
        this.phone_number = phone_number;
        this.time = time;
        this.type = type;
    }
}
