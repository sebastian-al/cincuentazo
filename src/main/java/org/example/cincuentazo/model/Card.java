package org.example.cincuentazo.model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;

public class Card {
    /**
     *  Esta es la clase card (carta) la cual determina a la carte y sus atributos
     *  dentro de la aplicación
     */
    private String name;
    private int value;
    private Rectangle rectangle;
    private Rectangle background;

    /**
     * Constructor de la carta.
     * @param name Nombre de la carta (ej: "ace_of_spades")
     * @param imagePath Ruta de la imagen (ej: "/images/ace_of_spades.png")
     */
    public Card(String name, String imagePath) {
        this.name = name;
        this.value = calcularValor(name);
        crearCartaConFondo(imagePath);
    }

    /**
     * Calcula el valor de la carta según las reglas del Cincuentazo.
     */
    private int calcularValor(String nombreCarta) {
        // Extrae solo el valor (antes del primer _ o .)
        String valorStr = nombreCarta.split("[_\\.]")[0].toUpperCase();

        switch(valorStr) {
            case "2": return 2;
            case "3": return 3;
            case "4": return 4;
            case "5": return 5;
            case "6": return 6;
            case "7": return 7;
            case "8": return 8;
            case "9": return 0;  // 9 ni suma ni resta
            case "10": return 10;
            case "J":
            case "JACK":
                return -10;
            case "Q":
            case "QUEEN":
                return -10;
            case "K":
            case "KING":
                return -10;
            case "A":
            case "ACE":
                return 1;  // Por defecto 1, pero puede ser 10
            default:
                System.err.println("⚠️ Valor no reconocido: " + valorStr);
                return 0;
        }
    }

    /**
     * Crea la representación visual de la carta con fondo y sombra.
     */
    private void crearCartaConFondo(String imagePath) {
        this.rectangle = new Rectangle(80, 120);

        try {
            // Intenta cargar la imagen desde resources
            Image img = new Image(getClass().getResourceAsStream(imagePath));
            this.rectangle.setFill(new ImagePattern(img));
            System.out.println("✅ Imagen cargada: " + imagePath);

        } catch (Exception e) {
            System.err.println("❌ Error cargando imagen: " + imagePath);
            System.err.println("   Verifica que el archivo exista en: src/main/resources" + imagePath);
            e.printStackTrace();

            // Fallback: carta roja con texto
            this.rectangle.setFill(Color.DARKRED);
            this.rectangle.setStroke(Color.WHITE);
            this.rectangle.setStrokeWidth(2);
        }

        // Crea el fondo blanco con bordes redondeados
        this.background = new Rectangle(80, 120);
        this.background.setFill(Color.WHITE);
        this.background.setStroke(Color.DARKGRAY);
        this.background.setStrokeWidth(2);
        this.background.setArcWidth(10);
        this.background.setArcHeight(10);

        // Añade sombra
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.color(0, 0, 0, 0.4));
        shadow.setRadius(5);
        shadow.setOffsetX(2);
        shadow.setOffsetY(2);
        this.background.setEffect(shadow);

        // Bordes redondeados para la carta
        this.rectangle.setArcWidth(10);
        this.rectangle.setArcHeight(10);
    }

    // Getters
    public Rectangle getBackground() {
        return background;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    /**
     * Verifica si la carta es un As (puede valer 1 o 10).
     */
    public boolean isAce() {
        String valorStr = name.split("[_\\.]")[0].toUpperCase();
        return valorStr.equals("A") || valorStr.equals("ACE");
    }

    /**
     * Obtiene el valor alternativo (solo para Ases).
     * @return 10 si es As, el valor normal en caso contrario
     */
    public int getAlternativeValue() {
        return isAce() ? 10 : value;
    }

    @Override
    public String toString() {
        return name + " (Valor: " + value + ")";
    }
}