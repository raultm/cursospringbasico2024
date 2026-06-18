package com.example.curso2024.interfaces.noprestable;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.curso2024.models.Copy;
import com.example.curso2024.models.Member;

@Component
public class FinDeSemanaYNoProfesor implements NoPrestable {

    public static final String MENSAJE = "El perfil de usuario no puede sacar libros en fin de semana";

    @Override
    public String getMensaje(Member socio, Copy copia, LocalDateTime fechaComienzo) {
        return MENSAJE;
    }

    @Override
    public boolean cumple(Member socio, Copy copia, LocalDateTime fechaComienzo) {
        return !socio.isProfesor() && (fechaComienzo.getDayOfWeek().getValue() == 6 || fechaComienzo.getDayOfWeek().getValue() == 7);
    }
    
}
