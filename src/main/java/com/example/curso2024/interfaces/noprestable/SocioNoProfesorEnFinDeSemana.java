package com.example.curso2024.interfaces.noprestable;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.curso2024.models.Copy;
import com.example.curso2024.models.Member;

@Component
public class SocioNoProfesorEnFinDeSemana implements NoPrestable {

    public static final String MENSAJE = "El perfil de usuario no puede sacar libros en fin de semana";

    @Override
    public boolean cumple(Member socio, Copy copia, LocalDateTime fechaComienzo) {
        DayOfWeek dia = fechaComienzo.getDayOfWeek();
        boolean esFinDeSemana = dia.equals(DayOfWeek.SATURDAY) || dia.equals(DayOfWeek.SUNDAY);
        return esFinDeSemana && !socio.isProfesor();
    }

    @Override
    public String getMensaje(Member socio, Copy copia, LocalDateTime fechaComienzo) {
        return MENSAJE;
    }

}
