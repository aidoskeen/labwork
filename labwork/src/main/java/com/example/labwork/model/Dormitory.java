package com.example.labwork.model;

import java.util.ArrayList;
import java.util.List;

public class Dormitory {
    private int dorm_num;
    private String address;
    private int rooms_count;
    public Dormitory(){}
    public String getAddress() {
        return address;
    }

    public void setDorm_num(int dorm_num) {
        this.dorm_num = dorm_num;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    public int getRooms_count() {
        return rooms_count;
    }

    public void setRooms_count(int rooms_count) {
        this.rooms_count = rooms_count;
    }

    private ArrayList<Room> rooms;

    public Dormitory(int dorm_num, ArrayList<Room> rooms) {
        this.dorm_num = dorm_num;
        this.rooms = rooms;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public int getDorm_num() {
        return dorm_num;
    }
}
