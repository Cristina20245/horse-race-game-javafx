package org.example.carreradecaballosm03uf5.Utils;

import org.example.carreradecaballosm03uf5.model.CardSuit;
import org.example.carreradecaballosm03uf5.model.Jugador;
import org.example.carreradecaballosm03uf5.model.JugadorBot;
import org.example.carreradecaballosm03uf5.model.JugadorHumano;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CreadorDeJugadores {

    /**
     * Crea jugadores (un jugador humano y tres bots).
     */
    public static Jugador[] crearJugadores(String playerName, CardSuit palo, Integer bet) {
        // Crea el jugador humano
        Jugador jugadorHumano = crearJugadorHumano(playerName,palo ,bet);

        // Lista de los palos del enum CardSuit
        List<CardSuit> palosNaipes = new ArrayList<>();
        Collections.addAll(palosNaipes, CardSuit.values()); // Añade todos los palos de CardSuit

        // Remueve el palo elegido por el jugador humano para evitar duplicados en los bots
        palosNaipes.remove(jugadorHumano.getPalo());

        // Mezcla los palos restantes para asignarlos aleatoriamente a los bots
        Collections.shuffle(palosNaipes);

        // Crea un array para almacenar el jugador humano y 3 bots
        Jugador[] jugadores = new Jugador[4];
        jugadores[0] = jugadorHumano; // El jugador humano se queda en la primera posición

        // Crea bots usando el nuevo metodo, y con los nombres de bots
        String[] nombresBots = {"Bot1", "Bot2", "Bot3"};
        for (int i = 1; i <= 3; i++) {
            jugadores[i] = crearJugadorBot(nombresBots[i - 1], palosNaipes.get(i - 1)); // Asigna palo y ficha aleatoria al bot
        }
        return jugadores;
    }

    /**
     * Método para crear el jugador humano.
     */
    public static Jugador crearJugadorHumano(String playerName, CardSuit palo,Integer bet) {
        return new JugadorHumano(playerName, palo, bet); // Retorna el nuevo jugador creado con la información recolectada
    }

    /**
     * Método para crear un jugador bot.
     */
    public static Jugador crearJugadorBot(String nombre, CardSuit palo) {
        return new JugadorBot(nombre, palo); // Crea un bot con el nombre y palo
    }
}

