package com.actividades.chatofflinep;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ChatOfflineAplication extends Application{

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ChatOfflineAplication.class.getResource("pantallaInicioSesion.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("ChatOffline");
        File imagenURL = new File("imagenes/iconoApp.png");
        Image image = new Image(imagenURL.toURI().toString());
        stage.getIcons().add(image);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }



    public static void main(String[] args) {
    launch();

    }
}