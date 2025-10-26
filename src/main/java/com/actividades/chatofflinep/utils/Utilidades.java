package com.actividades.chatofflinep.utils;

import javafx.stage.FileChooser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Utilidades {

    //Metodo que comprueba si una cadena corresponde a un numero de telefono
    public static boolean esNumeroDeTelefono(String cadena) {
        if (cadena == null) {
            return false;
        }
        String limpio = cadena.replaceAll("[\\s\\-()]", "");
        return limpio.matches("^[67][0-9]{8}$");
    }
    //Metodo que limpia un numero de telefono de espacios inecesarios
    public static String limpiarNumeroTelefono(String cadena) {
        if (cadena == null) return "";
        cadena = cadena.trim();
        return cadena.replaceAll("[\\p{Zs}\\s\\u00A0\\-()]", "");
    }
    //Metodo que verifica si una cadena es un correo electronico
    public static boolean esCorreoElectronicoValido(String correo) {
        if (correo == null || correo.isEmpty()) {
            return false;
        }
        String patronCorreo = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return correo.matches(patronCorreo);
    }

}
