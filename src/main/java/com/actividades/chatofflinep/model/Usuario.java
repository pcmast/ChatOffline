package com.actividades.chatofflinep.model;

import com.actividades.chatofflinep.utils.Utilidades;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import javafx.fxml.FXML;

import java.io.Serializable;
import java.util.List;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name =  "TodosUsuarios")
public class Usuario implements Serializable {
    @XmlElement
    private String nombre;
    @XmlElement
    private String numeroTelefono;
    @XmlElement
    private String email;
    @XmlElement
    private String contrasenna;
    @XmlElement
    private String estadoLinea;
    @XmlElement
    private List<Usuario> contactos;

    public Usuario(String nombre, String numeroTelefono, String email, String contrasenna, String estadoLinea, List<Usuario> contactos) {
        this.nombre = nombre;
        this.numeroTelefono = numeroTelefono;
        this.email = email;
        this.contrasenna = contrasenna;
        this.estadoLinea = estadoLinea;
        this.contactos = contactos;
    }

    public Usuario() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getEstadoLinea() {
        return estadoLinea;
    }

    public void setEstadoLinea(String estadoLinea) {
        this.estadoLinea = estadoLinea;
    }

    public boolean annadirContacto(String telefono){
        boolean annadido = Utilidades.esNumeroDeTelefono(telefono);


        return annadido;
    }

    public List<Usuario> getContactos() {
        return contactos;
    }

    public void setContactos(List<Usuario> contactos) {
        this.contactos = contactos;
    }

    public String getContrasenna() {
        return contrasenna;
    }

    public void setContrasenna(String contrasenna) {
        this.contrasenna = contrasenna;
    }
}
