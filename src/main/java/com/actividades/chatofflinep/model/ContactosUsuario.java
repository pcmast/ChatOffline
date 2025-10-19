package com.actividades.chatofflinep.model;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "ContactosUsuario")
@XmlAccessorType(XmlAccessType.FIELD)
public class ContactosUsuario {

    @XmlElement(name = "contacto")
    private List<Contacto> contactos = new ArrayList<>();

    public ContactosUsuario() { }

    public ContactosUsuario(List<Contacto> contactos) {
        this.contactos = contactos;
    }

    public List<Contacto> getContactos() {
        return contactos;
    }

    public void setContactos(List<Contacto> contactos) {
        this.contactos = contactos;
    }
}