package com.actividades.chatofflinep.controller;

import com.actividades.chatofflinep.ChatOfflineAplication;
import com.actividades.chatofflinep.dataAccess.XMLManagerCollection;
import com.actividades.chatofflinep.model.Contacto;
import com.actividades.chatofflinep.model.ContactosUsuario;
import com.actividades.chatofflinep.model.TodosUsuarios;
import com.actividades.chatofflinep.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PantallaCrearContactoController {
    @FXML
    private Button crearContacto;
    @FXML
    private ImageView imagenNombre;
    @FXML
    private ImageView imagenTelefono;
    @FXML
    private TextField telefono;
    @FXML
    private TextField apodo;
    @FXML
    private Label informacionContacto;
    @FXML
    private Button editarContacto;
    @FXML
    private Button eliminarContacto;
    private Contacto contacto = new Contacto();



    public void initialize() {
        File foto1 = new File("imagenes/persona.png");
        File foto2 = new File("imagenes/telefono.png");
        Image image1 = new Image(foto1.toURI().toString());
        Image image2 = new Image(foto2.toURI().toString());
        imagenNombre.setImage(image1);
        imagenTelefono.setImage(image2);
        habilitarEdicion();

        editarContacto.setDisable(true);
        eliminarContacto.setDisable(true);
        crearContacto.setDisable(false);


    }

    public Contacto getContacto() {
        return contacto;
    }

    public void setContacto(Contacto contacto) {
        this.contacto = contacto;
    }

    public void crearContacto(MouseEvent mouseEvent) {
        boolean existe = false;
        Contacto contacto = new Contacto();
        contacto.setApodo(apodo.getText());
        contacto.setNumeroTelefono(telefono.getText());
        contacto.getUsuario().setNumeroTelefono(UsuarioActualController.getInstance().getUsuario().getNumeroTelefono());
        File file = new File("xml/contactosUsuario/" + UsuarioActualController.getInstance().getUsuario().getNumeroTelefono());
        List<Usuario> usuarios = TodosUsuarios.getInstance().getUsuarios();
        if (!UsuarioActualController.getInstance().getUsuario().getNumeroTelefono().equals(contacto.getNumeroTelefono())){
        for (Usuario usuario : usuarios) {
            if (usuario.getNumeroTelefono().equals(contacto.getNumeroTelefono())) {
                existe = true;
            }
        }
        if (existe) {
            List<Contacto> contactos = UsuarioActualController.getInstance().getUsuario().getContactos();
            if (!contactos.contains(contacto)) {
                String rutaArchivo = "xml/contactosUsuario/" + UsuarioActualController.getInstance().getUsuario().getNumeroTelefono() + ".xml";
                boolean existeContacto = contactos.stream().anyMatch(c -> c.getNumeroTelefono().equals(contacto.getNumeroTelefono()));
                if (!existeContacto) {
                    ContactosUsuario contenedor;
                    if (!file.exists()) {
                        contenedor = new ContactosUsuario();
                    } else {
                        contenedor = XMLManagerCollection.readXML(ContactosUsuario.class, rutaArchivo);

                    }
                    contactos.add(contacto);
                    contenedor.setContactos(contactos);
                    XMLManagerCollection.writeXML(contenedor, rutaArchivo);

                    pantallaAnterior(mouseEvent);
                }

            }
            boolean existeContacto = contactos.stream().anyMatch(c -> c.getNumeroTelefono().equals(contacto.getNumeroTelefono()));
            if (existeContacto){
                informacionContacto.setText("Contacto ya existe en tus contactos");
            }
        }else {
            informacionContacto.setText("No existe este numero");
        }

        }else {
        informacionContacto.setText("No puedes introducir tu numero de telefono");
        }

    }


    public void asignarValores() {
        telefono.setText(contacto.getNumeroTelefono());
        apodo.setText(contacto.getApodo());

    }

    public void habilitarEdicion() {
        this.editarContacto.setDisable(false);
        this.eliminarContacto.setDisable(false);
        this.crearContacto.setDisable(true);
    }

    public void editar(MouseEvent mouseEvent) {
        contacto.setNumeroTelefono(telefono.getText());
        contacto.setApodo(apodo.getText());
        UsuarioActualController.getInstance().getUsuario().editarContacto(contacto);

        if (telefono.getText().isEmpty() || apodo.getText().isEmpty()) {
            informacionContacto.setText("Debes Introducir todos los datos");
        } else {
            pantallaAnterior(mouseEvent);
        }
    }

    public void eliminarContacto(MouseEvent mouseEvent) {
        UsuarioActualController.getInstance().getUsuario().eliminarContacto(contacto);
        pantallaAnterior(mouseEvent);


    }

    public void pantallaAnterior(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ChatOfflineAplication.class.getResource("pantallaContactos.fxml"));
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


}