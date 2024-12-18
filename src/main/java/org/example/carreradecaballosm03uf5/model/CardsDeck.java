package org.example.carreradecaballosm03uf5.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CardsDeck {
    private ArrayList<Card> cardsDeck = new ArrayList<>(); // Array de la Baraja de cartas
    private int[] num = {1, 2, 3, 4, 5, 6, 7}; // Números de las cartas
    private CardSuit[] cardSuits = {CardSuit.GOLD, CardSuit.CLUBS, CardSuit.CUPS, CardSuit.SWORDS}; // Palos de las cartas

    private CardFace[] cardFaces = {CardFace.JACK, CardFace.KNIGHT, CardFace.KING}; // sota, caballo y rey
    private Card card; // Carta temporal
    private ArrayList<Integer> numCartes; // Lista de cartas repartidas

    /**
     * Método para crear la baraja de cartas cada vez que quieras jugar.
     * Crea cartas a partir de dos arreglos (número y palo) y las añade a la ArrayList de la baraja.
     */
    public CardsDeck() {
        for (int i = 0; i < num.length; i++) {
            for (int j = 0; j < cardSuits.length; j++) {
                card = new NumeredCard(num[i], cardSuits[j]);
                cardsDeck.add(card);
            }
        }
        for (CardFace face : cardFaces) {
            for (int j = 0; j < cardSuits.length; j++) {
                card = new FacedCard(face, cardSuits[j]);
                cardsDeck.add(card);
            }
        }
        numCartes = new ArrayList<>(); // Inicializa la lista de cartas repartidas
    }


    /**
     * Obtiene el número de cartas que se han repartido.
     * @return El tamaño de numCartes, que indica cuántas cartas han sido repartidas.
     */
    public int getNumCartes() {
        return numCartes.size(); // Retorna el número de cartas repartidas
    }

    /**
     * Método para repartir una nueva carta aleatoria a la mano.
     * @return La carta repartida.
     */
    public Card getCardFromDeck() {

        int numcarta = comprovarNumCartes(); // Comprueba el número de cartas
        Card cartadonada = null;
        if (numcarta != -1) {
            cartadonada = cardsDeck.get(numcarta); // Asigna la carta aleatoria
        }else{
            throw new BarajaVaciaException();
        }
        return cartadonada; // Retorna la carta repartida
    }



    /**
     * Filtra la baraja para crear una nueva baraja con solo cartas numeradas y ciertas cartas con cara.
     * @return Una CardsDeck filtrada que contiene solo cartas válidas.
     */
    public CardsDeck filterNumeredCards() {
        CardsDeck filteredDeck = new CardsDeck(); // Nueva baraja filtrada
        filteredDeck.cardsDeck = new ArrayList<>(); // Inicializa la baraja filtrada

        for (Card card : this.cardsDeck) {
            if (card instanceof NumeredCard ||
                    (card instanceof FacedCard && ((FacedCard) card).getFace() != CardFace.KING
                            && ((FacedCard) card).getFace() != CardFace.KNIGHT)) {
                filteredDeck.cardsDeck.add(card); // Añade cartas numeradas y ciertas cartas con cara
            }
        }

        return filteredDeck; // Retorna la baraja filtrada
    }

    /**
     * Limpia la lista de cartas repartidas, permitiendo que la baraja sea barajada de nuevo.
     */
    public void barajar() {
        numCartes.clear(); // Limpia la lista de cartas repartidas
    }

    /**
     * Método auxiliar que devuelve una carta aleatoria de la baraja que no ha sido repartida antes.
     * @return Carta no repartida antes.
     */
    private int comprovarNumCartes() {
        boolean trobada; // Variable para comprobar si la carta ya ha sido repartida
        int numcarta; // Número de la carta
        int sizeDeck = cardsDeck.size(); // Tamaño de la baraja

        if (numCartes.size() >= sizeDeck) {
            return -1; // Retorna -1 si se han repartido todas las cartas
        }

        do {
            trobada = false; // Inicializa la variable
            numcarta = (int) (Math.random() * sizeDeck); // Genera un número aleatorio

            for (Integer x : numCartes) {
                if (numcarta == x) {
                    trobada = true; // Marca que la carta ya ha sido repartida
                    break;
                }
            }
        } while (trobada); // Continúa buscando hasta encontrar una carta no repartida

        numCartes.add(numcarta); // Añade el número de la carta a la lista de cartas repartidas
        return numcarta; // Retorna el número de la carta
    }

    /**
     * Obtiene el tamaño total de la baraja.
     * @return El número total de cartas en la baraja.
     */
    public int getDeckSize() {
        return cardsDeck.size(); // Retorna el tamaño de la baraja
    }

    /**
     * Elimina una carta especificada de la baraja.
     * @param card La carta que se va a eliminar de la baraja.
     */
    public void removeCard(Card card) {
        cardsDeck.remove(card); // Elimina la carta de la baraja
    }
}

