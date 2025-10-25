package com.actividades.chatofflinep.model;

import com.actividades.chatofflinep.dataAccess.XMLManagerCollection;
import jakarta.xml.bind.annotation.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "chat")
@XmlAccessorType(XmlAccessType.FIELD)
public class Chat {

    @XmlElement(name = "usuario1")
    private Usuario Usuario1;
    @XmlElement(name = "usuario2")
    private Usuario Usuario2;

    @XmlElementWrapper(name = "mensajes")
    @XmlElement(name = "mensaje")
    private List<Mensaje> mensajes = new ArrayList<>();



    public Chat() {


    }
    public Chat(Usuario usuario1, Usuario usuario2, List<Mensaje> mensajes) {
        Usuario1 = usuario1;
        Usuario2 = usuario2;
        this.mensajes = mensajes;
    }

    public void agregarMensaje(Usuario emisor, String texto, LocalTime horaEnvio) {
        Mensaje mensaje = new Mensaje(emisor.getNumeroTelefono(), texto, horaEnvio);
        mensajes.add(mensaje);
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    public Usuario getUsuario1() {
        return Usuario1;
    }

    public void setUsuario1(Usuario usuario1) {
        Usuario1 = usuario1;
    }

    public Usuario getUsuario2() {
        return Usuario2;
    }

    public void setUsuario2(Usuario usuario2) {
        Usuario2 = usuario2;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Mensaje mensaje : mensajes) {
            String nombreEmisor = mensaje.getEmisor().equals(Usuario1.getNumeroTelefono()) ? Usuario1.getNombre() : Usuario2.getNombre();
            if (mensaje.getRuta() != null && !mensaje.getRuta().isEmpty()) {
                sb.append(nombreEmisor).append(": ").append(mensaje.getRuta()).append("\n");
            } else {
                sb.append(nombreEmisor).append(": ").append(mensaje.getTexto()).append("\n");
            }
        }
        return sb.toString();
    }

}
