package com.example.curso2024.interfaces.contextoprestamo;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class ContextoFinDeSemana implements ContextoPrestamo {

    @Override
    public String getNombre() {
        return "findesemana";
    }

    @Override
    public boolean aplica(LocalDateTime fechaComienzo) {
        DayOfWeek dia = fechaComienzo.getDayOfWeek();
        return dia.equals(DayOfWeek.SATURDAY) || dia.equals(DayOfWeek.SUNDAY);
    }

}
