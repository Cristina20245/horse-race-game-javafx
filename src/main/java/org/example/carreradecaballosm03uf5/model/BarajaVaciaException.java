package org.example.carreradecaballosm03uf5.model;

public class BarajaVaciaException extends RuntimeException{
    public BarajaVaciaException() {
        super("Se acabaron las cartas de la baraja");
    }

    public BarajaVaciaException(String message) {
        super(message);
    }
}
