package com.example.team6project.tab_hospital;

public class Clinic {
    String name, position, address, phone_number;
    String[] time;
    String type;

    public Clinic(String name, String position, String address, String phone_number , String[] time, String type){
        this.name = name;
        this.position = position;
        this.address = address;
        this.phone_number = phone_number;
        this.time = time;
        this.type = type;
    }
}
