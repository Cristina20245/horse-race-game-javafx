package org.example.carreradecaballosm03uf5.Utils;

import org.example.carreradecaballosm03uf5.model.Card;
import org.example.carreradecaballosm03uf5.model.Tablero;

public class MovimientosCaballo {
    private Tablero tablero;

    public MovimientosCaballo(Tablero tablero) {
        this.tablero = tablero;
    }

    /**
     * Mueve un caballo hacia adelante según la carta sacada.
     * @param carta La carta sacada.
     */
    public void avanzarCaballo(Card carta) {
        // Comprueba si el caballo existe
        if (tablero.obtenerPosicion(carta.getSuit()) != -1) {
            tablero.actualizarPosicion(carta.getSuit(), 1); // Mueve hacia adelante 1 posición
        } else {
//            System.out.println("Caballo no encontrado para la carta: " + carta);
        }
    }

    /**
     * Mueve un caballo hacia atrás según la carta sacada.
     * @param carta La carta sacada.
     */
    public void retrocederCaballo(Card carta) {
        if (tablero.obtenerPosicion(carta.getSuit()) != -1) {
            int posicionCaballo = tablero.obtenerPosicion(carta.getSuit());
            if (posicionCaballo > 0) {
//                System.out.println("Ronda múltiple de 5: RETROCEDIENDO CABALLO");
                tablero.actualizarPosicion(carta.getSuit(), -1); // Mueve hacia atrás 1 posición
            }
        } else {
//            System.out.println("Caballo no encontrado para la carta: " + carta);
        }
    }
}
