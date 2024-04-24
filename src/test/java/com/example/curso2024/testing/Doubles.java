package com.example.curso2024.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.example.curso2024.MathUtils;

public class Doubles {
    

    @Test 
    void probandoMock(){
        MathUtils mathUtilsMock = mock(MathUtils.class);

        when(mathUtilsMock.suma(anyInt(), anyInt())).thenReturn(7);

        assertEquals(7, mathUtilsMock.suma(1, 8));
        assertFalse(mathUtilsMock.isPrime(8));
        assertEquals(0, mathUtilsMock.calcularDescuento(10,10));
    }

    @Test
    void probandoSpy(){
        MathUtils mathUtilsSpy = spy(MathUtils.class);

        mathUtilsSpy.calcularDescuento(50,10);

        verify(mathUtilsSpy).calcularDescuento(50,10);

    }
}
