module org.example.jeudenimfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens org.example.jeudenimfx to javafx.fxml;
    exports org.example.jeudenimfx;
}