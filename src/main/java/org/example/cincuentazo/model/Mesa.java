package org.example.cincuentazo.model;
import java.util.ArrayList;
import java.util.List;
public class Mesa {
    private final List<Card> cartas;
    private Integer valor=0;
    public Mesa() {
        this.cartas = new ArrayList<>();
    }
    public void agregarCarta(Card carta){
        cartas.add(carta);
        valor=valor + carta.getValue();
    }
    public void agregarCartasBaraja(Deck baraja){
        for(int i=0; i< (cartas.size()-1); i++){
            baraja.agregarCarta( cartas.get(i) );
        }
    }
    public boolean estaVacio(){
        return cartas.isEmpty();
    }
    public Card ultimaCarta(){
        return cartas.get(cartas.size()-1);
    }
    public int cartasEnMesa(){
        return cartas.size();
    }
    public Integer getValorMesa(){
        return  valor;
    }
}
