package com.actividades.chatofflinep.controller;

import com.actividades.chatofflinep.model.Chat;
import com.actividades.chatofflinep.model.Mensaje;
import com.actividades.chatofflinep.model.Usuario;
import javafx.scene.control.Label;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PantallaInformacionChatController {


    public Label numeroMensajes;
    public Label mensajesUsuario1;
    public Label mensajesUsuario2;
    public Label palabraRepetida;
    public Label numeroMensajesUsuario1;
    public Label numeroMensajesUsuario2;
    public Label mensajeMasLargo;
    public Label primerMensaje;
    public Label ultimoMensaje;

    private Chat chat;

    public void initialize() {
        numeroMensajesUsuario1.setText("Mensajes de ");

    }


    public void setChat(Chat chat) {
        this.chat = chat;
        if (chat != null) {
            analizarChat();
        }
    }

    private void analizarChat() {
        List<Mensaje> mensajes = chat.getMensajes();

        long totalMensajes = mensajes.stream().count();
        numeroMensajes.setText(String.valueOf(totalMensajes));


        long totalMensajess = mensajes.size();
        numeroMensajes.setText(String.valueOf(totalMensajess));


        Map<String, Long> mensajesPorUsuario = mensajes.stream()
                .collect(Collectors.groupingBy(
                        m -> m.getEmisor(),
                        Collectors.counting()
                ));

        long mensajesUsuario1 = mensajesPorUsuario.getOrDefault(chat.getUsuario1().getNombre(), 0L);
        long mensajesUsuario2 = mensajesPorUsuario.getOrDefault(chat.getUsuario2().getNombre(), 0L);



        numeroMensajesUsuario1.setText("Mensajes de " + chat.getUsuario1().getNombre() + ": " + mensajesUsuario1);
        numeroMensajesUsuario2.setText("Mensajes de " + chat.getUsuario2().getNombre() + ": " + mensajesUsuario2);

        String palabraMasRepetida = mensajes.stream()
                .flatMap(m -> Arrays.stream(m.getTexto().split("\\s+")))
                .map(String::toLowerCase)
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("");

        palabraRepetida.setText(palabraMasRepetida);


        Mensaje mensajeLargo = mensajes.stream()
                .max(Comparator.comparingInt(m -> m.getTexto().length()))
                .orElse(new Mensaje("",""));

        mensajeMasLargo.setText(mensajeLargo.getTexto());

        String primeroDeLosMensajes = mensajes.stream().findFirst().map(Mensaje::getTexto).orElse("");

        String ultimoDeLosMensajes = mensajes.stream().reduce((first, second) -> second).map(Mensaje::getTexto).orElse("");

        primerMensaje.setText(primeroDeLosMensajes);
        ultimoMensaje.setText(ultimoDeLosMensajes);
    }


}
