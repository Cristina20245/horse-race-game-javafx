package org.example.carreradecaballosm03uf5.model;

public abstract class Jugador {

    private String nombre;
    private CardSuit palo;  //Oros, Copas, Espadas, Bastos
    private int fichas;

    // Constructor: inicializa el nombre, el palo y el número de fichas del jugador
    public Jugador(String nombre, CardSuit palo, int fichas) {
        this.nombre = nombre;
        this.palo = palo;
        this.fichas = fichas;
    }

    public int getFichas() {
        return fichas;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public CardSuit getPalo() {
        return palo;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "nombre='" + nombre + '\'' +
                ", palo=" + palo.getDescription() +
                ", fichas=" + fichas +
                '}';
    }

    // Método abstracto que debe ser implementado por las subclases
    public abstract String getDescription();
}