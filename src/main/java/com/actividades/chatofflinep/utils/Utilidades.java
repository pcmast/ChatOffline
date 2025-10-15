package com.actividades.chatofflinep.utils;

public class Utilidades {

    public static boolean esNumeroDeTelefono(String cadena) {
        if (cadena == null) {
            return false;
        }
        String limpio = cadena.replaceAll("[\\s\\-()]", "");
        return limpio.matches("^[67][0-9]{8}$");
    }



}
