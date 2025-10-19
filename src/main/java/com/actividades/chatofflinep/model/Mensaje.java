package com.actividades.chatofflinep.model;

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

    @XmlElementWrapper(name = "adjunto")
    @XmlElement(name = "ruta")
    private List<String> archivos = new ArrayList<>();


    public Mensaje() {
    }

    public Mensaje(String emisor, String texto) {
        this.emisor = emisor;
        this.texto = texto;
    }

    public Mensaje(String emisor, String texto, List<String> archivos) {
        this.emisor = emisor;
        this.texto = texto;
        this.archivos = archivos;
    }

    public void insertarArchivo(String archivo){
    archivos.add(archivo);

    }

    public List<String> getArchivos() {
        return archivos;
    }
    public void setArchivos(List<String> archivos) {
        this.archivos = archivos;
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
        return emisor + ": " + texto;
    }


}

