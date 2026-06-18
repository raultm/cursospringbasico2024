package com.example.curso2024.interfaces.perfilsocio;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.curso2024.models.Member;

@Component
@Order(1)
public class PerfilVisitante implements PerfilSocio {

    @Override
    public String getNombre() {
        return "visitante";
    }

    @Override
    public boolean aplica(Member socio) {
        return socio.isVisitante();
    }

}
