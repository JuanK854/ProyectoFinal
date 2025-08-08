package mx.edu.utch.proyectofinal;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

public class DashboardController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label balanceLabel;

    @FXML
    private Button btnRegistrarGasto;

    @FXML
    private Button btnRegistrarIngreso;

    @FXML
    private TextField descripcionField;

    @FXML
    private TableColumn<?, ?> descripcionTable;

    @FXML
    private DatePicker fechaPicker;

    @FXML
    private TableColumn<?, ?> fechaTable;

    @FXML
    private TextField montoField;

    @FXML
    private TableColumn<?, ?> montoTable;

    @FXML
    private TableColumn<?, ?> tipoTable;

    @FXML
    void initialize() {
        assert balanceLabel != null : "fx:id=\"balanceLabel\" was not injected: check your FXML file 'Dashboard-view.fxml'.";
        assert btnRegistrarGasto != null : "fx:id=\"btnRegistrarGasto\" was not injected: check your FXML file 'Dashboard-view.fxml'.";
        assert btnRegistrarIngreso != null : "fx:id=\"btnRegistrarIngreso\" was not injected: check your FXML file 'Dashboard-view.fxml'.";
        assert descripcionField != null : "fx:id=\"descripcionField\" was not injected: check your FXML file 'Dashboard-view.fxml'.";
        assert descripcionTable != null : "fx:id=\"descripcionTable\" was not injected: check your FXML file 'Dashboard-view.fxml'.";
        assert fechaPicker != null : "fx:id=\"fechaPicker\" was not injected: check your FXML file 'Dashboard-view.fxml'.";
        assert fechaTable != null : "fx:id=\"fechaTable\" was not injected: check your FXML file 'Dashboard-view.fxml'.";
        assert montoField != null : "fx:id=\"montoField\" was not injected: check your FXML file 'Dashboard-view.fxml'.";
        assert montoTable != null : "fx:id=\"montoTable\" was not injected: check your FXML file 'Dashboard-view.fxml'.";
        assert tipoTable != null : "fx:id=\"tipoTable\" was not injected: check your FXML file 'Dashboard-view.fxml'.";

    }

}