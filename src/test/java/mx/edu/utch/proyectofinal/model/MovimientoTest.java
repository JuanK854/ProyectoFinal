package mx.edu.utch.proyectofinal.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovimientoTest {

    @Test
    void constructorGetters() {
        Movimiento m = new Movimiento("2025-08-08", "Ingreso", 100, "Salario");

        assertAll("Valida constructor y getters",
                () -> assertEquals("2025-08-08", m.getFecha()),
                () -> assertEquals("Ingreso", m.getTipo()),
                () -> assertEquals(100, m.getMonto(), 0.00),   //prueba error
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

        assertAll("Valida setters",
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

        assertAll("Valida el  contenido de toString",
                () -> assertTrue(texto.contains("Ingreso")),
                () -> assertTrue(texto.contains("100")),
                () -> assertTrue(texto.contains("Salario"))
        );
    }

    @Test
    void camposNulos() {
        Movimiento m = new Movimiento("2025-08-08", "Ingreso", 100.0, "Salario");

        assertAll("Valida que no falten campos",
                () -> assertNotNull(m.getFecha(), "La fecha no debe ser null"),
                () -> assertFalse(m.getFecha().isBlank(), "La fecha no debe estar vacía"),
                () -> assertNotNull(m.getTipo(), "El tipo no debe ser null"),
                () -> assertFalse(m.getTipo().isBlank(), "El tipo no debe estar vacío"),
                () -> assertTrue(m.getMonto() > 0, "El monto debe ser mayor que 0"),
                () -> assertNotNull(m.getDescripcion(), "La descripción no debe ser null"),
                () -> assertFalse(m.getDescripcion().isBlank(), "La descripción no debe estar vacía")
        );
    }
}
