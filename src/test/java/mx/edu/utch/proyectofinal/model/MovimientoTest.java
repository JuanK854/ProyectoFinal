package mx.edu.utch.proyectofinal.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovimientoTest {

    @Test
    void constructorGetters() {
        Movimiento m = new Movimiento("2025-08-08", "Ingreso", 100.0, "Salario");

        assertAll("Validar constructor y getters",
                () -> assertEquals("2025-08-08", m.getFecha()),
                () -> assertEquals("Ingreso", m.getTipo()),
                () -> assertEquals(10.0, m.getMonto(), 0.00),   //prueba error
                () -> assertEquals("Salario", m.getDescripcion())
        );
    }

    @Test
    void setters() {
        Movimiento m = new Movimiento();
        m.setFecha("2025-08-09");
        m.setTipo("Gasto");
        m.setMonto(100);
        m.setDescripcion("Comida");

        assertAll("Validar setters",
                () -> assertEquals("2025-08-09", m.getFecha()),
                () -> assertEquals("Gasto", m.getTipo()),
                () -> assertEquals(100, m.getMonto()),
                () -> assertEquals("Comida", m.getDescripcion())
        );
    }

    @Test
    void toStringIncluyeDatos() {
        Movimiento m = new Movimiento("2025-08-08", "Ingreso", 100, "Salario");
        String texto = m.toString();

        assertAll("Validar contenido de toString",
                () -> assertTrue(texto.contains("Ingreso")),
                () -> assertTrue(texto.contains("100")),
                () -> assertTrue(texto.contains("Salario"))
        );
    }
}
