package org.example.carreradecaballosm03uf5.Utils;

public class Constants {
    // Datos de conexión a la base de datos
    public static final String DB_URL = "jdbc:mysql://localhost:3306/carreradecaballos?createDatabaseIfNotExist=true";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "";

    public static final int LONGITUD_PISTAS = 10;

    public static final int LINEAS = 4;  // Número de lineas que representan caballos
    public static final int TAMANO_CELULA = 80; // Tamanño de los cuadrados de la linea de partida
    public static final int TAMANO_CUADRADO = 20; // Tamaño de los cuadrados rojos y blancos

    // Evitar instanciación
    private Constants() {
        throw new UnsupportedOperationException("Clase utilitaria, no debe ser instanciada.");
    }
}