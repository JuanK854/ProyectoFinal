package mx.edu.utch.proyectofinal.model;

public class Movimiento {
    private int             id;
    private String      fecha;
    private String      tipo;
    private double    monto;
    private String      descripcion;

    public Movimiento() {
        this(0, "", "", 0.0, "");
    }

    public Movimiento(final int id, final String fecha, final String tipo, final double monto, final String descripcion) {
        this.id                       = id;
        this.fecha               = fecha;
        this.tipo                   = tipo;
        this.monto              = monto;
        this.descripcion    = descripcion;
    }


    public Movimiento(final String fecha, final String tipo, final double monto, final String descripcion) {
        this(0, fecha, tipo, monto, descripcion);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getMonto() {
        return monto;
    }
    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Movimiento{" +
                "id=" + id +
                ", fecha='" + fecha + '\'' +
                ", tipo='" + tipo + '\'' +
                ", monto=" + monto +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
