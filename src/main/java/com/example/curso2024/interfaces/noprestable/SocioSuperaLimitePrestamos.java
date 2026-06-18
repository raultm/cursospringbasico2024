package com.example.curso2024.interfaces.noprestable;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.curso2024.models.Copy;
import com.example.curso2024.models.Member;

@Component
public class SocioSuperaLimitePrestamos implements NoPrestable{
    
    public static final String MENSAJE = "El Socio ha alcanzado el límite de préstamos abiertos";

    @Override
    public boolean cumple(Member socio, Copy copia, LocalDateTime fechaComienzo) {
        return socio.haSuperadoElLimiteDePrestamos();
    }

    @Override
    public String getMensaje(Member socio, Copy copia, LocalDateTime fechaComienzo) {
        return MENSAJE;
    }
}
