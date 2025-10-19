package com.actividades.chatofflinep.controller;

import com.actividades.chatofflinep.ChatOfflineAplication;
import com.actividades.chatofflinep.dataAccess.XMLManagerCollection;
import com.actividades.chatofflinep.model.Chat;
import com.actividades.chatofflinep.model.Contacto;
import com.actividades.chatofflinep.model.Mensaje;
import com.actividades.chatofflinep.model.Usuario;
import com.actividades.chatofflinep.utils.Utilidades;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PantallaInicialController {


    public ListView<Contacto> listContactos;
    public ImageView imagenAjustes;
    public MenuButton tresPuntos;
    public SplitPane splitPaneQuieto;
    public ListView<Chat> listViewChats;
    public TextField mensaje;
    public ImageView imagenAdjuntar;
    public TextField nombreDelCSV;
    public Label exportadoCSV;

    private List<Chat> list = new ArrayList<>();

    public void initialize() {
        File file = new File("imagenes/clip.jpg");
        Image image = new Image(file.toURI().toString());
        imagenAdjuntar.setImage(image);

        Platform.runLater(() -> {
            splitPaneQuieto.lookupAll(".split-pane-divider")
                    .forEach(div -> div.setMouseTransparent(true));
        });

        iniciarLista();

    }

    public void iniciarLista() {
        Usuario usuarioActual = UsuarioActualController.getInstance().getUsuario();
        list.clear();
        usuarioActual.getList().clear();

        File carpetaChats = new File("xml/chats");
        if (carpetaChats.exists() && carpetaChats.isDirectory()) {
            File[] archivos = carpetaChats.listFiles((dir, name) -> name.endsWith(".xml"));
            if (archivos != null) {
                for (File archivo : archivos) {
                    try {
                        Chat chat = XMLManagerCollection.readXML(Chat.class, archivo.getPath());
                        if (chat != null) {
                            list.add(chat);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        for (Chat chat : list) {
            if (chat.getUsuario1().getNumeroTelefono().equals(usuarioActual.getNumeroTelefono()) || chat.getUsuario2().getNumeroTelefono().equals(usuarioActual.getNumeroTelefono())) {
                usuarioActual.insertarChat(chat);
            }
        }

        actualizarListas(usuarioActual);

    }


    public void enviarMensaje(MouseEvent mouseEvent) {
        if (mensaje.getText().length() > 1) {
            String mensajeUsuario = mensaje.getText();
            Contacto contacto = listContactos.getSelectionModel().getSelectedItem();

            Usuario usuarioActual = UsuarioActualController.getInstance().getUsuario();

            Chat chatExistente = null;
            for (Chat chat : usuarioActual.getList()) {
                boolean esUsuario1 = chat.getUsuario1().getNumeroTelefono().equals(contacto.getNumeroTelefono());
                boolean esUsuario2 = chat.getUsuario2().getNumeroTelefono().equals(contacto.getNumeroTelefono());
                boolean esUsuario3 = chat.getUsuario1().getNumeroTelefono().equals(usuarioActual.getNumeroTelefono());
                boolean esUsuario4 = chat.getUsuario2().getNumeroTelefono().equals(usuarioActual.getNumeroTelefono());
                if ((esUsuario1 && esUsuario4) || (esUsuario2 && esUsuario3)) {
                    chatExistente = chat;
                    break;
                }
            }


            if (chatExistente != null) {
                chatExistente.agregarMensaje(UsuarioActualController.getInstance().getUsuario(), mensajeUsuario);
                mostrarChatsDelContacto(contacto);
            }

            listViewChats.refresh();
            try {
                if (chatExistente != null) {
                    String rutaArchivo = "xml/chats/" + chatExistente.getUsuario1().getNumeroTelefono() + "_" + chatExistente.getUsuario2().getNumeroTelefono() + ".xml";
                    XMLManagerCollection.writeXML(chatExistente, rutaArchivo);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            mensaje.setText("");

        }
    }


    public void actualizarListas(Usuario usuarioActual) {
        Set<Contacto> contactosConChats = new HashSet<>();
        for (Chat chat : usuarioActual.getList()) {
            Usuario otroUsuario = null;
            if (chat.getUsuario1().getNumeroTelefono().equals(usuarioActual.getNumeroTelefono())) {
                otroUsuario = chat.getUsuario2();
            } else if (chat.getUsuario2().getNumeroTelefono().equals(usuarioActual.getNumeroTelefono())) {
                otroUsuario = chat.getUsuario1();
            }

            if (otroUsuario != null) {
                Boolean encontrado = false;
                for (Contacto contacto : usuarioActual.getContactos()) {
                    if (contacto.getNumeroTelefono().equals(otroUsuario.getNumeroTelefono())) {
                        contactosConChats.add(contacto);
                        encontrado = true;
                        break;
                    }
                }
                if (!encontrado) {
                    Contacto contactoTemporal = new Contacto();
                    contactoTemporal.setNumeroTelefono(otroUsuario.getNumeroTelefono());
                    contactosConChats.add(contactoTemporal);
                }
            }
        }
        ObservableList<Contacto> observableList = FXCollections.observableArrayList();
        observableList.addAll(contactosConChats);
        listContactos.setItems(observableList);

    }

    public void cargarLista(MouseEvent mouseEvent) {
        iniciarLista();

    }


    public void crearChat(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ChatOfflineAplication.class.getResource("pantallaCrearChat.fxml"));
            Scene scene = null;
            scene = new Scene(fxmlLoader.load());

            Stage stage = new Stage();
            stage.setTitle("ChatOffline");
            stage.setScene(scene);
            stage.initOwner(listContactos.getScene().getWindow());
            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void cerrarSesion(ActionEvent actionEvent) {
        UsuarioActualController usuarioActualController = UsuarioActualController.getInstance();
        usuarioActualController.setUsuario(null);
        try {
            FXMLLoader loader = new FXMLLoader(ChatOfflineAplication.class.getResource("pantallaInicioSesion.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("ChatOffline");
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
            ((Stage) ((MenuItem) actionEvent.getSource()).getParentPopup().getOwnerWindow()).close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void menu(MouseEvent mouseEvent) {


    }

    public void contactos(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ChatOfflineAplication.class.getResource("pantallaContactos.fxml"));
            Scene scene = null;
            scene = new Scene(fxmlLoader.load());

            Stage stage = new Stage();
            stage.setTitle("ChatOffline");
            stage.setScene(scene);
            stage.initOwner(listContactos.getScene().getWindow());
            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private void mostrarChatsDelContacto(Contacto contacto) {
        Usuario usuarioActual = UsuarioActualController.getInstance().getUsuario();
        List<Chat> chatsDelContacto = new ArrayList<>();

        for (Chat chat : usuarioActual.getList()) {
            if (contacto != null) {
                if (chat.getUsuario1().getNumeroTelefono().equals(contacto.getNumeroTelefono()) || chat.getUsuario2().getNumeroTelefono().equals(contacto.getNumeroTelefono())) {
                    chatsDelContacto.add(chat);
                }
            }
        }

        ObservableList<Chat> observableChats = FXCollections.observableArrayList(chatsDelContacto);
        listViewChats.setItems(observableChats);
        if (!observableChats.isEmpty()) {
            listViewChats.getSelectionModel().selectFirst();
        }
    }

    public void cambiarConversacion(MouseEvent mouseEvent) {
        Contacto contactoSeleccionado = listContactos.getSelectionModel().getSelectedItem();
        if (contactoSeleccionado == null) {
            return;
        }
        mostrarChatsDelContacto(contactoSeleccionado);

    }

    public void adjuntarImagenesOArchivos(MouseEvent mouseEvent) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int resultado = fileChooser.showOpenDialog(null);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();

            File carpetaDestino = new File("archivosAdjuntos");
            if (!carpetaDestino.exists()) {
                carpetaDestino.mkdirs();
            }

            File archivoDestino = new File(carpetaDestino, archivoSeleccionado.getName());

            try {
                Files.copy(archivoSeleccionado.toPath(), archivoDestino.toPath(), StandardCopyOption.REPLACE_EXISTING);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Chat chat = listViewChats.getSelectionModel().getSelectedItem();
            List<Mensaje> mensajes = chat.getMensajes();
            Mensaje mensaje1 = new Mensaje();
            mensaje1.setEmisor(UsuarioActualController.getInstance().getUsuario().getNombre());
            if (mensaje.getText().isEmpty()){
                mensaje1.setTexto("");
            }else {
                mensaje1.setTexto(mensaje.getText());
            }

            mensaje1.insertarArchivo(archivoSeleccionado.getPath());
            mensajes.add(mensaje1);
            XMLManagerCollection.writeXML(chat, "xml/chats/"+chat.getUsuario1().getNumeroTelefono()+"_"+chat.getUsuario2().getNumeroTelefono()+".xml");

        }



    }

    public void Ajustes(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ChatOfflineAplication.class.getResource("pantallaDatosPersonales.fxml"));
            Scene scene = null;
            scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("ChatOffline");
            stage.setScene(scene);
            stage.initOwner(listContactos.getScene().getWindow());
            stage.setResizable(false);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void exportarCSV(MouseEvent mouseEvent) {
        String carpetaRuta = "xml/exportarCSV";
        File carpeta = new File(carpetaRuta);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        if (!nombreDelCSV.getText().isEmpty() && listViewChats.getSelectionModel().getSelectedItem() != null) {
            try {
                String nameFile = "xml/exportarCSV/" + nombreDelCSV.getText()+".csv";
                FileWriter fn = new FileWriter(nameFile);
                Chat chat = listViewChats.getSelectionModel().getSelectedItem();

                fn.write(chat.toString() + System.lineSeparator());
                exportadoCSV.setText("¡Exportado Con Exito!");

                fn.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }

    }


    public void quitar(MouseEvent mouseEvent) {
        exportadoCSV.setText("");

    }

    public void informacion(ActionEvent actionEvent) {
        if (listContactos.getSelectionModel().getSelectedItem() != null){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ChatOfflineAplication.class.getResource("pantallaInformacionChat.fxml"));
            Scene scene = null;
            scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("ChatOffline");
            stage.setScene(scene);
            PantallaInformacionChatController controller = fxmlLoader.getController();
            Chat chatSeleccionado = listViewChats.getSelectionModel().getSelectedItem();
            controller.setChat(chatSeleccionado);
            stage.initOwner(listContactos.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        }

    }
}