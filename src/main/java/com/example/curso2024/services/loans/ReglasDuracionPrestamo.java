package com.example.curso2024.services.loans;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.curso2024.interfaces.contextoprestamo.Contexto;
import com.example.curso2024.interfaces.perfilsocio.Perfil;

@Component
public class ReglasDuracionPrestamo {

    private static final Map<Perfil, Map<Contexto, Integer>> REGLAS = Map.of(
            Perfil.PROFESOR, Map.of(
                    Contexto.VACACIONES, 60,
                    Contexto.HORARIO_DIURNO, 30,
                    Contexto.HORARIO_NOCTURNO, 15,
                    Contexto.FINDESEMANA, 30),
            Perfil.ESTUDIANTE, Map.of(
                    Contexto.VACACIONES, 0,
                    Contexto.HORARIO_DIURNO, 15,
                    Contexto.HORARIO_NOCTURNO, 7,
                    Contexto.FINDESEMANA, 0),
            Perfil.VISITANTE, Map.of(
                    Contexto.VACACIONES, 0,
                    Contexto.HORARIO_DIURNO, 7,
                    Contexto.HORARIO_NOCTURNO, 3,
                    Contexto.FINDESEMANA, 0),
            Perfil.ESTANDAR, Map.of(
                    Contexto.VACACIONES, 21,
                    Contexto.HORARIO_DIURNO, 21,
                    Contexto.HORARIO_NOCTURNO, 21,
                    Contexto.FINDESEMANA, 21));

    public int diasPrestamo(Perfil perfil, Contexto contexto) {
        return REGLAS.getOrDefault(perfil, Map.of()).getOrDefault(contexto, 0);
    }

}
