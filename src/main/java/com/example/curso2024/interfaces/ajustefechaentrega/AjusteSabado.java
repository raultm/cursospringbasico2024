package com.example.curso2024.interfaces.ajustefechaentrega;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class AjusteSabado implements AjusteFechaEntrega {

    @Override
    public boolean aplica(LocalDateTime fechaEntrega) {
        return fechaEntrega.getDayOfWeek().equals(DayOfWeek.SATURDAY);
    }

    @Override
    public LocalDateTime ajustar(LocalDateTime fechaEntrega) {
        return fechaEntrega.plusDays(2);
    }

}
