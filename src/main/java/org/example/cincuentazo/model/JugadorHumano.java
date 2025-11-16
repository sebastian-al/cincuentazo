package org.example.cincuentazo.model;

public  class JugadorHumano extends JugadorAdaptador{
    /**
     * Juega una carta en la mesa de juego
     * @param mesa
     * @param carta
     */
    @Override
    public void jugarCarta(Mesa mesa, Card carta){
        mesa.agregarCarta(carta);
        mano.remove(carta);
    }

    /**
     * devuelve cuantas cartas tiene en la mano
     * @return cartas en la mano
     */
    public int cantidadCartasMano(){
        return mano.size();
    }
}
