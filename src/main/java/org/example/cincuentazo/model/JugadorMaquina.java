package org.example.cincuentazo.model;

public class JugadorMaquina extends JugadorAdaptador {
    private boolean puedeJugar;
    @Override
    public void jugarCarta(Mesa mesa){
        int indice = elegirMejorCarta(mesa);
        mesa.agregarCarta(mano.get(indice));
        mano.remove(indice);
    }
    private int elegirMejorCarta(Mesa mesa){
        int valorMesa = mesa.getValorMesa();
        if (valorMesa<40){
            int indiceMayor=0;
            for (int i=0; i<4; i++){
                if(mano.get(i).getValue()>valorMesa){
                    indiceMayor=i;
                }
            }
            return indiceMayor;
        } else{
            for (int i=0; i<4; i++){
                if((mano.get(i).getValue()+valorMesa)<=50){
                    return i;
                }
            }
        }
        return 0;
    }
}
