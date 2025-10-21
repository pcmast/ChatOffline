package com.actividades.chatofflinep.model;

import com.actividades.chatofflinep.controller.UsuarioActualController;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;

import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Mensaje {

    @XmlElement(name = "emisor")
    private String emisor;

    @XmlElement(name = "texto")
    private String texto;

    @XmlElement(name = "ruta")
    private String ruta = "";


    public Mensaje() {
    }

    public Mensaje(String emisor, String texto) {
        this.emisor = emisor;
        this.texto = texto;
    }

    public Mensaje(String emisor, String texto, String ruta) {
        this.emisor = emisor;
        this.texto = texto;
        this.ruta = ruta;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        // Obtener usuario actual
        Usuario usuarioActual = UsuarioActualController.getInstance().getUsuario();

        String nombreEmisor = emisor;

        if (emisor.equals(usuarioActual.getNumeroTelefono()) || emisor.equals(usuarioActual.getNombre())) {
            nombreEmisor = usuarioActual.getNombre();
        } else {
            // Buscar en los contactos del usuario actual
            for (Contacto c : usuarioActual.getContactos()) {
                if (c.getNumeroTelefono().equals(emisor)) {
                    nombreEmisor = c.getApodo();
                    break;
                }
            }
        }

        return nombreEmisor + ": " + texto;
    }

}

