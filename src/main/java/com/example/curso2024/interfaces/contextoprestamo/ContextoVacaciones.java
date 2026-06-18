package com.example.curso2024.interfaces.contextoprestamo;

import java.time.LocalDateTime;
import java.time.Month;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class ContextoVacaciones implements ContextoPrestamo {

    @Override
    public Contexto getContexto() {
        return Contexto.VACACIONES;
    }

    @Override
    public boolean aplica(LocalDateTime fechaComienzo) {
        return fechaComienzo.getMonth().equals(Month.JULY) || fechaComienzo.getMonth().equals(Month.AUGUST);
    }

}
