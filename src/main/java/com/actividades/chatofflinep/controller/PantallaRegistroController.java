package com.actividades.chatofflinep.controller;

import com.actividades.chatofflinep.ChatOfflineAplication;
import com.actividades.chatofflinep.dataAccess.XMLManagerCollection;
import com.actividades.chatofflinep.model.TodosUsuarios;
import com.actividades.chatofflinep.model.Usuario;
import com.actividades.chatofflinep.utils.Utilidades;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PantallaRegistroController {


    public TextField correo;
    public PasswordField constrasenna;
    public TextField nombre;
    public TextField numeroTelefono;
    public Label telefonoInvalido;
    public Label usuarioYaExiste;


    public void registrado(MouseEvent mouseEvent) {
        String email = correo.getText();
        String password = constrasenna.getText();
        String nombre = this.nombre.getText();
        boolean correcto = Utilidades.esNumeroDeTelefono(numeroTelefono.getText());
        if (!correcto) {
            telefonoInvalido.setText("Numero De Telefono Invalido Introduce otro");
        } else {
            Usuario usuario = new Usuario();
            usuario.setEmail(email);
            usuario.setContrasenna(password);
            usuario.setNombre(nombre);
            usuario.setNumeroTelefono(numeroTelefono.getText());
            String ruta = "xml/usuarios.xml";
            File file = new File(ruta);
            TodosUsuarios usuarios = new TodosUsuarios();
            List<Usuario> usuariosRegistrados = new ArrayList<>();

            if (file.exists()) {
                usuariosRegistrados = usuarios.getUsuarioList();
                boolean encontrado = false;
                for (Usuario usuario1 : usuariosRegistrados) {
                    if (usuario1.getNumeroTelefono().equals(usuario.getNumeroTelefono()) || usuario1.getEmail().equals(usuario.getEmail())) {
                        usuarioYaExiste.setText("Este usuario ya existe en el sistema Inicia sesión");
                        encontrado = true;
                    }
                }
                if (!encontrado) {
                    usuariosRegistrados.add(usuario);
                    usuarios.setUsuarioList(usuariosRegistrados);
                    XMLManagerCollection.writeXML(usuarios, "xml/usuarios.xml");
                    registroSesionExitoso(mouseEvent);
                }
            } else {
                XMLManagerCollection.writeXML(usuarios, "xml/usuarios.xml");
                usuariosRegistrados = usuarios.getUsuarioList();
                usuariosRegistrados.add(usuario);

            }


        }


    }


    public void iniciarSesion(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ChatOfflineAplication.class.getResource("pantallaInicioSesion.fxml"));
            Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            Scene scene = null;
            scene = new Scene(fxmlLoader.load());
            currentStage.setResizable(false);
            currentStage.setTitle("ChatOffline");
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void registroSesionExitoso(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ChatOfflineAplication.class.getResource("pantallaInicioSesion.fxml"));
            Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            Scene scene = null;
            scene = new Scene(fxmlLoader.load());
            currentStage.setResizable(false);
            currentStage.setTitle("ChatOffline");
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
