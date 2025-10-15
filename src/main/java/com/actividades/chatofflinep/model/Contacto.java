package com.actividades.chatofflinep.model;

import jakarta.xml.bind.annotation.XmlElement;

public class Contacto {

    @XmlElement
    private String nombre;
    @XmlElement
    private String apodo;
    @XmlElement
    private String numeroTelefono;
    @XmlElement
    private String email;
    @XmlElement
    private String contrasenna;
    @XmlElement
    private String estadoLinea;

    public Contacto(String nombre, String apodo, String numeroTelefono, String email, String contrasenna, String estadoLinea) {
        this.nombre = nombre;
        this.apodo = apodo;
        this.numeroTelefono = numeroTelefono;
        this.email = email;
        this.contrasenna = contrasenna;
        this.estadoLinea = estadoLinea;
    }
    public Contacto() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenna() {
        return contrasenna;
    }

    public void setContrasenna(String contrasenna) {
        this.contrasenna = contrasenna;
    }

    public String getEstadoLinea() {
        return estadoLinea;
    }

    public void setEstadoLinea(String estadoLinea) {
        this.estadoLinea = estadoLinea;
    }
}
