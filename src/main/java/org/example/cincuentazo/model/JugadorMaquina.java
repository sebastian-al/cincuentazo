package org.example.cincuentazo.model;

public class JugadorMaquina extends JugadorAdaptador {
    private String nombre;
    @Override
    public void jugarCarta(Mesa mesa, Card carta) {
            mesa.agregarCarta(carta);
            mano.remove(carta);
    }
    public Card elegirMejorCarta(Mesa mesa){
        int valorMesa = mesa.getValorMesa();
        if (valorMesa<40){
            int indiceMayor =0;
            for (int i=0; i<4; i++){
                if(mano.get(i).getValue()>valorMesa){
                    indiceMayor=i;
                }
            }
            return mano.get(indiceMayor);
        } else{
            for (Card card : mano){
                if((card.getValue()+valorMesa)<=50){
                    return card;
                }
            }
        }
        return null;
    }
    public boolean getPuedeJugar(Mesa mesa){
        if(elegirMejorCarta(mesa) == null){
            return false;
        } else {return true;}
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public String getNombre(){
        return nombre;
    }
    public void devolverCartas(Deck mazo){
        for (Card card : mano){
            mazo.agregarCarta( card);
            mano.remove(card);
        }
    }
}
