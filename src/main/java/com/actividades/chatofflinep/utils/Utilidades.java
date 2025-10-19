package com.actividades.chatofflinep.utils;

import javafx.stage.FileChooser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Utilidades {

    public static boolean esNumeroDeTelefono(String cadena) {
        if (cadena == null) {
            return false;
        }
        String limpio = cadena.replaceAll("[\\s\\-()]", "");
        return limpio.matches("^[67][0-9]{8}$");
    }


//    public static void guardarCSV(String nombreArchivo, String contenido) {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Guardar archivo CSV");
//
//        fileChooser.getExtensionFilters().add(
//                new FileChooser.ExtensionFilter("Archivo CSV", "*.csv")
//        );
//
//        File archivo = fileChooser.showSaveDialog(null);
//
//        if (archivo != null) {
//            if (!archivo.getName().toLowerCase().endsWith(".csv")) {
//                archivo = new File(archivo.getAbsolutePath() + ".csv");
//            }
//
//            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
//                writer.write(contenido);
//                System.out.println("Archivo CSV guardado en: " + archivo.getAbsolutePath());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }



}
