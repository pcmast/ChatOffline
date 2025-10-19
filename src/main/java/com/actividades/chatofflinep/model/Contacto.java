package com.actividades.chatofflinep.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Objects;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Contacto {

    @XmlElement
    private String apodo;
    @XmlElement
    private String numeroTelefono;

    @XmlElement
    private String estadoLinea;

    @XmlElement
    private Usuario usuario = new Usuario();


    public Contacto(String apodo, String numeroTelefono, String estadoLinea) {
        this.apodo = apodo;
        this.numeroTelefono = numeroTelefono;
        this.estadoLinea = estadoLinea;
    }
    public Contacto() {
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

    public String getEstadoLinea() {
        return estadoLinea;
    }

    public void setEstadoLinea(String estadoLinea) {
        this.estadoLinea = estadoLinea;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Contacto contacto = (Contacto) o;
        return Objects.equals(numeroTelefono, contacto.numeroTelefono);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(numeroTelefono);
    }

    @Override
    public String toString() {
        if (apodo != null && !apodo.isEmpty()) {
            return apodo + " - Teléfono: " + numeroTelefono;
        } else {
            return "Teléfono: " + numeroTelefono;
        }
    }
}
