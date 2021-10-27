package com.example.labwork.Controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RoomsParameters {
    private SimpleIntegerProperty roomnum = new SimpleIntegerProperty();
    private SimpleIntegerProperty dormnum = new SimpleIntegerProperty();
    private SimpleStringProperty rtype = new SimpleStringProperty();
    private SimpleIntegerProperty available = new SimpleIntegerProperty();

    public int getRoomnum() {
        return roomnum.get();
    }

    public SimpleIntegerProperty roomnumProperty() {
        return roomnum;
    }

    public void setRoomnum(int roomnum) {
        this.roomnum.set(roomnum);
    }

    public int getDormnum() {
        return dormnum.get();
    }

    public SimpleIntegerProperty dormnumProperty() {
        return dormnum;
    }

    public void setDormnum(int dormnum) {
        this.dormnum.set(dormnum);
    }

    public String getRtype() {
        return rtype.get();
    }

    public SimpleStringProperty rtypeProperty() {
        return rtype;
    }

    public void setRtype(String rtype) {
        this.rtype.set(rtype);
    }

    public int getAvailable() {
        return available.get();
    }

    public SimpleIntegerProperty availableProperty() {
        return available;
    }

    public void setAvailable(int available) {
        this.available.set(available);
    }
}
