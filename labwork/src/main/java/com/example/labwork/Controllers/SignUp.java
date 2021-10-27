package com.example.labwork.Controllers;

import com.example.labwork.Main;
import com.example.labwork.Tools.DBclass;
import com.example.labwork.model.Administrator;
import com.example.labwork.model.Dormitory;
import com.example.labwork.model.Student;
import com.example.labwork.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignUp implements Initializable {
    public TextField idText;
    public TextField loginText;
    
    public PasswordField passText;
    public TextField nameText;
    public TextField surnameText;
    public TextField facText;
    public Button signupButton;
    public Button goBackButton;
    public Label facultyLabel;
    public Label dormnumLabel;
    public TextField phoneText;
    public TextField emailText;
    public RadioMenuItem adminChoice;
    public RadioMenuItem studentChoice;
    SessionFactory factory;
    UserHiberCtrl userHib= new UserHiberCtrl(factory);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameText.setVisible(false);
        surnameText.setVisible(false);
        facText.setVisible(false);
        facultyLabel.setVisible(false);

    }
    private Connection connection;
    private PreparedStatement preparedStatement;
    public void signUserUp(ActionEvent actionEvent) throws IOException, SQLException {
            if(adminChoice.isSelected()) {
                userHib.start();
                Dormitory dorm=new Dormitory();
                Administrator admin = new Administrator(loginText.getText(),passText.getText(),nameText.getText(),surnameText.getText(),  phoneText.getText(), emailText.getText(),adminChoice.getText(),dorm);

                userHib.createUser(admin);
             /*   connection = DBclass.connectDb();
                String insertString = "INSERT INTO userstable(`user_id`,`login`, `password`, `name`,`surname`, `phone_num`, `email`,`user_type`) VALUES (?,?,?,?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(insertString);
                preparedStatement.setInt(1, admin.getUser_id());
                preparedStatement.setString(2, admin.getLogin());
                preparedStatement.setString(3, admin.getPassword());
                preparedStatement.setString(4, admin.getName());
                preparedStatement.setString(5, admin.getSurname());
                preparedStatement.setString(6, admin.getPhone_num());
                preparedStatement.setString(7, admin.getEmail());
                preparedStatement.setString(8, admin.getUser_type());
                preparedStatement.execute();
                DBclass.disconnectDb(connection, preparedStatement);*/
            }
            else{
                Student student = new Student( loginText.getText(),passText.getText(),nameText.getText(),surnameText.getText(),  phoneText.getText(), emailText.getText(),studentChoice.getText(),facText.getText());

                /*connection = DBclass.connectDb();
                String insertString = "INSERT INTO userstable(`user_id`,`login`, `password`,`name`, `surname`, `phone_num`, `email`, `user_type`, `faculty`) VALUES (?,?,?,?,?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(insertString);
                preparedStatement.setInt(1, student.getUser_id());
                preparedStatement.setString(2, student.getLogin());
                preparedStatement.setString(3, student.getPassword());
                preparedStatement.setString(4, student.getName());
                preparedStatement.setString(5, student.getSurname());
                preparedStatement.setString(6, student.getPhone_num());
                preparedStatement.setString(7, student.getEmail());
                preparedStatement.setString(8, student.getUser_type());
                preparedStatement.setString(9, student.getFaculty());
                preparedStatement.execute();
                DBclass.disconnectDb(connection, preparedStatement);*/
            }

        SignIN.openMessageBox("User created successfully.");
        Previous();
    }


    private void Previous() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SignIN.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        Stage stage = (Stage) loginText.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public void goBack(ActionEvent actionEvent) throws IOException {
        Previous();
    }


    public void showAdminFields(ActionEvent actionEvent) {
        nameText.setVisible(true);
        surnameText.setVisible(true);
        facText.setVisible(false);
        facultyLabel.setVisible(false);

    }

    public void showStudentFields(ActionEvent actionEvent) {
        nameText.setVisible(true);
        surnameText.setVisible(true);
        facText.setVisible(true);
        facultyLabel.setVisible(true);

    }
}

