package org.example.carreradecaballosm03uf5.model;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.example.carreradecaballosm03uf5.Utils.MovimientosCaballo;

import java.util.Optional;


public class Juego {
    private CardsDeck barajaSinReyes;
    private int ronda = 1;
    private Card cartaSacada;
    private MovimientosCaballo movimientosCaballo;
    private Jugador ganador= null;
    private  Integer boteFinal;

    public Juego(MovimientosCaballo movimientosCaballo) {
        this.movimientosCaballo = movimientosCaballo;
        CardsDeck baraja = new CardsDeck();
        this.barajaSinReyes = baraja.filterNumeredCards();
    }
    public void definirGanador(Jugador[] jugadores, CardSuit suit) {
        Jugador jugadorGanador=  encontrarGanador(suit, jugadores);
        Integer boteTotal=calcularBoteTotal(jugadores);
        setGanadorYBote(jugadorGanador,boteTotal);

    }

    public Jugador getGanador() {
        return ganador;
    }

    public Integer getBoteFinal() {
        return boteFinal;
    }

    public void setGanadorYBote(Jugador ganador, Integer boteFinal) {
        this.ganador = ganador;
        this.boteFinal=boteFinal;

    }
    public Jugador encontrarGanador(CardSuit suit, Jugador[] jugadores) {
        for (Jugador jugador : jugadores) {
            if (jugador.getPalo() == suit) {
                return jugador; // Retorna el jugador ganador
            }
        }
        return null;
    }
    public int calcularBoteTotal(Jugador[] jugadores) {
        int totalBote = 0;
        for (Jugador jugador : jugadores) {
            totalBote += jugador.getFichas();
        }
        return totalBote;
    }

    public int getRonda() {
        return ronda;
    }

    public Card jugarRonda() {
        try {
            // Saca una carta de la baraja
            cartaSacada = barajaSinReyes.getCardFromDeck();

            // Posiciona el caballo según la ronda
            if (ronda % 5 == 0) {
                movimientosCaballo.retrocederCaballo(cartaSacada);
            } else {
                movimientosCaballo.avanzarCaballo(cartaSacada);
            }

            // Incrementa la ronda
            ronda++;

            return cartaSacada;

        } catch (BarajaVaciaException e) {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("¡No hay más cartas!");
            alerta.setHeaderText(e.getMessage());
            alerta.setContentText("¿Deseas barajar las cartas otra vez o salir del juego?");

            // Añade los botones
            ButtonType buttonTypeShuffle = new ButtonType("Barajar");
            ButtonType buttonTypeExit = new ButtonType("Salir");
            alerta.getButtonTypes().setAll(buttonTypeShuffle, buttonTypeExit);

            // Mostrando el alerta y esperando respuesta del usuario
            Optional<ButtonType> resultado = alerta.showAndWait();
            if (resultado.isPresent() && resultado.get() == buttonTypeShuffle) {
                // El usuario eligió barajar
                barajaSinReyes.barajar();
                try {
                    cartaSacada = barajaSinReyes.getCardFromDeck();
                } catch (BarajaVaciaException ex) {
                    // Este caso no debería ocurrir, pero retorna null por seguridad
                    return null;
                }


                // Posiciona el caballo según la ronda
                if (ronda % 5 == 0) {
                    movimientosCaballo.retrocederCaballo(cartaSacada);
                } else {
                    movimientosCaballo.avanzarCaballo(cartaSacada);
                }

                // Incrementa la ronda
                ronda++;

                return cartaSacada;

            } else if (resultado.isPresent() && resultado.get() == buttonTypeExit) {
                // El usuario eligió salir
                Platform.exit(); // Finaliza la app
                return null;
            }
        }

        // Retorna null como último recurso
        return null;
    }

}
