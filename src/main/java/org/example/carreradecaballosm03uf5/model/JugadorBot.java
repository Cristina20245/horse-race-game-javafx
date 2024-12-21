package org.example.carreradecaballosm03uf5.model;

import java.util.Random;

public class JugadorBot extends Jugador {
    private static final Random random = new Random(); // Generador de números aleatorios

    // Constructor que llama al constructor de la clase base
    public JugadorBot(String nombre, CardSuit palo) {
        super(nombre, palo, generarFichasAleatorias()); // Llama al constructor de Jugador con un número aleatorio de fichas
    }

    // Método estático para generar un número aleatorio de fichas
    private static int generarFichasAleatorias() {
        return random.nextInt(100) + 1; // Genera entre 1 y 100 fichas
    }

    @Override
    public String getDescription() {
        return "Jugador BOT: " + getNombre() + ", Palo: " + getPalo().getDescription() + ", Fichas apostadas: " + getFichas();
    }
}