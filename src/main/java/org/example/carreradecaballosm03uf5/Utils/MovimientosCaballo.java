package org.example.carreradecaballosm03uf5.Utils;

import org.example.carreradecaballosm03uf5.model.Card;
import org.example.carreradecaballosm03uf5.model.Tablero;

public class MovimientosCaballo {
    private final Tablero tablero;

    public MovimientosCaballo(Tablero tablero) {
        this.tablero = tablero;
    }

    /**
     * Mueve un caballo hacia adelante según la carta sacada.
     * @param carta La carta sacada.
     */
    public void avanzarCaballo(Card carta, int idPartida) {
        // Comprueba si el caballo existe
        if (tablero.obtenerPosicion(carta.getSuit(), idPartida) != -1) {
            tablero.actualizarPosicion(carta.getSuit(), 1); // Mueve hacia adelante 1 posición
        }
    }

    /**
     * Mueve un caballo hacia atrás según la carta sacada.
     * @param carta La carta sacada.
     */
    public void retrocederCaballo(Card carta, int idPartida) {
        if (tablero.obtenerPosicion(carta.getSuit(), idPartida) != -1) {
            int posicionCaballo = tablero.obtenerPosicion(carta.getSuit(), idPartida);
            if (posicionCaballo > 0) {
                tablero.actualizarPosicion(carta.getSuit(), -1); // Mueve hacia atrás 1 posición
            }
        }
    }
}
