package com.example.curso2024.interfaces.perfilsocio;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.curso2024.models.Member;

@Component
public class PerfilSocioResolver {

    private final List<PerfilSocio> perfiles;

    public PerfilSocioResolver(List<PerfilSocio> perfiles) {
        this.perfiles = perfiles;
    }

    public String resolver(Member socio) {
        return perfiles.stream()
                .filter(perfil -> perfil.aplica(socio))
                .findFirst()
                .map(PerfilSocio::getNombre)
                .orElse("");
    }

}
