package com.actividades.chatofflinep.controller;

import com.actividades.chatofflinep.dataAccess.XMLManager;
import com.actividades.chatofflinep.dataAccess.XMLManagerCollection;
import com.actividades.chatofflinep.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class PantallaCrearChatController {


    public TextField buscarContacto;
    public ListView<Contacto> listaContactos;


    public void initialize() {
        listaContactos.setItems(cargarLista());
        listaContactos.getSelectionModel().select(0);
        String carpetaRuta = "xml/chats";
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


    public void buscar(KeyEvent keyEvent) {
        String textoBusqueda = buscarContacto.getText().trim();

        List<Contacto> contactosFiltrados = UsuarioActualController.getInstance().getUsuario().getContactos().stream().filter(c -> c != null).filter(c -> c.getNumeroTelefono().contains(textoBusqueda)).collect(Collectors.toList());

        ObservableList<Contacto> contactosObservable = FXCollections.observableArrayList(contactosFiltrados);
        listaContactos.setItems(contactosObservable);


    }


    public void crearChat(MouseEvent mouseEvent) {
        Chat chat = new Chat();
        Usuario usuarioOriginal = UsuarioActualController.getInstance().getUsuario();
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioOriginal.getNombre());
        usuario.setNumeroTelefono(usuarioOriginal.getNumeroTelefono());
        usuario.setContrasenna(null);
        usuario.setContactos(null);

        String numeroTelefono = listaContactos.getSelectionModel().getSelectedItem().getNumeroTelefono();
        TodosUsuarios listaUsuarios = XMLManagerCollection.readXML(TodosUsuarios.class, "xml/usuarios.xml");
        List<Usuario> list = listaUsuarios.getUsuarioList();
        for (Usuario usuario1 : list) {
            if (usuario1.getNumeroTelefono().equals(numeroTelefono)) {
                Usuario usuario2 = new Usuario();
                usuario2.setNombre(usuario1.getNombre());
                usuario2.setNumeroTelefono(usuario1.getNumeroTelefono());
                usuario2.setContrasenna(null);
                usuario2.setContactos(null);
                chat.setUsuario2(usuario2);
                break;
            }

        }
        chat.setUsuario1(usuario);
        String archivoChat = "xml/chats/" + usuario.getNumeroTelefono() + "_" + chat.getUsuario2().getNumeroTelefono() + ".xml";
        File fileChat = new File(archivoChat);

        if (!fileChat.exists()) {
            XMLManager.writeXML(chat, archivoChat);
        }

        Stage stageActual = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

        stageActual.close();

    }

}
