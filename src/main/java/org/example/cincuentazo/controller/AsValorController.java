package org.example.cincuentazo.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Esta clase se hace con el fin de que el usuario pueda escoger un valor para el As
 */

public class AsValorController {

    private boolean resultado;   // true = 10, false = 1

    @FXML
    private Button btnDiez;

    @FXML
    private Button btnUno;

    public boolean getResultado() {
        return resultado;
    }

    @FXML
    private void botonDiez() {
        resultado = true;   // 10
        cerrarVentana();
    }

    @FXML
    private void botonUno() {
        resultado = false;  // 1
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) btnDiez.getScene().getWindow();
        stage.close();
    }
}
