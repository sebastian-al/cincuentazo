package org.example.cincuentazo.model;
import java.util.ArrayList;
import java.util.List;
public interface Player {
    List<Card> getMano();
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
