package org.example.cincuentazo.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Clase encargada de mostrar la vista de la mesa inicial.
 */
public class view_mesa {

    public void mostrar(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/cincuentazo/hello-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("El 50tazo");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
