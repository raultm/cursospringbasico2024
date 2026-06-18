package com.example.curso2024.interfaces.perfilsocio;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.curso2024.models.Member;

@Component
@Order(100)
public class PerfilEstandar implements PerfilSocio {

    @Override
    public Perfil getPerfil() {
        return Perfil.ESTANDAR;
    }

    @Override
    public boolean aplica(Member socio) {
        return true;
    }

}
