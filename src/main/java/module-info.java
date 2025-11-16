module org.example.cincuentazo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.smartcardio;

    opens org.example.cincuentazo.controller to javafx.fxml;
    opens org.example.cincuentazo.view to javafx.fxml;

    exports org.example.cincuentazo.controller;
    exports org.example.cincuentazo.view;
}
