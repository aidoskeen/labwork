package com.example.labwork.model;

public abstract class User {
    private int user_id;
    private String login;
    private String password;
    private String name;
    private String surname;
    private String phone_num;
    private String email;
    private String user_type;

    public User(){}

    public User(int user_id, String login, String password, String name, String surname, String phone_num, String email, String user_type) {
        this.user_id = user_id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.phone_num = phone_num;
        this.email = email;
        this.user_type = user_type;
    }

    public User(String login, String password, String name, String surname, String phone_num, String email, String user_type) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.phone_num = phone_num;
        this.email = email;
        this.user_type = user_type;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}
