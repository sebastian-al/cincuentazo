package org.example.cincuentazo.model;
import java.util.ArrayList;
import java.util.List;
public class Mesa {
    private List<Card> cartas;
    private Integer valor;
    public void agregarCarta(Card carta){
        cartas.add(carta);
        valor=valor + carta.getValue();
    }
    public void agregarCartasBaraja(Deck baraja){
        for(int i=0; i< (cartas.size()-1); i++){
            baraja.agregarCarta( cartas.get(i) );
        }
    }
    public Integer getValorMesa(){
        return  valor;
    }
}
