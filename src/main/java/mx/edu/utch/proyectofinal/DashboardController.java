package mx.edu.utch.proyectofinal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import mx.edu.utch.proyectofinal.db.DataBase;
import mx.edu.utch.proyectofinal.model.Movimiento;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DashboardController {

    @FXML private Label balanceLabel;
    @FXML private TextField descripcionField;
    @FXML private TextField montoField;
    @FXML private DatePicker fechaPicker;

    @FXML private Button btnRegistrarIngreso;
    @FXML private Button btnRegistrarGasto;
    @FXML private Button btnBorrar;

    @FXML private TableView<Movimiento> movimientosTable;
    @FXML private TableColumn<Movimiento, String> fechaTable;
    @FXML private TableColumn<Movimiento, String> tipoTable;
    @FXML private TableColumn<Movimiento, Double> montoTable;
    @FXML private TableColumn<Movimiento, String> descripcionTable;

    private final ObservableList<Movimiento> datos = FXCollections.observableArrayList();
    private double balance = 0.0;

    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String TIPO_GASTO = "Gasto";

    @FXML
    private void initialize() {
        DataBase.init();

        btnRegistrarIngreso.setDisable(true);
        btnRegistrarGasto.setDisable(true);
        descripcionField.textProperty().addListener((obs, o, n) -> validarCampos());
        montoField.textProperty().addListener((obs, o, n) -> validarCampos());
        fechaPicker.valueProperty().addListener((obs, o, n) -> validarCampos());

        // Columnas y  getters del POJO
        fechaTable.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        tipoTable.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        montoTable.setCellValueFactory(new PropertyValueFactory<>("monto"));
        descripcionTable.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        movimientosTable.setItems(datos);

        fechaPicker.setValue(LocalDate.now());
        fechaPicker.setEditable(false);

        btnRegistrarIngreso.setOnAction(e -> registrar("Ingreso"));
        btnRegistrarGasto.setOnAction(e -> registrar("Gasto"));
        btnBorrar.setOnAction(e -> borrarSeleccionado());

        recargarTablaBalance();
    }

    private void registrar(String tipo) {
        final String descripcion = safe(descripcionField.getText());
        final String montoTxt    = safe(montoField.getText());
        final LocalDate fecha    = fechaPicker.getValue();

//        if (descripcion.isEmpty() || montoTxt.isEmpty() || fecha == null) {
//            info(MSG_CAMPOS_INCOMPLETOS);
//            return;
//        }

        final double monto;
        try {
            monto = Double.parseDouble(montoTxt);
        } catch (NumberFormatException ex) {
            info("Monto inválido ingrese solo numeros");
            return;
        }
        if (monto <= 0) {
            info("El monto debe ser mayor que cero.");
            return;
        }

        if (esGasto(tipo) && excedePresupuesto(monto)) {
            warn("Este gasto supera el presupuesto");
            return;
        }

        final Movimiento mov = new Movimiento(DF.format(fecha), tipo, monto, descripcion);
        try {
            DataBase.insertar(mov);
        } catch (RuntimeException ex) {
            info("No se pudo guardar el movimiento: ");
            return;
        }

        recargarTablaBalance();
        limpiar();
        info("Movimiento registrado correctamente.");
    }

    private void recargarTablaBalance() {
        try {
            List<Movimiento> lista = DataBase.listar();
            datos.setAll(lista);
            balance = DataBase.balance();
            actualizarBalance();
        } catch (RuntimeException ex) {
            info("Error al cargar datos: " );
        }
    }

    private void actualizarBalance() {
        balanceLabel.setText(String.format("$%.2f", balance));
    }

    private void limpiar() {
        descripcionField.clear();
        montoField.clear();
        fechaPicker.setValue(LocalDate.now());
    }

    private void info(String m) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, m, ButtonType.OK);
        a.setHeaderText(null);
        a.show();
    }

    private void warn(String m) {
        Alert a = new Alert(Alert.AlertType.WARNING, m, ButtonType.OK);
        a.setHeaderText(null);
        a.show();
    }

    private String safe(String s) {
        return s == null ? "" : s.trim();
    }

    private boolean esGasto(String tipo) {
        return TIPO_GASTO.equalsIgnoreCase(tipo);
    }

    private boolean excedePresupuesto(double montoGasto) {
        return (balance - montoGasto) < 0.0;
    }

    private void borrarSeleccionado() {
        Movimiento seleccionado = movimientosTable.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            info("Selecciona un movimiento para borrar.");
            return;
        }
        DataBase.borrar(seleccionado.getId());
        recargarTablaBalance();
        info("Movimiento borrado correctamente.");
    }

    private void validarCampos() {
        boolean camposLlenos =
                !descripcionField.getText().trim().isEmpty() &&
                        !montoField.getText().trim().isEmpty() &&
                        fechaPicker.getValue() != null;

        btnRegistrarIngreso.setDisable(!camposLlenos);
        btnRegistrarGasto.setDisable(!camposLlenos);
    }
}
