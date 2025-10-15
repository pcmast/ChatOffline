package com.actividades.chatofflinep.model;

import com.actividades.chatofflinep.dataAccess.XMLManagerUsuariosCollection;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name =  "Usuarios")
public class TodosUsuarios {

    private List<Usuario> usuarioList = new ArrayList<>();

    public List<Usuario> getUsuarios(){
        return usuarioList;
    }

    public List<Usuario> getUsuarioList() {
       TodosUsuarios usuariosLeidos = XMLManagerUsuariosCollection.readXML(TodosUsuarios.class, "xml/usuarios");

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