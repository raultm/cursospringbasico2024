package com.example.curso2024.interfaces.noprestable;

import java.time.LocalDateTime;

import com.example.curso2024.models.Copy;
import com.example.curso2024.models.Member;

public interface NoPrestable {
    
    public String getMensaje(Member socio, Copy copia, LocalDateTime fechaComienzo);

    public boolean cumple(Member socio, Copy copia, LocalDateTime fechaComienzo);

}
