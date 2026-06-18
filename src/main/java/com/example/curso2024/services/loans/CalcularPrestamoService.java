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

    public static final String COPIA_PRESTADA = CopiaEnPrestamo.MENSAJE;
    public static final String COPIA_RESERVADA = "La copia ya está reservada";
    public static final String COPIA_NO_DISPONIBLE_POR_EDAD = "La Copia no se puede prestar a ese Socio por la edad";
    public static final String SOCIO_LIMITE_PRESTAMO = "El Socio ha alcanzado el límite de préstamos abiertos";
    public static final String SOCIO_PRESTAMO_VENCIDO = SocioTienePrestamoVencido.MENSAJE;
    public static final String FECHA_FIN_SEMANA = SocioNoProfesorEnFinDeSemana.MENSAJE;

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
