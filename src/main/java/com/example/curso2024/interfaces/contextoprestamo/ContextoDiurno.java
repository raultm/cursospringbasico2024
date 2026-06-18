package com.example.curso2024.interfaces.contextoprestamo;

import java.time.LocalDateTime;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(100)
public class ContextoDiurno implements ContextoPrestamo {

    @Override
    public Contexto getContexto() {
        return Contexto.HORARIO_DIURNO;
    }

    @Override
    public boolean aplica(LocalDateTime fechaComienzo) {
        return true;
    }

}
