package com.example.labwork.Controllers;

import com.example.labwork.Main;
import com.example.labwork.Tools.DBclass;
import com.example.labwork.model.Room;
import com.example.labwork.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class RoomsCtrl implements Initializable {
    public TableColumn<RoomsParameters,Integer> roomnumCol;
    public TableColumn <RoomsParameters,String>dormnumCol;
    public TableColumn<RoomsParameters,String> typeCol;
    public TableColumn <RoomsParameters,String>availableCol;
    public TextField dormNumText;
    public TextField roomNumText;
    public TextField studidText;
    public TextField roomissueText;
    public TextField typeText;
    public TableView roomsTab;
    public TableColumn<RoomsParameters,Void> emptyField;
    private ObservableList<RoomsParameters> obsList = FXCollections.observableArrayList();
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private PreparedStatement pStatement;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        roomsTab.setEditable(true);
        roomnumCol.setCellValueFactory(new PropertyValueFactory<>("roomnum"));
        availableCol.setCellValueFactory(new PropertyValueFactory<>("available"));
        dormnumCol.setCellValueFactory(new PropertyValueFactory<>("dormnum"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("rtype"));
        typeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        typeCol.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setRtype(t.getNewValue());
                    //Update e record on change
                    DBclass.updateField("rooms","room_type","room_num", t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getRtype(), t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getRoomnum());
                }
        );
        Callback<TableColumn<RoomsParameters, Void>, TableCell<RoomsParameters, Void>> cellFactory = new Callback<TableColumn<RoomsParameters, Void>, TableCell<RoomsParameters, Void>>() {
            @Override
            public TableCell<RoomsParameters, Void> call(final TableColumn<RoomsParameters, Void> param) {
                final TableCell<RoomsParameters, Void> cell = new TableCell<RoomsParameters, Void>() {

                    private final Button button = new Button("Delete");

                    {
                        button.setOnAction((ActionEvent event) -> {
                            RoomsParameters data = getTableView().getItems().get(getIndex());
                            DBclass.deleteUser("rooms","room_num",data.getRoomnum());
                            try {
                                loadData();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(button);
                        }
                    }
                };
                return cell;
            }
        };

        emptyField.setCellFactory(cellFactory);

        try {
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void loadData() throws SQLException {
        roomsTab.setEditable(true);
        roomsTab.getItems().clear();
        connection = DBclass.connectDb();
        String sql = "SELECT * FROM rooms";
        statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            RoomsParameters roomParameters = new RoomsParameters();
            roomParameters.setRoomnum(rs.getInt(1));
            roomParameters.setDormnum(rs.getInt(4));
            roomParameters.setRtype(rs.getString(2));
            roomParameters.setAvailable(rs.getInt(3));

            obsList.add(roomParameters);
        }

        roomsTab.setItems(obsList);

        DBclass.disconnectDb(connection, statement);
    }
    public void addRoom(ActionEvent actionEvent) throws SQLException {
        Room room =new Room(Integer.parseInt(roomNumText.getText()),typeText.getText(),true);
        connection = DBclass.connectDb();
        String sql = "SELECT count(*) FROM dormtable AS u WHERE u.dormnum = ?";
        pStatement = connection.prepareStatement(sql);
        pStatement.setInt(1, Integer.parseInt(dormNumText.getText()));
        ResultSet rs = pStatement.executeQuery();
        while (rs.next()) {
            if (rs.getInt(1) > 0) {
                String insertString = "INSERT INTO rooms(`room_num`,`room_type`, `room_available`, `dormnum`) VALUES (?,?,?,?)";
                preparedStatement = connection.prepareStatement(insertString);
                preparedStatement.setInt(1, room.getRoom_num());
                preparedStatement.setString(2, room.getRoom_type());
                preparedStatement.setInt(3, room.isAvailable()?1:0);
                preparedStatement.setInt(4, Integer.parseInt(dormNumText.getText()));
                preparedStatement.execute();
                SignIN.openMessageBox("ADDED!");
            }
            else{
                SignIN.openMessageBox("Such Dormitory didn't exist!");
            }
            DBclass.disconnectDb(connection, preparedStatement);
        }
    }

    public void issueRoom(ActionEvent actionEvent) throws SQLException {
        String sql = "SELECT count(*) FROM userstable AS u WHERE u.user_id = ? AND u.user_type = ?";
        connection = DBclass.connectDb();
        pStatement = connection.prepareStatement(sql);
        pStatement.setInt(1, Integer.parseInt(studidText.getText()));
        pStatement.setString(2, "Student");
        ResultSet rs = pStatement.executeQuery();
        while (rs.next()) {
            if (rs.getInt(1) > 0) {
                String sql2 = "SELECT * FROM rooms AS u where u.room_num = " + roomissueText.getText();
                Statement statement = connection.createStatement();
                ResultSet RS = statement.executeQuery(sql2);
                Room room=new Room();
                while (RS.next()) {
                    room.setRoom_num(RS.getInt(1));
                    room.setRoom_type(RS.getString(2));
                    room.setAvailable(RS.getInt(3) == 1);
                }
                Student student = new Student(room);
                DBclass.updateField("userstable","room_num","user_id",student.getIssued_room().getRoom_num(),Integer.parseInt(studidText.getText()));
                SignIN.openMessageBox("ISSUED!");
            }
            else{
                SignIN.openMessageBox("No such student!");
            }
            DBclass.disconnectDb(connection, preparedStatement);
        }
    }


    public void goback(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainPage.fxml"));
        Parent parent1=fxmlLoader.load();
        Stage stage=(Stage)studidText.getScene().getWindow();
        Scene scene = new Scene(parent1);
        stage.setScene(scene);
        stage.show();
    }
}
