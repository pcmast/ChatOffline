package com.actividades.chatofflinep.controller;

import com.actividades.chatofflinep.ChatOfflineAplication;
import com.actividades.chatofflinep.dataAccess.XMLManagerCollection;
import com.actividades.chatofflinep.model.Contacto;
import com.actividades.chatofflinep.model.ContactosUsuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PantallaContactosController {

    @FXML
    private ListView<Contacto> listaContactos;


    public void initialize() {
        listaContactos.setItems(cargarLista());
        listaContactos.getSelectionModel().select(0);

        String carpetaRuta = "xml/contactosUsuario";
        File carpeta = new File(carpetaRuta);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
    }

    public ObservableList<Contacto> cargarLista() {
        ObservableList<Contacto> observableList = FXCollections.observableArrayList();
        List<Contacto> list = UsuarioActualController.getInstance().getUsuario().getContactos();
        observableList.addAll(list);
        return observableList;
    }

    public void crearContacto(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ChatOfflineAplication.class.getResource("pantallaCrearContacto.fxml"));
            Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            Scene scene = null;
            scene = new Scene(fxmlLoader.load());
            currentStage.setTitle("ChatOffline");
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void editarContacto(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ChatOfflineAplication.class.getResource("pantallaCrearContacto.fxml"));
            Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            Scene scene = null;
            scene = new Scene(fxmlLoader.load());
            PantallaCrearContactoController controller = fxmlLoader.getController();
            controller.habilitarEdicion();
            controller.setContacto(listaContactos.getSelectionModel().getSelectedItem());
            controller.asignarValores();
            currentStage.setTitle("ChatOffline");
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
