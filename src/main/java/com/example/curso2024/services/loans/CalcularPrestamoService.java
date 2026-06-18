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

/*
 * Calcula los datos de un nuevo préstamo (fecha de inicio, días de duración
 * y fecha de entrega) a partir de un socio, una copia y la fecha en la que
 * se solicita.
 *
 * Esta clase apenas tiene lógica propia: orquesta cuatro colaboradores,
 * cada uno respondiendo a una pregunta sobre el préstamo apoyándose en una
 * lista de estrategias que Spring inyecta automáticamente:
 *
 * - NoPrestableValidator: ¿hay algún motivo para bloquear el préstamo?
 *   Recorre las implementaciones de NoPrestable que apliquen y lanza una
 *   excepción con sus mensajes si hay alguna.
 * - PerfilSocioResolver: ¿qué perfil tiene el socio (visitante, estudiante,
 *   profesor, estándar)? Devuelve el de la primera implementación de
 *   PerfilSocio cuya condición se cumpla.
 * - ContextoPrestamoResolver: ¿en qué contexto se hace el préstamo
 *   (vacaciones, fin de semana, horario nocturno/diurno)? Mismo patrón
 *   sobre ContextoPrestamo.
 * - AjusteFechaEntregaResolver: ¿hay que corregir la fecha de entrega ya
 *   calculada (p. ej. si cae en sábado o domingo)? Mismo patrón sobre
 *   AjusteFechaEntrega.
 *
 * Para añadir una regla nueva no se toca esta clase: se crea una clase que
 * implemente la interfaz que corresponda (NoPrestable, PerfilSocio,
 * ContextoPrestamo o AjusteFechaEntrega) anotada con @Component, y Spring
 * la añade sola a la lista que recibe el resolver/validator
 * correspondiente.
 */
@Service
@RequiredArgsConstructor
public class CalcularPrestamoService {

    private final NoPrestableValidator noPrestableValidator;
    private final PerfilSocioResolver perfilSocioResolver;
    private final ContextoPrestamoResolver contextoPrestamoResolver;
    private final AjusteFechaEntregaResolver ajusteFechaEntregaResolver;
    private final ReglasDuracionPrestamo reglasDuracionPrestamo;

    private static final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            .appendOptional(DateTimeFormatter.ISO_LOCAL_DATE)
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
            .toFormatter();

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
