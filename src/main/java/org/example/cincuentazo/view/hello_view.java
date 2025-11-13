package org.example.cincuentazo.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Clase encargada de mostrar la vista de la mesa inicial.
 */
public class hello_view extends Stage{



    /**
     * Clase que inicializa la vista de la interfaz inicial del juego por medio del FXML layout y configuracion.
     * The window´s poperties, like title, scene and others.
     *
     * @throws IOException if there is an issue loading the FXML file
     */
    public hello_view() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/cincuentazo/hello-view.fxml"));
        Parent root = loader.load();
        this.setTitle("El 50tazo");
        Scene scene = new Scene(root);
        this.setScene(scene);
        this.setResizable(false);
        this.show();
    }

    /**
     * Holds the single instance of GameView, following the Singleton pattern.
     */
    private static class hello_view_holder {
        private static hello_view INSTANCE;
    }

    /**
     * Provides access to the single instance of GameView.
     * If the instance does not exist, it is created; otherwise, the existing
     * instance is returned.
     *
     * @return the singleton instance of GameView
     * @throws IOException if there is an issue loading the FXML file
     */
    public static hello_view getInstance() throws IOException {

        if (hello_view_holder.INSTANCE == null) {
            return hello_view_holder.INSTANCE = new hello_view();
        } else {
            return hello_view_holder.INSTANCE;
        }
    }
}
