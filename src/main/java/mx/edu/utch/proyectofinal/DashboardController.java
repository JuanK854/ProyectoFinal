package mx.edu.utch.proyectofinal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import mx.edu.utch.proyectofinal.model.Movimiento;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DashboardController {

    @FXML private Label balanceLabel;
    @FXML private TextField descripcionField;
    @FXML private TextField montoField;
    @FXML private DatePicker fechaPicker;
    @FXML private Button btnRegistrarIngreso;
    @FXML private Button btnRegistrarGasto;

    @FXML private TableView<Movimiento> movimientosTable;
    @FXML private TableColumn<Movimiento, String> fechaTable;
    @FXML private TableColumn<Movimiento, String> tipoTable;
    @FXML private TableColumn<Movimiento, Double> montoTable;
    @FXML private TableColumn<Movimiento, String> descripcionTable;

    private final ObservableList<Movimiento> datos = FXCollections.observableArrayList();
    private double balance = 0.0;
    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    private void initialize() {
        fechaTable.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        tipoTable.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        montoTable.setCellValueFactory(new PropertyValueFactory<>("monto"));
        descripcionTable.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        movimientosTable.setItems(datos);

        fechaPicker.setValue(LocalDate.now());
        fechaPicker.setEditable(false);

        btnRegistrarIngreso.setOnAction(e -> registrar("Ingreso"));
        btnRegistrarGasto.setOnAction(e -> registrar("Gasto"));

        actualizarBalance();
    }

    private void registrar(String tipo) {
        String desc = safe(descripcionField.getText());
        String montoTxt = safe(montoField.getText());
        LocalDate fecha = fechaPicker.getValue();

        if (desc.isEmpty() || montoTxt.isEmpty() || fecha == null) {
            info("Completa descripción, monto y fecha.");
            return;
        }

        double monto;
        try { monto = Double.parseDouble(montoTxt); }
        catch (NumberFormatException ex) { info("Monto inválido (ej. 150.50)."); return; }
        if (monto <= 0) { info("El monto debe ser mayor que cero."); return; }

        datos.add(new Movimiento(DF.format(fecha), tipo, monto, desc));
        balance += tipo.equals("Ingreso") ? monto : -monto;
        actualizarBalance();
        limpiar();
        info("Movimiento registrado correctamente.");
    }

    private void actualizarBalance() { balanceLabel.setText(String.format("$%.2f", balance)); }
    private void limpiar() { descripcionField.clear(); montoField.clear(); fechaPicker.setValue(LocalDate.now()); }
    private void info(String m) { new Alert(Alert.AlertType.INFORMATION, m).show(); }
    private String safe(String s) { return s == null ? "" : s.trim(); }
}
