package com.example.labwork.Controllers;

import com.example.labwork.Main;
import com.example.labwork.Tools.DBclass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class UsersController implements Initializable {
    public TableColumn <TableParameters,Integer> user_idCol;
    public TableColumn<TableParameters,String> loginCol;
    public TableColumn<TableParameters,String> phonenumCol;
    public TableColumn<TableParameters,String> emailCol;
    public TableColumn<TableParameters,String> nameCol;
    public TableColumn<TableParameters,String> facultyCol;
    public TableColumn<TableParameters,Integer> DormnumCol;
    public TableColumn<TableParameters,Void> emptyField;
    public TableColumn<TableParameters,String> surnameCol;
    public TableColumn<TableParameters,String> utCol;
    public TableColumn<TableParameters,Integer> roomCol;
    public TableView allusers;
    private ObservableList<TableParameters> obsList = FXCollections.observableArrayList();
    private Connection connection;
    private Statement statement;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        allusers.setEditable(true);
        user_idCol.setCellValueFactory(new PropertyValueFactory<>("userid"));
        DormnumCol.setCellValueFactory(new PropertyValueFactory<>("dorm_num"));
        roomCol.setCellValueFactory(new PropertyValueFactory<>("room_num"));
        utCol.setCellValueFactory(new PropertyValueFactory<>("usertype"));
        loginCol.setCellValueFactory(new PropertyValueFactory<>("login"));
        loginCol.setCellFactory(TextFieldTableCell.forTableColumn());
        loginCol.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setLogin(t.getNewValue());
                    //Update e record on change
                    DBclass.updateField("userstable","login","user_id", t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getLogin(), t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getUserid());
                }
        );
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(
                t -> {
                    String newName = t.getNewValue();
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setName(newName);

                    DBclass.updateField("userstable","name","user_id", newName, t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getUserid());
                }
        );
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        surnameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        surnameCol.setOnEditCommit(
                t -> {
                    String newSurname = t.getNewValue();
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setSurname(t.getNewValue());
                    //Update db
                    DBclass.updateField("userstable","surname","user_id", newSurname, t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getUserid());}
        );
        facultyCol.setCellValueFactory(new PropertyValueFactory<>("faculty"));
        facultyCol.setCellFactory(TextFieldTableCell.forTableColumn());
        facultyCol.setOnEditCommit(
                t -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setFaculty(t.getNewValue())
        );
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        emailCol.setOnEditCommit(
                 t -> {String newEmail=t.getNewValue();
                     t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setEmail(t.getNewValue());
                     DBclass.updateField("usertable","email","user_id", t.getTableView().getItems().get(
                             t.getTablePosition().getRow()).getEmail(), t.getTableView().getItems().get(
                             t.getTablePosition().getRow()).getUserid());
                 }
        );

        phonenumCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        phonenumCol.setCellFactory(TextFieldTableCell.forTableColumn());
        phonenumCol.setOnEditCommit(
                t -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setPhone(t.getNewValue())
        );


        Callback<TableColumn<TableParameters, Void>, TableCell<TableParameters, Void>> cellFactory = new Callback<TableColumn<TableParameters, Void>, TableCell<TableParameters, Void>>() {
            @Override
            public TableCell<TableParameters, Void> call(final TableColumn<TableParameters, Void> param) {
                final TableCell<TableParameters, Void> cell = new TableCell<TableParameters, Void>() {

                    private final Button button = new Button("Delete");

                    {
                        button.setOnAction((ActionEvent event) -> {
                            TableParameters data = getTableView().getItems().get(getIndex());
                            //Delete user by id
                            DBclass.deleteUser("`userstable`","user_id",data.getUserid());
                            //Hibernate version:
                            //userHibController.removeUser(data.getUserId());

                            try {
                                //Immediately after delete, update info in table
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
        allusers.setEditable(true);
        allusers.getItems().clear();
        connection = DBclass.connectDb();
        String sql = "SELECT * FROM userstable";
        statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            TableParameters tableParameters = new TableParameters();
            tableParameters.setUserid(rs.getInt(1));
            tableParameters.setLogin(rs.getString(2));
            tableParameters.setName(rs.getString(4));
            tableParameters.setSurname(rs.getString(5));
            tableParameters.setPhone(rs.getString(6));
            tableParameters.setEmail(rs.getString(7));
            tableParameters.setUsertype(rs.getString(8));
            tableParameters.setFaculty(rs.getString(9));
            tableParameters.setDorm_num(rs.getInt(10));
            tableParameters.setRoom_num(rs.getInt(11));
            obsList.add(tableParameters);
        }

        allusers.setItems(obsList);

        DBclass.disconnectDb(connection, statement);
    }

    public void goprevious(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainPage.fxml"));
        Parent parent1=fxmlLoader.load();
        Stage stage=(Stage)allusers.getScene().getWindow();
        Scene scene = new Scene(parent1);
        stage.setScene(scene);
        stage.show();
    }
}
