package org.example.carreradecaballosm03uf5.model;

public abstract class Card {
    protected CardSuit suit; // Palo de la carta
    protected float value; // Valor de la carta

    protected String toString(String numberOrFace) {
        return String.format("%7s de %6s, valor %.1f", numberOrFace, suit, value); // Formato de la representación de la carta
    }

   /* public int getValue() {
        return value;
    }*/

    public int getValue() {
        return (int) value;  // Convierte el valor a entero, descartando la parte decimal
    }


    public CardSuit getSuit() {
        return suit; // Retorna el palo de la carta
    }

    // Método abstracto para obtener una descripción de la carta
    public abstract String getDescription();

    public abstract String getImagePath();
}
