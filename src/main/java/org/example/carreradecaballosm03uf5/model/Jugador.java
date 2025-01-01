package org.example.carreradecaballosm03uf5.model;

public abstract class Jugador {

    private int idJugador;
    private String nombre;
    private CardSuit palo;  //Oros, Copas, Espadas, Bastos
    private int fichas;

    private int linea;

    private int columna;

    // Constructor: inicializa el nombre, el palo y el número de fichas del jugador
    public Jugador(String nombre, CardSuit palo, int fichas) {
        this.nombre = nombre;
        this.palo = palo;
        this.fichas = fichas;
    }

    public Jugador(){}

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPalo(CardSuit palo) {
        this.palo = palo;
    }

    public void setFichas(int fichas) {
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

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
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