package org.example.cincuentazo.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.example.cincuentazo.model.Card;
import org.example.cincuentazo.model.Deck;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador principal del juego Cincuentazo.
 * Gestiona la lógica de la partida, turnos y visualización de cartas.
 */
public class controller_juego {

    @FXML
    private HBox contenedorMaquina;

    @FXML
    private HBox cartasJugador;

    @FXML
    private ImageView mazoCartas;

    @FXML
    private StackPane mesaCentral; // Contenedor para las cartas jugadas en la mesa

    @FXML
    private Label labelSumaMesa; // Label para mostrar la suma actual

    @FXML
    private Button btnTomarCarta;

    @FXML
    private Label labelCartasRestantes; // Muestra cartas restantes en el mazo

    @FXML
    private Label labelTurnoActual; // Muestra de quién es el turno

    @FXML
    private Label labelEstadoJuego; // Muestra el estado actual del juego

    private Deck mazo;
    private List<Card> manoJugador;
    private List<Card> cartasEnMesa; // Cartas jugadas en la mesa
    private int sumaMesa;
    private int numContrincantes = 1;
    private boolean cartaJugadaEnTurno = false; // Control para verificar si jugó carta antes de robar

    /**
     * Establece el número de contrincantes máquina (1-3).
     */
    public void setNumContrincantes(int numContrincantes) {
        this.numContrincantes = Math.max(1, Math.min(3, numContrincantes));
        generarMaquinas();
    }

    /**
     * Inicializa el controlador y prepara el juego.
     */
    @FXML
    public void initialize() {
        mazo = new Deck();
        manoJugador = new ArrayList<>();
        cartasEnMesa = new ArrayList<>();
        sumaMesa = 0;

        // Iniciar el juego
        iniciarJuego();
    }

    /**
     * Inicia una nueva partida según las reglas del Cincuentazo.
     */
    private void iniciarJuego() {
        System.out.println("🎮 Iniciando juego...");

        // Repartir 4 cartas a cada jugador
        repartirManosIniciales();

        // Colocar carta inicial en la mesa
        colocarCartaInicialEnMesa();

        // Configurar imagen del mazo
        configurarImagenMazo();

        // Deshabilitar botón de robar hasta que juegue una carta
        btnTomarCarta.setDisable(true);
    }

    /**
     * Reparte 4 cartas a cada jugador (humano y máquinas).
     */
    private void repartirManosIniciales() {
        // Repartir al jugador humano
        for (int i = 0; i < 4; i++) {
            Card carta = mazo.tomarCarta();
            if (carta != null) {
                manoJugador.add(carta);
            }
        }

        // Mostrar cartas del jugador
        actualizarCartasJugador();

        System.out.println("✅ Mano inicial repartida: " + manoJugador.size() + " cartas");
    }

    /**
     * Coloca la primera carta en la mesa y actualiza la suma.
     */
    private void colocarCartaInicialEnMesa() {
        Card cartaInicial = mazo.tomarCarta();

        if (cartaInicial != null) {
            cartasEnMesa.add(cartaInicial);
            sumaMesa = cartaInicial.getValue();

            System.out.println("🃏 Carta inicial en mesa: " + cartaInicial.getName() + " (Valor: " + cartaInicial.getValue() + ")");
            System.out.println("📊 Suma inicial de la mesa: " + sumaMesa);

            actualizarMesaCentral();
            actualizarLabelSuma();
        }
    }

    /**
     * Actualiza la visualización de la mesa central con la última carta jugada.
     */
    private void actualizarMesaCentral() {
        mesaCentral.getChildren().clear();

        if (!cartasEnMesa.isEmpty()) {
            Card ultimaCarta = cartasEnMesa.get(cartasEnMesa.size() - 1);

            // Crear StackPane con fondo y carta
            StackPane cartaPane = new StackPane();
            cartaPane.getChildren().addAll(
                    ultimaCarta.getBackground(),
                    ultimaCarta.getRectangle()
            );

            mesaCentral.getChildren().add(cartaPane);
        }
    }

    /**
     * Actualiza el label que muestra la suma actual de la mesa.
     */
    private void actualizarLabelSuma() {
        labelSumaMesa.setText("Suma: " + sumaMesa);

        // Cambiar color según la cercanía a 50
        if (sumaMesa > 40) {
            labelSumaMesa.setStyle("-fx-text-fill: red; -fx-font-size: 24px; -fx-font-weight: bold;");
        } else if (sumaMesa > 30) {
            labelSumaMesa.setStyle("-fx-text-fill: orange; -fx-font-size: 24px; -fx-font-weight: bold;");
        } else {
            labelSumaMesa.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");
        }
    }

    /**
     * Actualiza la visualización de las cartas del jugador.
     */
    private void actualizarCartasJugador() {
        cartasJugador.getChildren().clear();

        for (Card carta : manoJugador) {
            StackPane cartaPane = new StackPane();
            cartaPane.getChildren().addAll(
                    carta.getBackground(),
                    carta.getRectangle()
            );

            // Agregar efecto hover
            cartaPane.setOnMouseEntered(e -> {
                cartaPane.setScaleX(1.1);
                cartaPane.setScaleY(1.1);
                cartaPane.setStyle("-fx-cursor: hand;");
            });

            cartaPane.setOnMouseExited(e -> {
                cartaPane.setScaleX(1.0);
                cartaPane.setScaleY(1.0);
            });

            // Click para jugar la carta
            cartaPane.setOnMouseClicked(e -> jugarCarta(carta, cartaPane));

            cartasJugador.getChildren().add(cartaPane);
        }
    }

    /**
     * Intenta jugar una carta del jugador en la mesa.
     */
    private void jugarCarta(Card carta, StackPane cartaPane) {
        // Calcular nueva suma
        int nuevaSuma = sumaMesa + carta.getValue();

        // Si es As, elegir el mejor valor (1 o 10)
        if (carta.isAce()) {
            int sumaConAs10 = sumaMesa + 10;
            int sumaConAs1 = sumaMesa + 1;

            // Elegir la opción que no pase de 50
            if (sumaConAs10 <= 50) {
                nuevaSuma = sumaConAs10;
                System.out.println("🃏 As jugado con valor 10");
            } else {
                nuevaSuma = sumaConAs1;
                System.out.println("🃏 As jugado con valor 1");
            }
        }

        // Verificar si la jugada es válida
        if (nuevaSuma > 50) {
            System.err.println("❌ No puedes jugar esa carta, excede 50");
            System.err.println("   Suma actual: " + sumaMesa + " + Carta: " + carta.getValue() + " = " + nuevaSuma);

            // Efecto visual de rechazo
            cartaPane.setStyle("-fx-effect: dropshadow(gaussian, red, 10, 0.5, 0, 0);");

            // Quitar efecto después de 500ms
            javafx.application.Platform.runLater(() -> {
                try {
                    Thread.sleep(500);
                    cartaPane.setStyle("");
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            });

            return;
        }

        // Jugada válida: actualizar estado
        sumaMesa = nuevaSuma;
        manoJugador.remove(carta);
        cartasEnMesa.add(carta);

        System.out.println("✅ Carta jugada: " + carta.getName() + " (Valor: " + carta.getValue() + ")");
        System.out.println("📊 Nueva suma: " + sumaMesa);

        // Actualizar visualización
        actualizarMesaCentral();
        actualizarCartasJugador();
        actualizarLabelSuma();

        // Marcar que ya jugó carta y habilitar botón de robar
        cartaJugadaEnTurno = true;
        btnTomarCarta.setDisable(false);

        // Verificar si el jugador puede seguir jugando
        verificarSiPuedeJugar();
    }

    /**
     * Verifica si el jugador tiene al menos una carta jugable.
     */
    private void verificarSiPuedeJugar() {
        boolean puedeJugar = false;

        for (Card carta : manoJugador) {
            int valorCarta = carta.getValue();

            // Si es As, considerar ambos valores
            if (carta.isAce()) {
                if (sumaMesa + 1 <= 50 || sumaMesa + 10 <= 50) {
                    puedeJugar = true;
                    break;
                }
            } else {
                if (sumaMesa + valorCarta <= 50) {
                    puedeJugar = true;
                    break;
                }
            }
        }

        if (!puedeJugar && manoJugador.size() > 0) {
            System.err.println("💀 JUGADOR ELIMINADO - No tiene cartas jugables");
            // Aquí implementarías la lógica de eliminación
        }
    }

    /**
     * Permite al jugador tomar una carta del mazo.
     */
    @FXML
    private void tomarCarta() {
        // Verificar que haya jugado una carta primero
        if (!cartaJugadaEnTurno) {
            System.err.println("⚠️ Debes jugar una carta antes de robar");
            return;
        }

        // Verificar si el mazo necesita recargarse
        if (mazo.estaVacio()) {
            recargarMazo();
        }

        Card nuevaCarta = mazo.tomarCarta();

        if (nuevaCarta != null) {
            manoJugador.add(nuevaCarta);
            System.out.println("✅ Carta tomada: " + nuevaCarta.getName());

            actualizarCartasJugador();

            // Resetear turno
            cartaJugadaEnTurno = false;
            btnTomarCarta.setDisable(true);

            System.out.println("🔄 Turno terminado. Ahora juega la máquina...");
            // Aquí llamarías al turno de la máquina
        } else {
            System.err.println("❌ No se pudo tomar carta del mazo");
        }
    }

    /**
     * Recarga el mazo con las cartas de la mesa (excepto la última).
     */
    private void recargarMazo() {
        System.out.println("♻️ Recargando mazo...");

        if (cartasEnMesa.size() <= 1) {
            System.err.println("⚠️ No hay suficientes cartas en la mesa para recargar");
            return;
        }

        // Guardar la última carta
        Card ultimaCarta = cartasEnMesa.remove(cartasEnMesa.size() - 1);

        // Agregar las demás cartas al mazo
        for (Card carta : cartasEnMesa) {
            mazo.agregarCarta(carta);
        }

        // Limpiar lista de cartas en mesa y dejar solo la última
        cartasEnMesa.clear();
        cartasEnMesa.add(ultimaCarta);

        // Barajar el mazo
        mazo.barajar();

        System.out.println("✅ Mazo recargado con " + mazo.cartasRestantes() + " cartas");
    }

    /**
     * Configura la imagen del mazo (carta boca abajo).
     */
    private void configurarImagenMazo() {
        InputStream is = getClass().getResourceAsStream("/org/example/cincuentazo/cartamaquina.png");
        if (is != null) {
            mazoCartas.setImage(new Image(is));
            mazoCartas.setFitWidth(80);
            mazoCartas.setFitHeight(110);
        }
    }

    /**
     * Genera las zonas visuales de los jugadores máquina.
     */
    private void generarMaquinas() {
        contenedorMaquina.getChildren().clear();

        InputStream is = getClass().getResourceAsStream("/org/example/cincuentazo/cartamaquina.png");
        Image cartaBocaAbajo = null;
        if (is != null) {
            cartaBocaAbajo = new Image(is);
        }

        for (int i = 0; i < numContrincantes; i++) {
            VBox zonaMaquina = new VBox(5);
            zonaMaquina.setStyle("-fx-alignment: center;");

            // Label con nombre de la máquina
            Label nombreMaquina = new Label("Máquina " + (i + 1));
            nombreMaquina.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");

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

            zonaMaquina.getChildren().addAll(nombreMaquina, filaCartas);
            contenedorMaquina.getChildren().add(zonaMaquina);
        }
    }

    /**
     * Obtiene la suma actual de la mesa.
     */
    public int getSumaMesa() {
        return sumaMesa;
    }

    /**
     * Obtiene el número de cartas en la mesa.
     */
    public int getNumeroCartasEnMesa() {
        return cartasEnMesa.size();
    }
}