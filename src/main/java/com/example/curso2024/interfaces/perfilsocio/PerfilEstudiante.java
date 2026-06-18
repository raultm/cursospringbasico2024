package com.example.curso2024.interfaces.perfilsocio;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.curso2024.models.Member;

@Component
@Order(2)
public class PerfilEstudiante implements PerfilSocio {

    @Override
    public Perfil getPerfil() {
        return Perfil.ESTUDIANTE;
    }

    @Override
    public boolean aplica(Member socio) {
        return socio.isEstudiante();
    }

}
