package org.example.cincuentazo.model;

public class JugadorHumano extends JugadorAdaptador{
    @Override
    public void jugarCarta(Mesa mesa){
        int indice = pedirCartaUsuario();
        mesa.agregarCarta(mano.get(indice));
        mano.remove(indice);
    }
    private int pedirCartaUsuario(){
        return 0;
    }
}
