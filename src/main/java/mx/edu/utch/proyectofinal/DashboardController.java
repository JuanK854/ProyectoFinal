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

    @FXML
    private void initialize() {
        // Inicializa DB (crea tabla si no existe) y carga datos
        DataBase.init();

        // Configurar columnas con getters del POJO
        fechaTable.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        tipoTable.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        montoTable.setCellValueFactory(new PropertyValueFactory<>("monto"));
        descripcionTable.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        // Fuente de datos para la tabla
        movimientosTable.setItems(datos);

        // Fecha por defecto y deshabilitar edición por teclado
        fechaPicker.setValue(LocalDate.now());
        fechaPicker.setEditable(false);

        // Acciones
        btnRegistrarIngreso.setOnAction(e -> registrar("Ingreso"));
        btnRegistrarGasto.setOnAction(e -> registrar("Gasto"));
        btnBorrar.setOnAction(e -> borrarSeleccionado());

        // Cargar datos iniciales y balance desde la DB
        recargarTablaBalance();
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
        try {
            monto = Double.parseDouble(montoTxt);
        } catch (NumberFormatException ex) {
            info("Monto inválido introduzca solo numeros");
            return;
        }
        if (monto <= 0) {
            info("El monto debe ser mayor que cero.");
            return;
        }

        // Guardar en DB
        Movimiento m = new Movimiento(DF.format(fecha), tipo, monto, desc);
        try {
            DataBase.insertar(m);
        } catch (RuntimeException ex) {
            info("No se pudo guardar el movimiento: " + ex.getMessage());
            return;
        }

        // Recargar datos desde DB y limpiar formulario
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
            info("Error al cargar datos: " + ex.getMessage());
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

    private String safe(String s) {
        return s == null ? "" : s.trim();
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
}
