package org.example.carreradecaballosm03uf5.model;

public class JugadorHumano extends Jugador {

    public JugadorHumano(String nombre, CardSuit palo, int fichas) {
        super(nombre, palo, fichas);
    }

    // GetDescription
    @Override
    public String getDescription() {
        return "Jugador Humano: " + getNombre() + ", Palo: " + getPalo().getDescription() + ", Fichas apostadas: " + getFichas();
    }
    public static boolean esNombreValido(String nombre) {
        return nombre.matches("[a-zA-Z]+");
    }

    public static boolean esApuestaValida(int apuesta) {
        return apuesta >= 1 && apuesta <= 100;
    }
}