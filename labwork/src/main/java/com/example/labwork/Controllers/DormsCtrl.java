package com.example.labwork.Controllers;

import com.example.labwork.Main;
import com.example.labwork.Tools.DBclass;
import com.example.labwork.model.Dormitory;
import com.example.labwork.model.Room;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DormsCtrl implements Initializable {

    public ListView dormsList;
    public TextField dormText;
    public TableColumn<DormsParameters,Integer> dormCol;
    public TableColumn<DormsParameters,String> addrCol;
    public TableColumn <DormsParameters,Integer>rcountCol;
    public TableColumn<DormsParameters,Void> emptyCol;
    public Button goBack;
    public TableView dormTable;
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ObservableList<DormsParameters> obsList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dormTable.setEditable(true);
        dormCol.setCellValueFactory(new PropertyValueFactory<>("dormitorynum"));
        rcountCol.setCellValueFactory(new PropertyValueFactory<>("rcount"));
        addrCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        addrCol.setCellFactory(TextFieldTableCell.forTableColumn());
        addrCol.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setAddress(t.getNewValue());
                    //Update e record on change
                    DBclass.updateField("dormtable","dormnum","dormnum" ,t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getAddress(), t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).getDormitorynum());
                }
        );
        Callback<TableColumn<DormsParameters, Void>, TableCell<DormsParameters, Void>> cellFactory = new Callback<TableColumn<DormsParameters, Void>, TableCell<DormsParameters, Void>>() {
            @Override
            public TableCell<DormsParameters, Void> call(final TableColumn<DormsParameters, Void> param) {
                final TableCell<DormsParameters, Void> cell = new TableCell<DormsParameters, Void>() {

                    private final Button button = new Button("Delete");

                    {
                        button.setOnAction((ActionEvent event) -> {
                            DormsParameters data = getTableView().getItems().get(getIndex());
                            DBclass.deleteUser("dormtable","dormnum",data.getDormitorynum());
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

        emptyCol.setCellFactory(cellFactory);

        try {
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void loadData() throws SQLException {
        dormTable.setEditable(true);
        dormTable.getItems().clear();
        connection = DBclass.connectDb();
        String sql = "SELECT * FROM dormtable";
        statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            DormsParameters dormParameters = new DormsParameters();
            dormParameters.setDormitorynum(rs.getInt(1));
            dormParameters.setRcount(rs.getInt(3));
            dormParameters.setAddress(rs.getString(2));


            obsList.add(dormParameters);
        }

        dormTable.setItems(obsList);

        DBclass.disconnectDb(connection, statement);
    }
    private void fillTables(int dormnum) {
        dormsList.getItems();
        ArrayList<Room> roomsOfDorm = DBclass.getRoomsbyDormnum(dormnum);
        Dormitory dorm=new Dormitory(dormnum,roomsOfDorm);
        for ( Room r : dorm.getRooms()) {
            dormsList.getItems().add(+ dorm.getDorm_num()+" | "+r.getRoom_num() + " | " + r.getRoom_type() );
        }
    }

    public void seeRooms(ActionEvent actionEvent) {
        fillTables(Integer.parseInt(dormText.getText()));
    }

    public void gobackmain(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainPage.fxml"));
        Parent parent1=fxmlLoader.load();
        Stage stage=(Stage)dormsList.getScene().getWindow();
        Scene scene = new Scene(parent1);
        stage.setScene(scene);
        stage.show();
    }
}
