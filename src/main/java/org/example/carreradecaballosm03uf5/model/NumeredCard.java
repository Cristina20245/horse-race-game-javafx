package org.example.carreradecaballosm03uf5.model;

public class NumeredCard extends Card {

    private int num;
    /**
     * Constructor para crear las cartas con su palo y valor llamando a un método auxiliar.
     *
     * @param num --> Número de la carta
     * @param cardSuit --> Palo de la carta
     */
    public NumeredCard(int num, CardSuit cardSuit) {
        this.num = num;
        super.suit = cardSuit;
        super.value = num;
    }

    public int getNum() {
        return num;
    }

    /**
     * Método ToString para mostrar los datos de la carta.
     * @return --> Número, Palo y valor
     */
    @Override
    public String toString() {
        return super.toString(String.valueOf(this.num));
    }

    @Override
    public String getDescription() {
        return  (int)value + " de " + suit.getDescription();  // Devuelve la descripción de la carta
    }

    @Override
    public String getImagePath() {
        return String.format("/images/%d_%s.png", num, suit.name().toUpperCase());
    }
}
