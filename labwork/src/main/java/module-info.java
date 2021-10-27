module com.example.labwork {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires mysql.connector.java;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;

    opens com.example.labwork to javafx.fxml;
    exports com.example.labwork;
    exports com.example.labwork.Controllers;
    opens com.example.labwork.Controllers to javafx.fxml;
    exports com.example.labwork.model;
    opens com.example.labwork.model to javafx.fxml;
}