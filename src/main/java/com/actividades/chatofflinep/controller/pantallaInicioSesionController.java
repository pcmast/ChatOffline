package com.actividades.chatofflinep.controller;

import com.actividades.chatofflinep.ChatOfflineAplication;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Stack;

public class pantallaInicioSesionController {

    public ImageView imagenLogo;
    public PasswordField txContrasenna;
    public TextField txCorreo;
    public Button btnInicioSesion;

    public void initialize(){
        File imagenRuta = new File("imagenes/mensajeria.png");
        Image image = new Image(imagenRuta.toURI().toString());
        imagenLogo.setImage(image);

    }


    public void inicioSesion(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ChatOfflineAplication.class.getResource("pantallaInicial.fxml"));
            Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            Scene scene = null;
            scene = new Scene(fxmlLoader.load());
            currentStage.setTitle("Respawnix");
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

    public void registrarse(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ChatOfflineAplication.class.getResource("pantallaRegistro.fxml"));
            Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            Scene scene = null;
            scene = new Scene(fxmlLoader.load());
            currentStage.setTitle("Respawnix");
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
