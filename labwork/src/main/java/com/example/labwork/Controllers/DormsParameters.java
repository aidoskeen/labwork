package com.example.labwork.Controllers;

import com.example.labwork.Tools.DBclass;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DormsParameters{
    private SimpleIntegerProperty dormitorynum = new SimpleIntegerProperty();
    private SimpleStringProperty address = new SimpleStringProperty();
    private SimpleIntegerProperty rcount = new SimpleIntegerProperty();

    public int getDormitorynum() {
        return dormitorynum.get();
    }

    public SimpleIntegerProperty dormitorynumProperty() {
        return dormitorynum;
    }

    public void setDormitorynum(int dormitorynum) {
        this.dormitorynum.set(dormitorynum);
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public int getRcount() {
        return rcount.get();
    }

    public SimpleIntegerProperty rcountProperty() {
        return rcount;
    }

    public void setRcount(int rcount) {
        this.rcount.set(rcount);
    }
}
