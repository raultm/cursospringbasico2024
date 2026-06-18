package com.example.curso2024.interfaces.contextoprestamo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ContextoPrestamoResolver {

    private final List<ContextoPrestamo> contextos;

    public ContextoPrestamoResolver(List<ContextoPrestamo> contextos) {
        this.contextos = contextos;
    }

    public String resolver(LocalDateTime fechaComienzo) {
        return contextos.stream()
                .filter(contexto -> contexto.aplica(fechaComienzo))
                .findFirst()
                .map(ContextoPrestamo::getNombre)
                .orElse("horario_diurno");
    }

}
