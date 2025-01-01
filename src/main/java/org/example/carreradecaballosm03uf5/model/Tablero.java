package org.example.carreradecaballosm03uf5.model;

import java.util.HashMap;
import java.util.Map;

public class Tablero {

    private Map<String, Integer> posicicionDeLosCaballos; // Almacena las posiciones de los caballos

    // Constructor
    public Tablero(Map<String, Integer> posicicionDeLosCaballosNewPartida) {
        this.posicicionDeLosCaballos = new HashMap<>();

        if(posicicionDeLosCaballosNewPartida.isEmpty()) {
            // Obtiene los valores de la enumeración CardSuit
            CardSuit[] suits = CardSuit.values();

            // Inicializa las posiciones con los símbolos de los caballos para cada CardSuit
            for (CardSuit suit : suits) {
                posicicionDeLosCaballos.put(suit.getDescription(), 0);
            }
        } else {
            posicicionDeLosCaballos = posicicionDeLosCaballosNewPartida;
        }
    }

    // Método para actualizar la posición de un caballo
    public void actualizarPosicion(CardSuit caballo, int avanza) {
        int nuevaPosicion = posicicionDeLosCaballos.get(caballo.getDescription()) + avanza;
        posicicionDeLosCaballos.put(caballo.getDescription(), nuevaPosicion);
    }

    // Método para obtener la posición de un caballo
    public int obtenerPosicion(CardSuit caballo, int idPartida) {
        return posicicionDeLosCaballos.getOrDefault(caballo.getDescription(), -1); // Retorna -1 si el caballo no se encuentra
    }
}
