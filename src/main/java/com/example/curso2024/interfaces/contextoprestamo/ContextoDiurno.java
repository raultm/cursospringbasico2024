package com.example.curso2024.interfaces.contextoprestamo;

import java.time.LocalDateTime;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(100)
public class ContextoDiurno implements ContextoPrestamo {

    @Override
    public String getNombre() {
        return "horario_diurno";
    }

    @Override
    public boolean aplica(LocalDateTime fechaComienzo) {
        return true;
    }

}
