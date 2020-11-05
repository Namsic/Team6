package com.example.team6project.tab_clinic;

// 각 진료소 & 병원 정보를 담을 객체
// Editor - 김남재
public class Clinic {
    String name, position, phone_number;
    String[] time;
    String type;

    public Clinic(String name, String position, String phone_number , String[] time, String type){
        if(name.split("[*]").length == 1)
            this.name = name;
        else
            this.name = name.replace("*", "\n");
        this.position = position;
        this.phone_number = phone_number;
        this.time = time;
        this.type = type;
    }
}
