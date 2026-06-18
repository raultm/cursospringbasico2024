package com.example.curso2024.interfaces.ajustefechaentrega;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class AjusteDomingo implements AjusteFechaEntrega {

    @Override
    public boolean aplica(LocalDateTime fechaEntrega) {
        return fechaEntrega.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    }

    @Override
    public LocalDateTime ajustar(LocalDateTime fechaEntrega) {
        return fechaEntrega.plusDays(1);
    }

}
