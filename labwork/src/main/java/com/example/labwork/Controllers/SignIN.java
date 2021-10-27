package com.example.labwork.Controllers;

import com.example.labwork.Main;
import com.example.labwork.Tools.DBclass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignIN {
    @FXML
    public Button signINBut;
    @FXML
    public Button signUPBut;
    @FXML
    public TextField loginID;
    @FXML
    public PasswordField passID;
    private Connection connection;
    private PreparedStatement preparedStatement;
    public void SignUserin(ActionEvent actionEvent) throws SQLException, IOException {

        connection = DBclass.connectDb();
        String sql = "SELECT count(*) FROM userstable AS u WHERE u.login = ? AND u.password = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, loginID.getText());
        preparedStatement.setString(2, passID.getText());
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            if (rs.getInt(1) > 0) {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainPage.fxml"));
                Parent root = fxmlLoader.load();

                MainPageCtrl mainPageCtrl = fxmlLoader.getController();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Main page");
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            } else {
                openMessageBox("Incorrect input");
            }
        }
        //Do not forget to close open connections and statements
        DBclass.disconnectDb(connection, preparedStatement);
    }
    public static void openMessageBox(String mess) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Message text:");
        alert.setContentText(mess);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    public void nextForm(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SignUp.fxml"));
        Parent parent1=fxmlLoader.load();
        Stage stage=(Stage)loginID.getScene().getWindow();
        Scene scene = new Scene(parent1);
        stage.setScene(scene);
        stage.show();
    }
}
