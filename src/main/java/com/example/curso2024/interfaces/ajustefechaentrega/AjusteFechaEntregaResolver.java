package com.example.curso2024.interfaces.ajustefechaentrega;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class AjusteFechaEntregaResolver {

    private final List<AjusteFechaEntrega> ajustes;

    public AjusteFechaEntregaResolver(List<AjusteFechaEntrega> ajustes) {
        this.ajustes = ajustes;
    }

    public LocalDateTime ajustar(LocalDateTime fechaEntrega) {
        return ajustes.stream()
                .filter(ajuste -> ajuste.aplica(fechaEntrega))
                .findFirst()
                .map(ajuste -> ajuste.ajustar(fechaEntrega))
                .orElse(fechaEntrega);
    }

}
