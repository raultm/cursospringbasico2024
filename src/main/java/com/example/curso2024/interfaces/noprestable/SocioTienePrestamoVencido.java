package com.example.curso2024.interfaces.noprestable;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.curso2024.models.Copy;
import com.example.curso2024.models.Member;

@Component
public class SocioTienePrestamoVencido implements NoPrestable {
    public static final String MENSAJE = "El Socio tiene un préstamos vencido pendiente";

    @Override
    public boolean cumple(Member socio, Copy copia, LocalDateTime fechaComienzo) {
        return socio.tienePrestamoVencido();
    }

    @Override
    public String getMensaje(Member socio, Copy copia, LocalDateTime fechaComienzo) {
        return MENSAJE;
    }
}
