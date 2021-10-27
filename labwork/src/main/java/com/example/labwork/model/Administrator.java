package com.example.labwork.model;

public class Administrator extends User{
    private Dormitory dorm;



    public Administrator(){}

    public Administrator(String login, String password, String name, String surname, String phone_num, String email, String user_type, Dormitory dorm) {
        super(login, password, name, surname, phone_num, email, user_type);
        this.dorm = dorm;
    }

    public Dormitory getDorm() {
        return dorm;
    }

    public void setDorm(Dormitory dorm) {
        this.dorm = dorm;
    }
}
