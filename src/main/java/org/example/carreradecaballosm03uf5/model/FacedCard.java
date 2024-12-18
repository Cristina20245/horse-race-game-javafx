package org.example.carreradecaballosm03uf5.model;

public class FacedCard extends Card {
    private CardFace face;

    /**
     * Constructor para crear una FacedCard con una cara y palo especificados.
     * @param cardFace El valor de la cara de la carta.
     * @param pal El palo de la carta.
     */
    public FacedCard(CardFace cardFace, CardSuit pal) {
        super.value = 0.5f;
        super.suit = pal;
        this.face = cardFace;
    }

    /**
     * Obtiene el valor de la cara de la carta.
     * @return La cara de la carta.
     */
    public CardFace getFace() {
        return face;
    }

    /**
     * Convierte el objeto FacedCard a una representación en cadena.
     * @return Una cadena que muestra la cara, el palo y el valor de la carta.
     */
    @Override
    public String toString() {
        return super.toString(this.face.toString());
    }

    /**
     * Obtiene la descripción de la carta.
     * @return Una cadena que muestra el valor y el palo de la carta.
     */
    @Override
    public String getDescription() {
       return  face.name()+" de "+suit.getDescription();
    }


    @Override
    public String getImagePath() {
        return String.format("/images/%s_%s.png", face.name().toUpperCase(), suit.name().toUpperCase());
    }
}
