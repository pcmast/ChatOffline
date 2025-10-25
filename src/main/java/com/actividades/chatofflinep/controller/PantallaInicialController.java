package com.actividades.chatofflinep.controller;

import com.actividades.chatofflinep.ChatOfflineAplication;
import com.actividades.chatofflinep.dataAccess.XMLManagerCollection;
import com.actividades.chatofflinep.model.*;
import com.actividades.chatofflinep.utils.Utilidades;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class PantallaInicialController {


    public ListView<Contacto> listContactos;
    public ImageView imagenAjustes;
    public MenuButton tresPuntos;
    public SplitPane splitPaneQuieto;
    public ListView<Mensaje> listViewChats;
    public TextField mensaje;
    public ImageView imagenAdjuntar;
    public TextField nombreDelCSV;
    public Label exportadoCSV;
    public ImageView exportarAZip;
    public Label exportadoAZIP;


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
        File file1 = new File("imagenes/zip.png");
        Image image1 = new Image(file1.toURI().toString());
        exportarAZip.setImage(image1);

        listViewChats.setCellFactory(param -> new ListCell<Mensaje>() {
            private final ImageView imageView = new ImageView();
            private final Label labelMensaje = new Label();
            private final Label labelHora = new Label();

            private final HBox horaContainer = new HBox(new Region(), labelHora);
            private final VBox vbox = new VBox(labelMensaje, imageView, horaContainer);

            {
                vbox.setSpacing(5);
                vbox.setFillWidth(true);

                imageView.setPreserveRatio(true);
                imageView.setFitWidth(300);
                imageView.setVisible(false);
                imageView.setManaged(false);

                labelMensaje.setWrapText(true);
                labelMensaje.setMaxWidth(320);
                labelMensaje.setStyle("-fx-padding: 5 8 5 8;");

                labelHora.setStyle("-fx-font-size: 10px; -fx-text-fill: gray;");
                HBox.setHgrow(horaContainer.getChildren().get(0), Priority.ALWAYS);

                vbox.setStyle("-fx-background-radius: 10; -fx-padding: 6;");
            }

            @Override
            protected void updateItem(Mensaje mensaje, boolean empty) {
                super.updateItem(mensaje, empty);

                if (empty || mensaje == null) {
                    setGraphic(null);
                    setText(null);
                    setStyle("");
                    return;
                }

                Usuario usuarioActual = UsuarioActualController.getInstance().getUsuario();
                String nombreMostrar = mensaje.getEmisor();

                if (mensaje.getEmisor().equals(usuarioActual.getNumeroTelefono())) {
                    nombreMostrar = usuarioActual.getNombre();
                } else {
                    for (Contacto contacto : usuarioActual.getContactos()) {
                        if (contacto.getNumeroTelefono().equals(mensaje.getEmisor())) {
                            nombreMostrar = contacto.getApodo();
                            break;
                        }
                    }
                }

                labelMensaje.setText(nombreMostrar + ": " + (mensaje.getTexto() != null ? mensaje.getTexto() : ""));

                String ruta = mensaje.getRuta();
                if (ruta != null && !ruta.trim().isEmpty()) {
                    File archivo = new File(ruta.trim());

                    if (archivo.exists() && archivo.isFile()) {
                        String nombreArchivo = archivo.getName().toLowerCase();

                        if (nombreArchivo.endsWith(".png") || nombreArchivo.endsWith(".jpg")
                                || nombreArchivo.endsWith(".jpeg") || nombreArchivo.endsWith(".gif")) {

                            Image img = new Image(archivo.toURI().toString(), 300, 0, true, true);
                            imageView.setImage(img);
                            imageView.setVisible(true);
                            imageView.setManaged(true);
                            labelMensaje.setOnMouseClicked(null);
                        } else {
                            imageView.setImage(null);
                            imageView.setVisible(false);
                            imageView.setManaged(false);

                            labelMensaje.setText(nombreMostrar + ": " + archivo.getName());
                            labelMensaje.setStyle("-fx-padding: 8; -fx-background-color: #d0d0d0; "
                                    + "-fx-border-color: gray; -fx-border-radius: 5; -fx-background-radius: 5;");

                            labelMensaje.setOnMouseClicked(e -> {
                                try {
                                    FileChooser fileChooser = new FileChooser();
                                    fileChooser.setTitle("Guardar archivo como...");
                                    fileChooser.setInitialFileName(archivo.getName());
                                    fileChooser.getExtensionFilters().add(
                                            new FileChooser.ExtensionFilter("Todos los archivos (*.*)", "*.*")
                                    );
                                    File destino = fileChooser.showSaveDialog(labelMensaje.getScene().getWindow());
                                    if (destino != null) {
                                        try (InputStream in = new FileInputStream(archivo);
                                             OutputStream out = new FileOutputStream(destino)) {
                                            byte[] buffer = new byte[8192];
                                            int bytesLeidos;
                                            while ((bytesLeidos = in.read(buffer)) != -1) {
                                                out.write(buffer, 0, bytesLeidos);
                                            }
                                        }
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            });
                        }
                    } else {
                        imageView.setImage(null);
                        imageView.setVisible(false);
                        imageView.setManaged(false);

                        labelMensaje.setText(nombreMostrar + ": [Archivo no encontrado]");
                        labelMensaje.setStyle("-fx-padding: 8; -fx-background-color: #ffcccc; "+"-fx-border-color: red; -fx-border-radius: 5; -fx-background-radius: 5;");

                        labelMensaje.setOnMouseClicked(null);
                    }
                } else {
                    imageView.setImage(null);
                    imageView.setVisible(false);
                    imageView.setManaged(false);
                    labelMensaje.setOnMouseClicked(null);
                    labelMensaje.setStyle("-fx-padding: 5 8 5 8;");
                }

                labelHora.setText(mensaje.getHoraDeEnvio() != null ? mensaje.getHoraDeEnvio() : "");

                if (mensaje.getEmisor().equals(usuarioActual.getNumeroTelefono())) {
                    vbox.setStyle("-fx-background-color: #a5e8a3; -fx-background-radius: 10; -fx-padding: 6;");
                    vbox.setAlignment(Pos.CENTER_RIGHT);
                } else {
                    vbox.setStyle("-fx-background-color: #e6e6e6; -fx-background-radius: 10; -fx-padding: 6;");
                    vbox.setAlignment(Pos.CENTER_LEFT);
                }

                vbox.setMaxWidth(Region.USE_PREF_SIZE);
                vbox.setPrefWidth(Region.USE_COMPUTED_SIZE);
                vbox.setMinHeight(Region.USE_PREF_SIZE);

                setGraphic(vbox);
            }
        });

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
            LocalTime localTime = LocalTime.now();

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
                chatExistente.agregarMensaje(UsuarioActualController.getInstance().getUsuario(), mensajeUsuario,localTime);
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
            File imagenURL = new File("imagenes/iconoApp.png");
            Image image = new Image(imagenURL.toURI().toString());
            stage.getIcons().add(image);
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
            File imagenURL = new File("imagenes/iconoApp.png");
            Image image = new Image(imagenURL.toURI().toString());
            stage.getIcons().add(image);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
            ((Stage) ((MenuItem) actionEvent.getSource()).getParentPopup().getOwnerWindow()).close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


    public void contactos(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ChatOfflineAplication.class.getResource("pantallaContactos.fxml"));
            Scene scene = null;
            scene = new Scene(fxmlLoader.load());

            Stage stage = new Stage();
            stage.setTitle("ChatOffline");
            stage.setScene(scene);
            File imagenURL = new File("imagenes/iconoApp.png");
            Image image = new Image(imagenURL.toURI().toString());
            stage.getIcons().add(image);
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

        for (Chat chat : usuarioActual.getList()) {
            if (contacto != null) {
                if (chat.getUsuario1().getNumeroTelefono().equals(contacto.getNumeroTelefono()) || chat.getUsuario2().getNumeroTelefono().equals(contacto.getNumeroTelefono())) {

                    ObservableList<Mensaje> observableMensajes = FXCollections.observableArrayList(chat.getMensajes());
                    listViewChats.setItems(observableMensajes);
                    if (!observableMensajes.isEmpty()) {
//                        listViewChats.getSelectionModel().selectLast();
                        Platform.runLater(() -> listViewChats.scrollTo(observableMensajes.size() - 1));
                    }
                    break;
                }
            }
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

        if(listContactos.getSelectionModel().getSelectedItem() != null) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar archivo o imagen");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Todos los archivos", "*.*"),
                    new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg", "*.gif")
            );

            File archivoSeleccionado = fileChooser.showOpenDialog(((Node) mouseEvent.getSource()).getScene().getWindow());
            if (archivoSeleccionado != null) {
                File carpetaDestino = new File("media");
                if (!carpetaDestino.exists()) {
                    carpetaDestino.mkdirs();
                }

                File archivoDestino = new File(carpetaDestino, archivoSeleccionado.getName());

                try (InputStream in = new FileInputStream(archivoSeleccionado);
                     OutputStream out = new FileOutputStream(archivoDestino)) {

                    byte[] buffer = new byte[4096];
                    int bytesLeidos;


                    while ((bytesLeidos = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesLeidos);
                    }
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Contacto contactoSeleccionado = listContactos.getSelectionModel().getSelectedItem();
                if (contactoSeleccionado == null) return;

                Chat chat = null;
                Usuario usuarioActual = UsuarioActualController.getInstance().getUsuario();
                for (Chat c : usuarioActual.getList()) {
                    if (c.getUsuario1().getNumeroTelefono().equals(contactoSeleccionado.getNumeroTelefono()) ||
                            c.getUsuario2().getNumeroTelefono().equals(contactoSeleccionado.getNumeroTelefono())) {
                        chat = c;
                        break;
                    }
                }
                if (chat == null) return;

                List<Mensaje> mensajes = chat.getMensajes();
                Mensaje mensaje1 = new Mensaje();
                mensaje1.setEmisor(UsuarioActualController.getInstance().getUsuario().getNumeroTelefono());
                if (mensaje.getText().isEmpty()) {
                    mensaje1.setTexto("");
                } else {
                    mensaje1.setTexto(mensaje.getText());
                }
                String rutaHaciaProyecto = "media/" + archivoSeleccionado.getName();
                mensaje1.setRuta(rutaHaciaProyecto);
                mensajes.add(mensaje1);

                XMLManagerCollection.writeXML(chat, "xml/chats/" + chat.getUsuario1().getNumeroTelefono() + "_" + chat.getUsuario2().getNumeroTelefono() + ".xml");

                listViewChats.setItems(FXCollections.observableArrayList(mensajes));
                listViewChats.refresh();
            }
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
            File imagenURL = new File("imagenes/iconoApp.png");
            Image image = new Image(imagenURL.toURI().toString());
            stage.getIcons().add(image);

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

        if (!nombreDelCSV.getText().isEmpty() && listContactos.getSelectionModel().getSelectedItem() != null) {
            try {
                Contacto contactoSeleccionado = listContactos.getSelectionModel().getSelectedItem();
                Usuario usuarioActual = UsuarioActualController.getInstance().getUsuario();
                Chat chatSeleccionado = null;

                for (Chat chat : usuarioActual.getList()) {
                    if (chat.getUsuario1().getNumeroTelefono().equals(contactoSeleccionado.getNumeroTelefono()) ||
                            chat.getUsuario2().getNumeroTelefono().equals(contactoSeleccionado.getNumeroTelefono())) {
                        chatSeleccionado = chat;
                        break;
                    }
                }

                if (chatSeleccionado != null) {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Guardar chat como CSV");
                    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo CSV (*.csv)", "*.csv"));
                    fileChooser.setInitialFileName(nombreDelCSV.getText() + ".csv");

                    File archivoDestino = fileChooser.showSaveDialog(((Node) mouseEvent.getSource()).getScene().getWindow());
                    if (archivoDestino == null) {
                        return;
                    }

                    FileWriter file = new FileWriter(archivoDestino);
                    file.write(chatSeleccionado.toString() + System.lineSeparator());
                    file.close();

                    exportadoCSV.setText("¡Exportado Con Éxito!");
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public void quitarCSV(MouseEvent mouseEvent) {
        exportadoCSV.setText("");

    }

    public void informacion(ActionEvent actionEvent) {
        if (listContactos.getSelectionModel().getSelectedItem() != null){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ChatOfflineAplication.class.getResource("pantallaInformacionChat.fxml"));
            Scene scene = null;
            scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            File imagenURL = new File("imagenes/iconoApp.png");
            Image image = new Image(imagenURL.toURI().toString());
            stage.getIcons().add(image);
            stage.setResizable(false);
            stage.setTitle("ChatOffline");
            stage.setScene(scene);
            PantallaInformacionChatController controller = fxmlLoader.getController();

            Contacto contactoSeleccionado = listContactos.getSelectionModel().getSelectedItem();
            Usuario usuarioActual = UsuarioActualController.getInstance().getUsuario();
            Chat chatSeleccionado = null;

            for (Chat chat : usuarioActual.getList()) {
                if (chat.getUsuario1().getNumeroTelefono().equals(contactoSeleccionado.getNumeroTelefono()) ||
                        chat.getUsuario2().getNumeroTelefono().equals(contactoSeleccionado.getNumeroTelefono())) {
                    chatSeleccionado = chat;
                    break;
                }
            }
            controller.setChat(chatSeleccionado);
            stage.initOwner(listContactos.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        }

    }

    public void descargarZIP(MouseEvent mouseEvent) {
        try {
            Contacto contactoSeleccionado = listContactos.getSelectionModel().getSelectedItem();
            if (contactoSeleccionado != null) {
                Usuario usuarioActual = UsuarioActualController.getInstance().getUsuario();
                Chat chatSeleccionado = null;

                for (Chat chat : usuarioActual.getList()) {
                    if (chat.getUsuario1().getNumeroTelefono().equals(contactoSeleccionado.getNumeroTelefono()) ||
                            chat.getUsuario2().getNumeroTelefono().equals(contactoSeleccionado.getNumeroTelefono())) {
                        chatSeleccionado = chat;
                        break;
                    }
                }

                String numeroTelefonoUsuario1 = chatSeleccionado.getUsuario1().getNumeroTelefono();
                String numeroTelefonoUsuario2 = chatSeleccionado.getUsuario2().getNumeroTelefono();

                String contenidoCSV = chatSeleccionado.toString();
                byte[] data = contenidoCSV.getBytes();

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Guardar chat como ZIP");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo ZIP (*.zip)", "*.zip"));
                fileChooser.setInitialFileName(numeroTelefonoUsuario1 + "_" + numeroTelefonoUsuario2 + ".zip");

                File archivoDestino = fileChooser.showSaveDialog(((Node) mouseEvent.getSource()).getScene().getWindow());
                if (archivoDestino == null) {
                    return;
                }


                FileOutputStream fos = new FileOutputStream(archivoDestino);
                ZipOutputStream zipOut = new ZipOutputStream(fos);

                ZipEntry zipEntry = new ZipEntry(numeroTelefonoUsuario1 + "_" + numeroTelefonoUsuario2 + ".txt");
                zipOut.putNextEntry(zipEntry);
                zipOut.write(data, 0, data.length);
                zipOut.closeEntry();

                for (Mensaje mensaje : chatSeleccionado.getMensajes()) {
                    if (mensaje.getRuta() != null && !mensaje.getRuta().isEmpty()) {
                        File archivo = new File(mensaje.getRuta());
                        if (archivo.exists() && archivo.isFile()) {
                            ZipEntry archivoEntry = new ZipEntry(archivo.getName());
                            zipOut.putNextEntry(archivoEntry);
                        }
                    }
                }
                zipOut.close();
                fos.close();


                exportadoAZIP.setText("Exportado a ZIP Correctamente");


            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    public void quitarZIP(MouseEvent mouseEvent) {
        exportadoAZIP.setText("");

    }
}