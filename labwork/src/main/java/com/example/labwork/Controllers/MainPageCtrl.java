package com.example.labwork.Controllers;

import com.example.labwork.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPageCtrl {
    public Button usersbut;

    public void goAllusers(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AllUsersData.fxml"));
        Parent parent1=fxmlLoader.load();
        Stage stage=(Stage)usersbut.getScene().getWindow();
        Scene scene = new Scene(parent1);
        stage.setScene(scene);
        stage.show();
    }

    public void goRooms(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Rooms.fxml"));
        Parent parent1=fxmlLoader.load();
        Stage stage=(Stage)usersbut.getScene().getWindow();
        Scene scene = new Scene(parent1);
        stage.setScene(scene);
        stage.show();
    }

    public void goDorms(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Dormitories.fxml"));
        Parent parent1=fxmlLoader.load();
        Stage stage=(Stage)usersbut.getScene().getWindow();
        Scene scene = new Scene(parent1);
        stage.setScene(scene);
        stage.show();
    }

}
