package org.example.cincuentazo.model;

public class JugadorMaquina extends JugadorAdaptador {
    private String nombre;

    /**
     * Juega una carta en la mesa
     * @param mesa mesa donde se estan jugando las cartas
     * @param carta carta a jugar
     */
    @Override
    public void jugarCarta(Mesa mesa, Card carta) {
            mesa.agregarCarta(carta);
            mano.remove(carta);
    }

    /**
     * elige la mejor carta para jugar
     * @param mesa mesa donde se estan jugando las cartas
     * @return mejor carta para jugar respecto a las otras cartas de la mano
     */
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

    /**
     * Este metodo busca verificar si el jugador puede seguir en el juego
     * @param mesa mesa donde se juegan las cartas
     * @return devuelve true si el jugador puede seguir en el juego y false si no
     */
    public boolean getPuedeJugar(Mesa mesa){
        if(elegirMejorCarta(mesa) == null){
            return false;
        } else {return true;}
    }

    /**
     * le da un nombre al jugador maquina
     * @param nombre nombre a agregar
     */
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    /**
     *
     * @return devuelve el nombre asignado a la maquina
     */
    public String getNombre(){
        return nombre;
    }

    /**
     * devuelve las cartas de su mano al mazo de juego cuando el jugador ya no puede seguir en el juego
     * @param mazo mazo de juego
     */
    public void devolverCartas(Deck mazo){
        for (Card card : mano){
            mazo.agregarCarta( card);
            mano.remove(card);
        }
    }
}
