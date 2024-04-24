package com.example.curso2024.testing;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import com.example.curso2024.MathUtils;

@DisplayName("Listado de Asserts")
public class AssertsList {
    
    MathUtils miClase = new MathUtils();

    @BeforeAll
    public static void setUpAll(){
        System.out.println("Configuración inicial de la clase");
    }

    @AfterAll
    public static void tearDownAll(){
        System.out.println("Limpieza después de todas las pruebas");
    }

    @BeforeEach
    public void setUp() {
        miClase = new MathUtils();
    }

    @AfterEach
    public void tearDown() {
        miClase = null;
    }

    @Test 
    public void testSuma() {
        // Act
        int resultado = miClase.suma(2, 3);

        // Assert
        assertEquals(5, resultado);
    }

    @Test
    public void equal() {
        assertEquals(5, miClase.suma(2, 3));
    }

    @Test
    public void notEqual() {
        assertNotEquals(6, miClase.suma(2, 3));
    }

    @Test
    public void checkTrue() {
        assertTrue(5 == miClase.suma(2, 3));
    }

    @Test
    public void checkFalse() {
        assertFalse(6 == miClase.suma(2, 3));
    }

    @Test
    public void checkNull() {
        miClase = null;
        assertNull(miClase);
    }

    @Test
    public void checkNotNull() {
        assertNotNull(miClase);
    }

    @Test
    public void checkThrows() {
        assertThrows(NullPointerException.class, () -> miClase.lanzarException());
    }

    @Test
    public void checkNotThrows() {
        assertDoesNotThrow(() -> miClase.suma(3,4));
    }
}
