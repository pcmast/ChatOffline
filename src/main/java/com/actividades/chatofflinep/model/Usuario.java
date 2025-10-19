package com.actividades.chatofflinep.model;

import com.actividades.chatofflinep.dataAccess.XMLManagerCollection;
import com.actividades.chatofflinep.utils.Utilidades;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import javafx.fxml.FXML;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private List<Contacto> contactos = new ArrayList<>();
    @XmlElement
    private List<Chat> list = new ArrayList<>();

    public Usuario(String nombre, String numeroTelefono, String email, String contrasenna, String estadoLinea, List<Contacto> contactos, List<Chat> list) {
        this.nombre = nombre;
        this.numeroTelefono = numeroTelefono;
        this.email = email;
        this.contrasenna = contrasenna;
        this.estadoLinea = estadoLinea;
        this.contactos = contactos;
        this.list = list;
    }

    public Usuario() {
    }
    public void insertarChat(Chat chat){
        list.add(chat);

    }

    public List<Chat> getList() {
        return list;
    }

    public void setList(List<Chat> list) {
        this.list = list;
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

    public List<Contacto> getContactos() {
        File file = new File("xml/contactosUsuario/"+getNumeroTelefono()+".xml");
        List<Contacto> list = new ArrayList<>();
        ContactosUsuario contactosUsuario = new ContactosUsuario();
        if (file.exists()){
           contactosUsuario = XMLManagerCollection.readXML(ContactosUsuario.class,"xml/contactosUsuario/"+getNumeroTelefono()+".xml");
        }
            contactos = contactosUsuario.getContactos();
        return contactos;
    }

    public void editarContacto(Contacto contactoEditado){
        String ruta = "xml/contactosUsuario/"+getNumeroTelefono()+".xml";
        File file = new File(ruta);
        ContactosUsuario contactosUsuario = XMLManagerCollection.readXML(ContactosUsuario.class, ruta);
        List<Contacto> list = contactosUsuario.getContactos();
        for (int i = 0; i < list.size(); i++) {
            Contacto c = list.get(i);
            if (c.getNumeroTelefono().equals(contactoEditado.getNumeroTelefono())) {
                list.set(i, contactoEditado);
                break;
            }
        }

        contactosUsuario.setContactos(list);
        XMLManagerCollection.writeXML(contactosUsuario, ruta);
    }

    public void eliminarContacto(Contacto contacto){
        String ruta = "xml/contactosUsuario/"+getNumeroTelefono()+".xml";
        File file = new File(ruta);
        ContactosUsuario contactosUsuario = XMLManagerCollection.readXML(ContactosUsuario.class, ruta);
        List<Contacto> list = contactosUsuario.getContactos();
        for (int i = 0; i < list.size(); i++) {
            Contacto c = list.get(i);
            if (c.getNumeroTelefono().equals(contacto.getNumeroTelefono())) {
                list.remove(i);
                break;
            }
        }

        contactosUsuario.setContactos(list);
        XMLManagerCollection.writeXML(contactosUsuario, ruta);

    }

    public void setContactos(List<Contacto> contactos) {
        this.contactos = contactos;
    }

    public String getContrasenna() {
        return contrasenna;
    }

    public void setContrasenna(String contrasenna) {
        this.contrasenna = contrasenna;
    }

}
