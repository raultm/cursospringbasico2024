package com.example.curso2024;

import java.util.function.BooleanSupplier;

public class MathUtils {

    public int suma(int i, int j) {
        return i + j;
    }

    public void lanzarException() {
        throw new NullPointerException();
    }

    public static boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    public double calcularDescuento(double precioOriginal, float porcentajeDescuento) {
        return precioOriginal * ( 1 - (porcentajeDescuento/100));
    }

}
