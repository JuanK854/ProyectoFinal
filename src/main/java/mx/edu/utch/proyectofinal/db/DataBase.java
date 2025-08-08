package mx.edu.utch.proyectofinal.db;

import mx.edu.utch.proyectofinal.model.Movimiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {

    private static final String URL = "jdbc:sqlite:finanzas.db";

    private static final String CREATE_TABLE = """
        CREATE TABLE IF NOT EXISTS movimientos (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            fecha TEXT NOT NULL,
            tipo  TEXT NOT NULL CHECK(tipo IN ('Ingreso','Gasto')),
            monto REAL NOT NULL CHECK(monto > 0),
            descripcion TEXT NOT NULL
        );
        """;

    private static final String SQL_INSERT = """
        INSERT INTO movimientos(fecha, tipo, monto, descripcion)
        VALUES(?, ?, ?, ?)
        """;

    private static final String SQL_SELECT_ALL = """
        SELECT id, fecha, tipo, monto, descripcion
        FROM movimientos
        ORDER BY id DESC
        """;

    private static final String SQL_BALANCE = """
        SELECT COALESCE(SUM(
            CASE WHEN tipo='Ingreso' THEN monto
                 WHEN tipo='Gasto'   THEN -monto
                 ELSE 0 END
        ), 0) AS balance
        FROM movimientos
        """;

    private static final String SQL_DELETE = "DELETE FROM movimientos WHERE id = ?";

    private DataBase() { }

    public static void init() {
        try (Connection connection  = getConnection();
             Statement statement        = connection.createStatement()) {
            statement.execute(CREATE_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException("Error inicializando la base de datos");
        }
    }

    public static void insertar(final Movimiento m) {
        try (Connection connection                                  = getConnection();
             PreparedStatement preparedStatement   = connection.prepareStatement(SQL_INSERT)) {
            preparedStatement.setString(1, m.getFecha());
            preparedStatement.setString(2, m.getTipo());
            preparedStatement.setDouble(3, m.getMonto());
            preparedStatement.setString(4, m.getDescripcion());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error insertando movimiento");
        }
    }

    public static List<Movimiento> listar() {
        final List<Movimiento> resultados = new ArrayList<>();
        try (Connection connection  = getConnection();
             Statement statement        = connection.createStatement();
             ResultSet resultSet             = statement.executeQuery(SQL_SELECT_ALL)) {
            while (resultSet.next()) {
                resultados.add(new Movimiento(
                        resultSet.getInt("id"),
                        resultSet.getString("fecha"),
                        resultSet.getString("tipo"),
                        resultSet.getDouble("monto"),
                        resultSet.getString("descripcion")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error listando movimientos");
        }
        return resultados;
    }

    public static double balance() {
        try (Connection connection   = getConnection();
             Statement statement        = connection.createStatement();
             ResultSet resultSet             = statement.executeQuery(SQL_BALANCE)) {
            return resultSet.next() ? resultSet.getDouble("balance") : 0.0;
        } catch (SQLException e) {
            throw new RuntimeException("Error calculando balance");
        }
    }

    public static void borrar(final int id) {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_DELETE)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error borrando movimiento");
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
