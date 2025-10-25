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

        Map<String, Long> mensajesPorUsuario = mensajesPorUsuario(mensajes);

        String telefono1 = chat.getUsuario1().getNumeroTelefono() == null ? "" : chat.getUsuario1().getNumeroTelefono().trim();
        String telefono2 = chat.getUsuario2().getNumeroTelefono() == null ? "" : chat.getUsuario2().getNumeroTelefono().trim();

        long mensajesUsuario1 = mensajes.stream()
                .filter(m -> telefono1.equals(m.getEmisor()))
                .count();

        long mensajesUsuario2 = mensajes.stream()
                .filter(m -> telefono2.equals(m.getEmisor()))
                .count();



        numeroMensajesUsuario1.setText("Mensajes de " + chat.getUsuario1().getNombre() + ": " + mensajesUsuario1);
        numeroMensajesUsuario2.setText("Mensajes de " + chat.getUsuario2().getNombre() + ": " + mensajesUsuario2);

        String palabraMasRepetida = mensajes.stream()
                .map(Mensaje::getTexto)
                .filter(t -> t != null && !t.trim().isEmpty()) // Ignoramos vacíos o null
                .flatMap(t -> Arrays.stream(t.split("\\s+")))
                .map(String::toLowerCase)
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Sin palabras");
        palabraRepetida.setText(palabraMasRepetida);

        palabraRepetida.setText(palabraMasRepetida);

        Mensaje mensajeLargo = mensajes.stream()
                .max(Comparator.comparingInt(m -> m.getTexto().length()))
                .orElse(new Mensaje("", ""));

        mensajeMasLargo.setText(mensajeLargo.getTexto());

        String primeroDeLosMensajes = mensajes.stream().findFirst().map(m -> {
                    if (m.getTexto() != null && !m.getTexto().trim().isEmpty()) return m.getTexto();
                    if (m.getRuta() != null) return "Archivo adjunto";
                    return "(mensaje vacío)";}).orElse("Sin mensajes");


        String ultimoDeLosMensajes = mensajes.stream().reduce((first, second) -> second).map(m -> {
                    if (m.getTexto() != null && !m.getTexto().trim().isEmpty()) return m.getTexto();
                    if (m.getRuta() != null) return "Archivo adjunto";return "(mensaje vacío)";}).orElse("Sin mensajes");

        primerMensaje.setText(primeroDeLosMensajes);
        ultimoMensaje.setText(ultimoDeLosMensajes);
    }

    public static Map<String, Long> mensajesPorUsuario(List<Mensaje> mensajes) {
        return mensajes.stream().collect(Collectors.groupingBy(Mensaje::getEmisor, Collectors.counting()));
    }

}
