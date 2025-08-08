package mx.edu.utch.proyectofinal.db;

import mx.edu.utch.proyectofinal.model.Movimiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {

    // Base de datos en la carpeta principal del proyecto
    private static final String URL = "jdbc:sqlite:finanzas.db";

    public static void init() {
        String sql = """
            CREATE TABLE IF NOT EXISTS movimientos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                fecha TEXT NOT NULL,
                tipo TEXT NOT NULL CHECK(tipo IN ('Ingreso','Gasto')),
                monto REAL NOT NULL CHECK(monto > 0),
                descripcion TEXT NOT NULL
            );
            """;
        try (Connection con = DriverManager.getConnection(URL);
             Statement st = con.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Error inicializando la base de datos", e);
        }
    }

    public static void insertar(Movimiento m) {
        String sql = "INSERT INTO movimientos(fecha, tipo, monto, descripcion) VALUES(?,?,?,?)";
        try (Connection con = DriverManager.getConnection(URL);
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, m.getFecha());
            ps.setString(2, m.getTipo());
            ps.setDouble(3, m.getMonto());
            ps.setString(4, m.getDescripcion());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error insertando movimiento", e);
        }
    }

    public static List<Movimiento> listar() {
        String sql = "SELECT fecha, tipo, monto, descripcion FROM movimientos ORDER BY id DESC";
        List<Movimiento> out = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(URL);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                out.add(new Movimiento(
                        rs.getString("fecha"),
                        rs.getString("tipo"),
                        rs.getDouble("monto"),
                        rs.getString("descripcion")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error listando movimientos", e);
        }
        return out;
    }

    public static double balance() {
        String sql = """
            SELECT COALESCE(SUM(
                CASE WHEN tipo='Ingreso' THEN monto
                     WHEN tipo='Gasto'   THEN -monto
                     ELSE 0 END
            ),0) AS balance FROM movimientos
            """;
        try (Connection con = DriverManager.getConnection(URL);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            return rs.next() ? rs.getDouble("balance") : 0.0;
        } catch (SQLException e) {
            throw new RuntimeException("Error calculando balance", e);
        }
    }
}
