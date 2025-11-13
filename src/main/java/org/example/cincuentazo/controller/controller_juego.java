package org.example.cincuentazo.controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.InputStream;

/**
 * Controlador de la vista del juego (mesa de juego).
 */
public class controller_juego {

    @FXML
    private HBox contenedorMaquina; // en el FXML usas <HBox fx:id="contenedorMaquina" ... />

    @FXML
    private HBox cartasJugador; // fx:id="cartasJugador" en el FXML

    @FXML
    private ImageView mazoCartas; // fx:id="mazoCartas" en el FXML

    private int numContrincantes = 1;

    public void setNumContrincantes(int numContrincantes) {
        this.numContrincantes = Math.max(1, Math.min(3, numContrincantes));
        generarMaquinas();
    }

    @FXML
    public void initialize() {
        // Si quieres un valor por defecto hasta que setNumContrincantes sea llamado:
        // generarMaquinas(); // opcional si ya llamas setNumContrincantes desde el otro controlador
    }

    private void generarMaquinas() {
        contenedorMaquina.getChildren().clear();

        // Cargamos la imagen de carta boca abajo una sola vez
        InputStream is = getClass().getResourceAsStream("/org/example/cincuentazo/cartamaquina.png");
        Image cartaBocaAbajo = null;
        if (is != null) {
            cartaBocaAbajo = new Image(is);
        }

        for (int i = 0; i < numContrincantes; i++) {
            // Cada "zona" de máquina será un VBox (que puede contener nombre/score encima y las 4 cartas en un HBox)
            VBox zonaMaquina = new VBox(5);
            zonaMaquina.setStyle("-fx-alignment: center;");

            // HBox con las 4 cartas de la máquina (boca abajo)
            HBox filaCartas = new HBox(8);
            filaCartas.setStyle("-fx-alignment: center;");

            for (int j = 0; j < 4; j++) {
                ImageView carta = new ImageView();
                if (cartaBocaAbajo != null) {
                    carta.setImage(cartaBocaAbajo);
                }
                carta.setFitWidth(80);
                carta.setFitHeight(110);
                filaCartas.getChildren().add(carta);
            }

            zonaMaquina.getChildren().addAll(filaCartas);
            contenedorMaquina.getChildren().add(zonaMaquina);
        }
    }

    @FXML
    private void tomarCarta() {
        // Manejador simple de prueba — por ahora solo imprime en consola.
        System.out.println("Tomar carta pulsado (lógica por implementar)");
        // Aquí más tarde: robar del mazo, actualizar carta central, actualizar botones del jugador, etc.
    }

}

