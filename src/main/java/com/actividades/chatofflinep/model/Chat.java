package com.actividades.chatofflinep.model;

import com.actividades.chatofflinep.dataAccess.XMLManagerCollection;

import java.util.ArrayList;
import java.util.List;

public class Chat {

    private String nombreUsuario1;
    private String nombreUsuario2;

    private List<String> conversacion = new ArrayList<>();

    public Chat(String nombreUsuario1, String nombreUsuario2, List<String> conversacion) {
        this.nombreUsuario1 = nombreUsuario1;
        this.nombreUsuario2 = nombreUsuario2;
        this.conversacion = conversacion;
    }

    public Chat() {


    }

    public String getNombreUsuario1() {
        return nombreUsuario1;
    }

    public void setNombreUsuario1(String nombreUsuario1) {
        this.nombreUsuario1 = nombreUsuario1;
    }

    public String getNombreUsuario2() {
        return nombreUsuario2;
    }

    public void setNombreUsuario2(String nombreUsuario2) {
        this.nombreUsuario2 = nombreUsuario2;
    }

    public List<String> getConversacion() {


        return conversacion;
    }

    public void setConversacion(List<String> conversacion) {
        this.conversacion = conversacion;
    }
}
