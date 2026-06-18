package com.example.curso2024.interfaces.perfilsocio;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.curso2024.models.Member;

@Component
@Order(3)
public class PerfilProfesor implements PerfilSocio {

    @Override
    public String getNombre() {
        return "profesor";
    }

    @Override
    public boolean aplica(Member socio) {
        return socio.isProfesor();
    }

}
