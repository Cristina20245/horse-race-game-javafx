
import org.example.carreradecaballosm03uf5.model.BarajaVaciaException;
import org.example.carreradecaballosm03uf5.model.CardsDeck;
import org.example.carreradecaballosm03uf5.model.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BarajaTest {

    private CardsDeck baraja; // Instancia de la clase Baraja

    @BeforeEach
    void setUp() {
        baraja = new CardsDeck(); // Inicializa la baraja antes de cada test
        baraja.barajar(); // Asegura que las cartas estén mezcladas antes de cada prueba
    }

    @Test
    void testGetCardFromDeck_DevuelveCartaCuandoHayCartasDisponibles() {

        Card carta = null;
        try {
            carta = baraja.getCardFromDeck();
        } catch (BarajaVaciaException e) {
            fail("No debería lanzar BarajaVaciaException cuando hay cartas disponibles");
        }


        assertNotNull(carta, "El método debería devolver una carta no nula");
    }

    @Test
    void testGetCardFromDeck_DisminuyeElTamañoDelMazo() {

        int tamañoInicial = baraja.getNumCartes();


        baraja.getCardFromDeck();


        assertEquals(tamañoInicial + 1, baraja.getNumCartes(),
                "El tamaño del mazo debería disminuir en 1 después de sacar una carta");
    }

    @Test
    void testGetCardFromDeck_LanzaBarajaVaciaExceptionCuandoNoHayCartas() {

        while (baraja.getNumCartes() < baraja.getDeckSize()) {
            try {
                baraja.getCardFromDeck();
            } catch (BarajaVaciaException e) {
                fail("No debería lanzar BarajaVaciaException mientras hay cartas disponibles");
            }
        }


        assertThrows(BarajaVaciaException.class, baraja::getCardFromDeck,
                "Debería lanzar BarajaVaciaException cuando el mazo está vacío");
    }

    @Test
    void testGetCardFromDeck_TodasLasCartasSonUnicas() throws BarajaVaciaException {
           Set<Card> cartasSacadas = new HashSet<>();

        while (baraja.getNumCartes() < baraja.getDeckSize()) {
            Card carta = baraja.getCardFromDeck();
            assertTrue(cartasSacadas.add(carta),
                    "La carta " + carta + " ya fue sacada anteriormente, las cartas deberían ser únicas");
        }
    }
}
