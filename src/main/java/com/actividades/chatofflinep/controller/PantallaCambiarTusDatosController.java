package com.actividades.chatofflinep.controller;

import com.actividades.chatofflinep.dataAccess.XMLManagerCollection;
import com.actividades.chatofflinep.model.TodosUsuarios;
import com.actividades.chatofflinep.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class PantallaCambiarTusDatosController {


    @FXML
    private Label contrasennaIncorrecta;
    @FXML
    private TextField esContrasenna;
    @FXML
    private TextField contrasenna;


    public void initialize() {
        contrasenna.setDisable(true);
        contrasenna.setText("****");
    }


    public void actualizarDatos(MouseEvent mouseEvent) {
        if (!esContrasenna.getText().isEmpty()) {
            if (esContrasenna.getText().equals(UsuarioActualController.getInstance().getUsuario().getContrasenna())) {
                System.out.println(esContrasenna);
                System.out.println(UsuarioActualController.getInstance().getUsuario().getContrasenna());
                contrasennaIncorrecta.setText("Debes introducir la contraseña para editar tu perfil");
                String contrasennaCambiada = contrasenna.getText();

                String ruta = "xml/usuarios.xml";

                TodosUsuarios todosUsuarios = new TodosUsuarios();
                List<Usuario> usuariosRegistrados = todosUsuarios.getUsuarioList();

                boolean encontrado = false;

                for (Usuario usuario : usuariosRegistrados) {
                    if (usuario.getEmail().equals(UsuarioActualController.getInstance().getUsuario().getEmail())) {
                        usuario.setContrasenna(contrasennaCambiada);
                        encontrado = true;
                        break;
                    }
                }
                if (encontrado) {
                    todosUsuarios.setUsuarioList(usuariosRegistrados);
                    XMLManagerCollection.writeXML(todosUsuarios, ruta);
                }
                Stage stageActual = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                stageActual.close();

            }
        }


    }

    public void liberarContrasenna(MouseEvent mouseEvent) {
        if (esContrasenna.getText().equals(UsuarioActualController.getInstance().getUsuario().getContrasenna())) {
            contrasenna.setDisable(false);
            contrasenna.setText("");
            contrasennaIncorrecta.setText("");
        } else {
            contrasennaIncorrecta.setText("La Contraseña No Es Correcta");
        }


    }
}
