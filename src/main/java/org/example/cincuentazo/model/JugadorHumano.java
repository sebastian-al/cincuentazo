package org.example.cincuentazo.model;

public  class JugadorHumano extends JugadorAdaptador{
    @Override
    public void jugarCarta(Mesa mesa, Card carta){
        mesa.agregarCarta(carta);
        mano.remove(carta);
    }
    public int cantidadCartasMano(){
        return mano.size();
    }
}
