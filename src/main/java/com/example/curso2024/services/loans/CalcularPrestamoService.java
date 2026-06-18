package com.example.curso2024.services.loans;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

import org.springframework.stereotype.Service;

import com.example.curso2024.interfaces.ajustefechaentrega.AjusteFechaEntregaResolver;
import com.example.curso2024.interfaces.contextoprestamo.Contexto;
import com.example.curso2024.interfaces.contextoprestamo.ContextoPrestamoResolver;
import com.example.curso2024.interfaces.noprestable.CopiaEnPrestamo;
import com.example.curso2024.interfaces.noprestable.NoPrestableValidator;
import com.example.curso2024.interfaces.noprestable.SocioNoProfesorEnFinDeSemana;
import com.example.curso2024.interfaces.noprestable.SocioTienePrestamoVencido;
import com.example.curso2024.interfaces.perfilsocio.Perfil;
import com.example.curso2024.interfaces.perfilsocio.PerfilSocioResolver;
import com.example.curso2024.models.Copy;
import com.example.curso2024.models.Loan;
import com.example.curso2024.models.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CalcularPrestamoService {

    private final NoPrestableValidator noPrestableValidator;
    private final PerfilSocioResolver perfilSocioResolver;
    private final ContextoPrestamoResolver contextoPrestamoResolver;
    private final AjusteFechaEntregaResolver ajusteFechaEntregaResolver;
    private final ReglasDuracionPrestamo reglasDuracionPrestamo;


    private static DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            .appendOptional(DateTimeFormatter.ISO_LOCAL_DATE)
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
            .toFormatter();

    // TODO Logica de calcular el prestamo a guardar
    public Loan execute(Member socio, Copy copia, String fecha) {
        LocalDateTime fechaComienzo = LocalDateTime.parse(fecha, formatter);

        noPrestableValidator.validar(socio, copia, fechaComienzo);

        Perfil perfil = perfilSocioResolver.resolver(socio);
        Contexto contexto = contextoPrestamoResolver.resolver(fechaComienzo);

        int diasPrestamo = reglasDuracionPrestamo.diasPrestamo(perfil, contexto);

        LocalDateTime fechaEntrega = fechaComienzo.plusDays(diasPrestamo);
        fechaEntrega = ajusteFechaEntregaResolver.ajustar(fechaEntrega);

        return Loan.builder()
                .member(socio)
                .copy(copia)
                .startedAt(fechaComienzo)
                .expiredAt(fechaEntrega)
                .build();

    }

}
