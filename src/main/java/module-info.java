module com.actividades.chatofflinep {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.xml.bind;


    opens com.actividades.chatofflinep to javafx.fxml;
    opens com.actividades.chatofflinep.controller to javafx.fxml;
    opens com.actividades.chatofflinep.model to jakarta.xml.bind;
    exports com.actividades.chatofflinep;
}