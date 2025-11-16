package org.example.cincuentazo.model;
import java.util.ArrayList;
import java.util.List;
public interface Player {
    List<Card> getMano();
    // Toma las 4 cartas iniciales del mazo y las agrega a su mano
    default void manoInicial(Deck mazo) {
        getMano().clear();
        try {
            for (int i =0; i<4; i++){
                robarCarta(mazo);
            }
        } catch (Exception e) {
            System.out.println("Error al repartir carta: " + e.getMessage());
        }
    }
    private void jugarCarta(Mesa mesa){}
    void robarCarta(Deck mazo);
}
