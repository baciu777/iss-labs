module com.example.labs {
    requires javafx.controls;
    requires javafx.fxml;


    requires java.sql;

    opens domain to javafx.base;


    opens com.example.labs to javafx.fxml;
    exports com.example.labs;
    exports repository;
}