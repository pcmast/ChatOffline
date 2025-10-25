package com.actividades.chatofflinep.controller;

import com.actividades.chatofflinep.ChatOfflineAplication;
import com.actividades.chatofflinep.model.TodosUsuarios;
import com.actividades.chatofflinep.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PantallaInicioSesionController {
    @FXML
    private ImageView imagenLogo;
    @FXML
    private PasswordField txContrasenna;
    @FXML
    private TextField txCorreo;
    @FXML
    private Button btnInicioSesion;
    @FXML
    private Label txVacios;

    public void initialize() {
        File imagenRuta = new File("imagenes/mensajeria.png");
        Image image = new Image(imagenRuta.toURI().toString());
        imagenLogo.setImage(image);

        String carpetaRuta = "xml";
        File carpeta = new File(carpetaRuta);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

    }


    public void inicioSesion(MouseEvent mouseEvent) {
        boolean inicioSesionExitoso = false;

        if (!txCorreo.getText().isEmpty() && !txContrasenna.getText().isEmpty()) {
            try {
                txVacios.setText("");
                TodosUsuarios todosUsuarios = TodosUsuarios.getInstance();

                List<Usuario> list = todosUsuarios.getUsuarioList();

                for (Usuario usuario : list) {
                    if (usuario.getEmail().equals(txCorreo.getText())) {
                        if (usuario.getContrasenna().equals(txContrasenna.getText())) {
                            inicioSesionExitoso = true;
                            UsuarioActualController.getInstance().setUsuario(usuario);
                        }

                    }

                }

                if (inicioSesionExitoso) {
                    FXMLLoader fxmlLoader = new FXMLLoader(ChatOfflineAplication.class.getResource("pantallaInicial.fxml"));
                    Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                    Scene scene = null;
                    scene = new Scene(fxmlLoader.load());
                    currentStage.setResizable(true);
                    currentStage.setTitle("ChatOffline");
                    currentStage.setScene(scene);
                    currentStage.centerOnScreen();
                    currentStage.show();
                } else {
                    txVacios.setText("Tus correo o contraseña no son correctos");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            txVacios.setText("Debes Introducir Todos los datos");

        }

    }

    public void registrarse(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ChatOfflineAplication.class.getResource("pantallaRegistro.fxml"));
            Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            Scene scene = null;
            scene = new Scene(fxmlLoader.load());
            currentStage.setTitle("ChatOffline");
            currentStage.setResizable(false);
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
