package org.example.cincuentazo.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Controlador de la vista principal (mesa inicial del juego).
 * Gestiona la selección del número de contrincantes y el inicio del juego.
 */
public class controller_mesa {

    @FXML
    private Label lblTitulo;

    @FXML
    private Label lblSubtitulo;

    @FXML
    private ComboBox<Integer> comboContrincantes;

    @FXML
    private Button btnIniciar;

    private int numContrincantes = 1;

    @FXML
    public void initialize() {
        // Inicializa el ComboBox con opciones 1, 2 y 3
        comboContrincantes.getItems().addAll(1, 2, 3);
        comboContrincantes.setValue(1); // valor por defecto
        comboContrincantes.setOnAction(e -> numContrincantes = comboContrincantes.getValue());
    }
    /**
    @FXML
    private void iniciarJuego() {
        try {
            // Obtener la ventana actual y cerrarla
            Stage stageActual = (Stage) btnIniciar.getScene().getWindow();
            stageActual.close();

            // Cargar la nueva vista (mesa de juego)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/cincuentazo/mesa-juego.fxml"));
            Parent root = loader.load();

            // Pasar el número de contrincantes al nuevo controlador
            controller_juego controller = loader.getController();
            controller.setNumContrincantes(numContrincantes);

            // Crear y mostrar la nueva ventana
            Stage nuevaVentana = new Stage();
            nuevaVentana.setTitle("El 50tazo - En juego");
            nuevaVentana.setScene(new Scene(root, 1000, 700));
            nuevaVentana.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}*/
    @FXML
    private void iniciarJuego() {
        try {
            System.out.println("Iniciando juego...");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/cincuentazo/mesa-juego.fxml"));
            System.out.println("FXMLLoader creado...");
            Parent root = loader.load();
            System.out.println("FXML cargado correctamente");

            controller_juego controller = loader.getController();
            controller.setNumContrincantes(numContrincantes);

            Stage nuevaVentana = new Stage();
            nuevaVentana.setTitle("El 50tazo - En juego");
            nuevaVentana.setScene(new Scene(root, 1000, 700));
            nuevaVentana.show();

            System.out.println("Ventana mostrada.");

            Stage stageActual = (Stage) btnIniciar.getScene().getWindow();
            stageActual.close();

        } catch (Exception e) {
            System.err.println("❌ Error al iniciar juego: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

