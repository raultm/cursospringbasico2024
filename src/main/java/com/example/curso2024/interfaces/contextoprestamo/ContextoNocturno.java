package com.example.curso2024.interfaces.contextoprestamo;

import java.time.LocalDateTime;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(3)
public class ContextoNocturno implements ContextoPrestamo {

    @Override
    public Contexto getContexto() {
        return Contexto.HORARIO_NOCTURNO;
    }

    @Override
    public boolean aplica(LocalDateTime fechaComienzo) {
        return fechaComienzo.getHour() >= 20 && fechaComienzo.getHour() < 8;
    }

}
