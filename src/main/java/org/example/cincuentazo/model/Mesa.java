package org.example.cincuentazo.model;
import java.util.ArrayList;
import java.util.List;
public class Mesa {
    /**
     *  Lista que almacena las cartas que ya han sido jugadas
     */
    private final List<Card> cartas;
    /**
     * Representa el valor acumulado de la mesa
     */
    private Integer valor=0;
    public Mesa() {
        this.cartas = new ArrayList<>();
    }

    /**
     * Añade una carta a la mesa
     * @param carta carta que ya ha sido jugada
     */
    public void agregarCarta(Card carta){
        cartas.add(carta);
        valor=valor + carta.getValue();
    }

    /**
     * Este medotodo devuelve las cartas a la baraja cuando ya no hay mas cartas en la baraja
     * @param baraja baraja de cartas
     */
    public void agregarCartasBaraja(Deck baraja){
        for(int i=0; i< (cartas.size()-1); i++){
            baraja.agregarCarta( cartas.get(i) );
        }
    }

    /**
     *
     * @return devuelve true si la mesa esta vacia
     */
    public boolean estaVacio(){
        return cartas.isEmpty();
    }

    /**
     *
     * @return devuelve la ultima carta que ha sido jugada en la mesa
     */
    public Card ultimaCarta(){
        return cartas.get(cartas.size()-1);
    }

    /**
     *
     * @return devuelve el valor correspondiente a la mesa
     */
    public Integer getValorMesa(){
        return  valor;
    }
}
