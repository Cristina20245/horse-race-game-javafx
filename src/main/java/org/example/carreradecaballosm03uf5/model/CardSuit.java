package org.example.carreradecaballosm03uf5.model;

public enum CardSuit {
    GOLD("Oros", "♞"),
    SWORDS("Espadas", "♞"),
    CUPS("Copas", "♞"),
    CLUBS("Bastos", "♞");

    private final String descripcion; // Descripción del palo
    private final String simboloCaballo; // Símbolo que representa el palo


    /**
     * Constructor para inicializar el palo de la carta con una descripción y un símbolo.
     * @param descripcion El nombre del palo.
     * @param simboloCaballo El símbolo que representa el palo.
     */
    CardSuit(String descripcion, String simboloCaballo) {
        this.descripcion = descripcion;
        this.simboloCaballo = simboloCaballo;
    }

    /**
     * Convierte el palo a una representación en cadena.
     * @return La descripción del palo.
     */
    public String getDescription() {
        return descripcion; // Devuelve la descripción del palo
    }

    public String getHorseSymbol() {
        return simboloCaballo; // Devuelve la descripción del palo
    }

}
