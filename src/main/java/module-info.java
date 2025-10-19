module com.actividades.chatofflinep {
    requires javafx.controls;
    requires jakarta.xml.bind;
    requires javafx.fxml;
    requires java.desktop;


    opens com.actividades.chatofflinep to javafx.fxml;
    opens com.actividades.chatofflinep.controller to javafx.fxml;
    opens com.actividades.chatofflinep.model to jakarta.xml.bind;
    exports com.actividades.chatofflinep;
}