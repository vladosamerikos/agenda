module com.mycompany.agendavlad {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    
    opens com.mycompany.agendavlad to javafx.fxml;
    exports com.mycompany.agendavlad;
}
