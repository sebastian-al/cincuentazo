package org.example.cincuentazo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;
    private static final String[] PALOS = {"spades", "hearts", "diamonds", "clubs"};
    private static final String[] VALORES = {"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};

    public Deck() {
        cards = new ArrayList<>();
        crearMazo();
        barajar();
    }

    /**
     * Crea un mazo completo de 52 cartas.
     */
    private void crearMazo() {
        for (String palo : PALOS) {
            for (String valor : VALORES) {
                String nombre = valor + "_of_" + palo;
                String imagePath = "/images/" + nombre + ".png";
                cards.add(new Card(nombre, imagePath));
            }
        }
        System.out.println("✅ Mazo creado con " + cards.size() + " cartas");
    }

    /**
     * Baraja las cartas aleatoriamente.
     */
    public void barajar() {
        Collections.shuffle(cards);
        System.out.println("🔀 Mazo barajado");
    }

    /**
     * Toma una carta del mazo.
     * @return La carta tomada, o null si el mazo está vacío
     */
    public Card tomarCarta() {
        if (cards.isEmpty()) {
            System.err.println("⚠️ El mazo está vacío");
            return null;
        }
        return cards.remove(0);
    }

    /**
     * Añade una carta al final del mazo.
     */
    public void agregarCarta(Card carta) {
        cards.add(carta);
    }

    /**
     * Obtiene el número de cartas restantes.
     */
    public int cartasRestantes() {
        return cards.size();
    }

    /**
     * Verifica si el mazo está vacío.
     */
    public boolean estaVacio() {
        return cards.isEmpty();
    }

    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }
}