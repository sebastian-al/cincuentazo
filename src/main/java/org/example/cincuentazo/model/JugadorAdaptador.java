package org.example.cincuentazo.model;
import java.util.ArrayList;
import java.util.List;

public  class JugadorAdaptador implements Player {
    protected List<Card> mano;
    public JugadorAdaptador() {
        this.mano = new ArrayList<>();
    }
    @Override
    public void robarCarta(Deck mazo) {
        Card carta = mazo.tomarCarta();
        if (carta == null) {
            throw new RuntimeException("No se puede agregar una carta, el mazo está vacío.");
        }
        mano.add(carta);
    }
    @Override
    public List<Card> getMano() {
        return mano;
    }
    public void jugarCarta(Mesa mesa, Card carta) {
    }
}
