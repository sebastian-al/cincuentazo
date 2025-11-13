package org.example.cincuentazo.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.cincuentazo.controller.controller_juego;

import java.io.IOException;

/**
 * Representa la ventana principal del juego cincuentazo game.
 * Esta clase extiende el escenario JavaFX y carga game-view.fxml
 * archivo para configurar los componentes iniciales de la interfaz de usuario del juego.

 * GameView utiliza el patrón Singleton para asegurar que solo exista una instancia de
 * la vista del juego se crea y se gestiona a la vez.
 */

public class mesa_vista extends Stage{


        private final controller_juego controllerJuego;
    private static mesa_vista instance;
    private Stage stage;
    private Parent root;
    private controller_juego controller;
    private int numContrincantes = 1;

        /**
         *  Inicializa la GameView cargando el diseño FXML y configurando
         *  * el título de la ventana, la escena y otras propiedades.
         *  *
         *  * @throws IOException si ocurre un problema al cargar el archivo FXML
         */
        public mesa_vista() throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/cincuentazo/mesa-juego.fxml"));
            Parent root = loader.load();
            this.setTitle("Cincuentazo");
            this.controllerJuego = loader.getController();
            Scene scene = new Scene(root);
            this.setScene(scene);
            this.setResizable(false);
            this.show();
        }

        /**
         * Holds the single instance of GameView, following the Singleton pattern.
         */
        private static class mesa_vista_holder {
            private static mesa_vista INSTANCE;
        }

        /**
         * Provides access to the single instance of GameView.
         * If the instance does not exist, it is created; otherwise, the existing
         * instance is returned.
         *
         * @return the singleton instance of GameView
         * @throws IOException if there is an issue loading the FXML file
         */
        public static mesa_vista getInstance() throws IOException {

            if (mesa_vista_holder.INSTANCE == null) {
                return mesa_vista_holder.INSTANCE = new mesa_vista();
            } else {
                return mesa_vista_holder.INSTANCE;
            }
        }

        public controller_juego getontrollerJuego() {
            return controllerJuego;
        }

    public void setNumContrincantes(int numContrincantes) {
        this.numContrincantes = numContrincantes;
        if (controller != null) {
            controller.setNumContrincantes(numContrincantes);
        }
    }
     }

