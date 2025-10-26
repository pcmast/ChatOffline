package com.actividades.chatofflinep.controller;

import com.actividades.chatofflinep.ChatOfflineAplication;
import com.actividades.chatofflinep.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class PantallaDatosPersonalesController {
    @FXML
    private Label nombre;
    @FXML
    private Label email;
    @FXML
    private Label contrasenna;
    @FXML
    private Label telefono;

    //Metodo que al iniciar la ventana pone datos en los campos
    public void initialize(){
        Usuario usuario =UsuarioActualController.getInstance().getUsuario();
        nombre.setText(usuario.getNombre());
        email.setText(usuario.getEmail());
        contrasenna.setText("*****");
        telefono.setText(usuario.getNumeroTelefono());
    }

    //Metodo que abre una ventana para cambiar tus datos (Contraseña)
    public void editarDatos(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ChatOfflineAplication.class.getResource("pantallaCambiarTusDatos.fxml"));
            Scene scene = null;
            scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("ChatOffline");
            stage.setScene(scene);
            Stage owner = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.setResizable(false);
            File imagenURL = new File("imagenes/iconoApp.png");
            Image image = new Image(imagenURL.toURI().toString());
            stage.getIcons().add(image);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(owner);
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
