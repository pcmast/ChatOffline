package com.actividades.chatofflinep.controller;

import com.actividades.chatofflinep.ChatOfflineAplication;
import com.actividades.chatofflinep.dataAccess.XMLManagerCollection;
import com.actividades.chatofflinep.model.TodosUsuarios;
import com.actividades.chatofflinep.model.Usuario;
import com.actividades.chatofflinep.utils.Utilidades;
import javafx.fxml.FXML;
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

    @FXML
    private TextField correo;
    @FXML
    private PasswordField constrasenna;
    @FXML
    private TextField nombre;
    @FXML
    private TextField numeroTelefono;
    @FXML
    private Label telefonoInvalido;
    @FXML
    private Label InformacionDelRegistro;

    //Metodo que registra un usuario en el sistema y comprueba si ese usuario ya existe en el mismo
    public void registrado(MouseEvent mouseEvent) {
        String email = correo.getText();
        String password = constrasenna.getText();
        String nombre = this.nombre.getText();
        boolean correcto = Utilidades.esNumeroDeTelefono(numeroTelefono.getText());
        boolean correoCorrecto = Utilidades.esCorreoElectronicoValido(correo.getText());
        telefonoInvalido.setText("");
        if (!numeroTelefono.getText().isEmpty() && !correo.getText().isEmpty() && !nombre.isEmpty()) {
            if (!correoCorrecto) {
            InformacionDelRegistro.setText("Introduce un correo correcto");
            }else {
            if (!correcto) {
                telefonoInvalido.setText("Numero De Telefono Invalido Introduce otro");
            } else {
                telefonoInvalido.setText("");
                InformacionDelRegistro.setText("");
                Usuario usuario = new Usuario();
                usuario.setEmail(email);
                usuario.setContrasenna(password);
                usuario.setNombre(nombre);
                String numeroLimpio = Utilidades.limpiarNumeroTelefono(numeroTelefono.getText());
                usuario.setNumeroTelefono(numeroLimpio);
                String ruta = "xml/usuarios.xml";
                File file = new File(ruta);
                TodosUsuarios usuarios = new TodosUsuarios();
                List<Usuario> usuariosRegistrados = new ArrayList<>();

                if (file.exists()) {
                    usuariosRegistrados = usuarios.getUsuarioList();
                    boolean encontrado = false;
                    for (Usuario usuario1 : usuariosRegistrados) {
                        if (usuario1.getNumeroTelefono().equals(usuario.getNumeroTelefono()) || usuario1.getEmail().equals(usuario.getEmail())) {
                            InformacionDelRegistro.setText("Este usuario ya existe en el sistema Inicia sesión");
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
    }else {
            telefonoInvalido.setText("Error Debes Introducir todos los campos");
        }


    }

    //Metodo que abre la ventana de inicio de sesion
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
    //Si el registro de sesion a sido exitoso abre la ventana de inicio de sesion
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
