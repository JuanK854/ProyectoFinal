module mx.edu.utch.proyectofinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    requires org.xerial.sqlitejdbc;

    opens mx.edu.utch.proyectofinal to javafx.fxml;
    exports mx.edu.utch.proyectofinal;
}