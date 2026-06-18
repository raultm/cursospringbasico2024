package com.example.curso2024.interfaces.noprestable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.curso2024.models.Copy;
import com.example.curso2024.models.Member;

@Component
public class NoPrestableValidator {

    private final List<NoPrestable> reglas;

    public NoPrestableValidator(List<NoPrestable> reglas) {
        this.reglas = reglas;
    }

    public void validar(Member socio, Copy copia, LocalDateTime fechaComienzo) {
        List<String> mensajes = new ArrayList<>();

        reglas.stream()
                .filter(noPrestable -> noPrestable.cumple(socio, copia, fechaComienzo))
                .forEach(noPrestable -> mensajes.add(noPrestable.getMensaje(socio, copia, fechaComienzo)));

        if (!mensajes.isEmpty()) {
            throw new RuntimeException(String.join(", ", mensajes));
        }
    }

}
