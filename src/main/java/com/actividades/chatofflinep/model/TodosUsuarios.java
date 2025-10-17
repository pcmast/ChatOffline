package com.actividades.chatofflinep.model;

import com.actividades.chatofflinep.dataAccess.XMLManagerCollection;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name =  "Usuarios")
public class TodosUsuarios {

    private List<Usuario> usuarioList = new ArrayList<>();
    private static TodosUsuarios instance;



    public List<Usuario> getUsuarios(){
        return usuarioList;
    }

    public static TodosUsuarios getInstance() {
        if (instance == null) {
            instance = new TodosUsuarios();
        }
        return instance;
    }

    public List<Usuario> getUsuarioList() {
       TodosUsuarios usuariosLeidos = XMLManagerCollection.readXML(TodosUsuarios.class, "xml/usuarios");

        if (usuariosLeidos != null && usuariosLeidos.getUsuarios() != null) {
            this.usuarioList = usuariosLeidos.getUsuarios();
        } else {
            this.usuarioList = new ArrayList<>();
        }

        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }
}