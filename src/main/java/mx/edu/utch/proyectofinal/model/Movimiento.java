package mx.edu.utch.proyectofinal.model;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Movimiento {

    private final ObjectProperty<LocalDate> fecha;
    private final StringProperty tipo;
    private final DoubleProperty monto;
    private final StringProperty descripcion;

    public Movimiento(LocalDate fecha, String tipo, double monto, String descripcion) {
        this.fecha = new SimpleObjectProperty<>(fecha);
        this.tipo = new SimpleStringProperty(tipo);
        this.monto = new SimpleDoubleProperty(monto);
        this.descripcion = new SimpleStringProperty(descripcion);
    }

    // Getters y Setters con propiedades para JavaFX
    public LocalDate getFecha() {
        return fecha.get();
    }

    public void setFecha(LocalDate fecha) {
        this.fecha.set(fecha);
    }

    public ObjectProperty<LocalDate> fechaProperty() {
        return fecha;
    }

    public String getTipo() {
        return tipo.get();
    }

    public void setTipo(String tipo) {
        this.tipo.set(tipo);
    }

    public StringProperty tipoProperty() {
        return tipo;
    }

    public double getMonto() {
        return monto.get();
    }

    public void setMonto(double monto) {
        this.monto.set(monto);
    }

    public DoubleProperty montoProperty() {
        return monto;
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public StringProperty descripcionProperty() {
        return descripcion;
    }
}

