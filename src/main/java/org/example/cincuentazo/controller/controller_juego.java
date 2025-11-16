package org.example.cincuentazo.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.example.cincuentazo.model.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

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
    private  Button btnPasarTurno;

    @FXML
    private Label labelCartasRestantes; // Muestra cartas restantes en el mazo

    @FXML
    private Label labelTurnoActual; // Muestra de quién es el turno

    @FXML
    private Label labelEstadoJuego; // Muestra el estado actual del juego

    private Deck mazo;
    private final JugadorHumano usuario= new JugadorHumano();
    private final Mesa mesa= new Mesa();
    private int numContrincantes = 1;
    private int botsEnJuego =0;
    private boolean cartaJugadaEnTurno = false; // Control para verificar si jugó carta antes de robar
    private final List<JugadorMaquina> bots= new ArrayList<>();;
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
        usuario.manoInicial(mazo);
        // Mostrar cartas del jugador
        actualizarCartasJugador();
        for (JugadorMaquina bot : bots){
            bot.manoInicial(mazo);
        }
        System.out.println("✅ Mano inicial repartida: 4 cartas");
        actualizarCartasMazo();
    }

    /**
     * Coloca la primera carta en la mesa y actualiza la suma.
     */
    private void colocarCartaInicialEnMesa() {
        Card cartaInicial = mazo.tomarCarta();

        if (cartaInicial != null) {
            mesa.agregarCarta(cartaInicial);
            System.out.println("🃏 Carta inicial en mesa: " + cartaInicial.getName() + " (Valor: " + cartaInicial.getValue() + ")");
            System.out.println("📊 Suma inicial de la mesa: " + mesa.getValorMesa());

            actualizarMesaCentral();
            actualizarLabelSuma();
            actualizarCartasMazo();
        }
    }

    /**
     * Actualiza la visualización de la mesa central con la última carta jugada.
     */
    private void actualizarMesaCentral() {
        mesaCentral.getChildren().clear();

        if (!mesa.estaVacio()) {
            Card ultimaCarta = mesa.ultimaCarta();

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
        int valorMesa = mesa.getValorMesa();
        labelSumaMesa.setText(String.valueOf(valorMesa));

        // Cambiar color según la cercanía a 50
        if (valorMesa > 40) {
            labelSumaMesa.setStyle("-fx-text-fill: red; -fx-font-size: 24px; -fx-font-weight: bold;");
        } else if (valorMesa > 30) {
            labelSumaMesa.setStyle("-fx-text-fill: orange; -fx-font-size: 24px; -fx-font-weight: bold;");
        } else {
            labelSumaMesa.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");
        }
    }

    /**
     * actualiza el label que muestra las cartas que le quedan al mazo
     */
    private void actualizarCartasMazo(){
        labelCartasRestantes.setText(String.valueOf(mazo.cartasRestantes()));
    }
    /**
     * Actualiza la visualización de las cartas del jugador.
     */
    private void actualizarCartasJugador() {
        cartasJugador.getChildren().clear();
        List<Card> manoUsuario = usuario.getMano();
        for (Card carta : manoUsuario) {
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
     * actualiza el label muestra el turno del jugador
     * @param turno jugador al que le corresponde el turno
     */
    private void actualizarTurno(String turno){
        labelTurnoActual.setText(turno);
    }
    /**
     * Intenta jugar una carta del jugador en la mesa.
     */
    private void jugarCarta(Card carta, StackPane cartaPane) {
        // Calcular nueva suma
        int nuevaSuma = mesa.getValorMesa() + carta.getValue();

        // Si es As, elegir el mejor valor (1 o 10)
        if (carta.isAce()) {
            if (pedirValorAs()){
                carta.getAlternativeValue();
            }
        }

        // Verificar si la jugada es válida
        if (nuevaSuma > 50) {
            System.err.println("❌ No puedes jugar esa carta, excede 50");
            System.err.println("   Suma actual: " + mesa.getValorMesa() + " + Carta: " + carta.getValue() + " = " + nuevaSuma);

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
        usuario.jugarCarta(mesa, carta);

        System.out.println("✅ Carta jugada: " + carta.getName() + " (Valor: " + carta.getValue() + ")");
        System.out.println("📊 Nueva suma: " + mesa.getValorMesa());

        // Actualizar visualización
        actualizarMesaCentral();
        actualizarCartasJugador();
        actualizarLabelSuma();

        // Marcar que ya jugó carta y habilitar botón de robar
        cartaJugadaEnTurno = true;
        btnTomarCarta.setDisable(false);
    }

    /**
     * Verifica si el jugador tiene al menos una carta jugable.
     */
    private boolean verificarSiPuedeJugar() {
        int cartasJugables=0;
        for (Card carta : usuario.getMano()) {
            if (carta.getValue()+mesa.getValorMesa() <= 50) {
                cartasJugables++;
            }
        }
        if (cartasJugables==0) {
            return false;
        } else  {
            return true;
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
            mesa.agregarCartasBaraja(mazo);
            mazo.barajar();
        }

        usuario.robarCarta(mazo);
        actualizarCartasJugador();
        actualizarCartasMazo();
        btnTomarCarta.setDisable(true);
    }

    /**
     * Permite al jugador pasar de turno para que comiencen a jugar la(s) maquina(s)
     */
    @FXML
    private void pasarTurno(){
        if (!cartaJugadaEnTurno) {
            mostrarAlerta("Alerta", "Debes haber jugado una carta para pasar de turno");
            return;
        }

        if (usuario.cantidadCartasMano() != 4) {
            mostrarAlerta("Alerta", "Debes tener 4 cartas en tu mano para pasar de turno");
            return;
        }
        btnTomarCarta.setDisable(true);
        btnPasarTurno.setDisable(true);
        botsEnJuego= 0;
        jugarBotsPorTurno(0);
    }

    /**
     * Metodo que gestiona el turno de los bots para jugar sus cartas y robar cartas del mazo
     * @param index indice del bot al que le toca el turno
     */
    private void jugarBotsPorTurno(int index) {

        if (index >= bots.size()) {
            // Ya jugaron todos los bots y vuelve al jugador
            volverTurnoJugador();
            return;
        }

        JugadorMaquina bot = bots.get(index);

        if (!bot.getPuedeJugar(mesa)) {
            // Saltar bots que no puedan jugar
            mostrarAlerta("JUGADOR ELIMINADO", "La "+bot.getNombre()+ "no tiene más cartas jugables, sale del juego.");
            bot.devolverCartas(mazo);
            jugarBotsPorTurno(index + 1);
            return;
        }

        actualizarTurno(bot.getNombre());
        botsEnJuego++;
        new Thread(() -> {
            try {

                Thread.sleep(3500);

                bot.jugarCarta(mesa, bot.elegirMejorCarta(mesa));
                if(mazo.estaVacio()) {
                    mesa.agregarCartasBaraja(mazo);
                    mazo.barajar();
                }
                bot.robarCarta(mazo);

                Platform.runLater(() -> {
                    actualizarMesaCentral();
                    actualizarLabelSuma();
                    actualizarCartasMazo();
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

            Platform.runLater(() -> jugarBotsPorTurno(index + 1));

        }).start();
    }

    /**
     * Despues del turno de los bots gestiona el turno del jugador y verifica si este gano o tiene alguna carta jugable
     */
    private void volverTurnoJugador() {
        actualizarTurno("Jugador");
        btnTomarCarta.setDisable(true);
        btnPasarTurno.setDisable(false);
        if (botsEnJuego == 0) {
            mostrarAlerta("GANASTE","No hay mas máquinas que puedan seguir jugando.");
            Platform.exit();
        }
        if(!verificarSiPuedeJugar()) {
            mostrarAlerta("PERDISTE", "No tienes ninguna carta jugable, perdiste el juego.");
            Platform.exit();
        }
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
            bots.add(new JugadorMaquina());
        }
        asignarNombres(bots);
        iniciarJuego();
    }

    /**
     * le asigna nombres a los bots del juego
     * @param jugadores lista con todos los bots del juego
     */
    private void asignarNombres(List<JugadorMaquina> jugadores) {
        for (int i = 0; i< jugadores.size(); i++) {
            jugadores.get(i).setNombre("Máquina " + (i + 1));
        }
    }

    /**
     * este metodo permite mostrar una alerta al jugador y darle feedback de la partida
     * @param titulo titulo de la alerta
     * @param contenido contenido de la alerta
     */
    private void mostrarAlerta(String titulo, String contenido) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
    /**
     * Muestra una ventana emergente para que el usuario pueda escoger el valor del AS
     */
    public  boolean pedirValorAs() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/cincuentazo/as-eleccion.fxml"));
            Parent root = loader.load();
            AsValorController controller = loader.getController();
            Stage stage = new Stage();
            stage.setTitle("Escoge valor del As");
            stage.setScene(new Scene(root));
            stage.setResizable(false);


            stage.showAndWait();

            return controller.getResultado();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}