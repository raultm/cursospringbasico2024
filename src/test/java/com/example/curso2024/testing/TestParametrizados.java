package com.example.curso2024.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.example.curso2024.MathUtils;

public class TestParametrizados {
    
    @ParameterizedTest  
    //@ValueSource(ints = { 2, 7, 29, 67, 89, 90 })  
    @ValueSource(ints = { 2, 7, 29, 67, 89 })  
    void isPrime(int number) {  
      Assertions.assertTrue(MathUtils.isPrime(number));  
    }

    @ParameterizedTest
    @MethodSource("sumarDataProvider")
    void testSuma(int num1, int num2, int resultadoEsperado) {
        MathUtils calculadora = new MathUtils();
        int resultado = calculadora.suma(num1, num2);
        assertEquals(resultadoEsperado, resultado);
    }

    static Stream<Arguments> sumarDataProvider() {
        return Stream.of(
            Arguments.of(2, 3, 5),
            Arguments.of(5, 7, 12),
            Arguments.of(-3, 8, 5)
        );
    }
    
    @ParameterizedTest
    @CsvFileSource(resources = "/datos-descuento.csv", numLinesToSkip = 1)
    void testCalcularDescuento(double precioOriginal, int porcentajeDescuento, double precioEsperado) {
        MathUtils calculadora = new MathUtils();
        double precioCalculado = calculadora.calcularDescuento(precioOriginal, porcentajeDescuento);
        assertEquals(precioEsperado, precioCalculado, 0.01); // Se usa un margen de error de 0.001
    }
}
