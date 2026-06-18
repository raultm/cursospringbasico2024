package com.example.curso2024.services.loans;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;

import com.example.curso2024.models.Copy;
import com.example.curso2024.models.Loan;
import com.example.curso2024.models.Member;

@Service
public class CalcularPrestamoService {

    public static final String COPIA_PRESTADA = "La copia ya está prestada";
    public static final String COPIA_RESERVADA = "La copia ya está reservada";
    public static final String COPIA_NO_DISPONIBLE_POR_EDAD = "La Copia no se puede prestar a ese Socio por la edad";
    public static final String SOCIO_LIMITE_PRESTAMO = "El Socio ha alcanzado el límite de préstamos abiertos";
    public static final String SOCIO_PRESTAMO_VENCIDO = "El Socio tiene un préstamos vencido pendiente";
    public static final String FECHA_FIN_SEMANA = "El perfil de usuario no puede sacar libros en fin de semana";

    private static DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            .appendOptional(DateTimeFormatter.ISO_LOCAL_DATE)
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
            .toFormatter();

    private static Map<String, Map<String, Integer>> reglas = Map.of(
        "profesor", Map.of(
            "vacaciones", 60,
            "horario_diurno", 30,
            "horario_nocturno", 15,
            "findesemana", 30
        ),
        "estudiante", Map.of(
            "vacaciones", 0,
            "horario_diurno", 15,
            "horario_nocturno", 7,
            "findesemana", 0
        ),
        "visitante", Map.of(
            "vacaciones", 0,
            "horario_diurno", 7,
            "horario_nocturno", 3,
            "findesemana", 0
        ),
        "", Map.of(
            "vacaciones", 21,
            "horario_diurno", 21,
            "horario_nocturno", 21,
            "findesemana", 21
        )
    );

    // TODO Logica de calcular el prestamo a guardar
    public Loan execute(Member socio, Copy copia, String fecha) {
        int diasPrestamo = 21;

        LocalDateTime fechaComienzo = LocalDateTime.parse(fecha, formatter);

        lanzaExcepcionSiPrestamoNoEsPosible(socio, copia, fechaComienzo);

        String perfil = resolvePerfil(socio);
        String contexto = resolverContexto(fechaComienzo);
        
        diasPrestamo = reglas.getOrDefault(perfil,Map.of()).getOrDefault(contexto, 0);

        LocalDateTime fechaEntrega = fechaComienzo.plusDays(diasPrestamo);
        fechaEntrega = modificacionesALaFechaDeEntrega(fechaEntrega);

        return Loan.builder()
                .member(socio)
                .copy(copia)
                .startedAt(fechaComienzo)
                .expiredAt(fechaEntrega)
                .build();

    }

    private LocalDateTime modificacionesALaFechaDeEntrega(LocalDateTime fechaEntrega) {
        LocalDateTime fechaEntregaFinal = fechaEntrega;
        
        if(fechaEntregaFinal.getDayOfWeek().equals(DayOfWeek.SATURDAY)){
            fechaEntregaFinal.plusDays(2);
        }

        if(fechaEntregaFinal.getDayOfWeek().equals(DayOfWeek.SUNDAY)){
            fechaEntregaFinal.plusDays(1);
        }

        return fechaEntregaFinal;
    }

    private String resolvePerfil(Member socio){
        if(socio.isVisitante()) return "visitante";
        if(socio.isEstudiante()) return "estudiante";
        if(socio.isProfesor()) return "profesor";
        return "";
    }

    private String resolverContexto(LocalDateTime fechaComienzo) {
        if(fechaComienzo.getMonth().equals(Month.JULY) || fechaComienzo.getMonth().equals(Month.AUGUST)){
            return "vacaciones";
        }

        if(fechaComienzo.getDayOfWeek().equals(DayOfWeek.SATURDAY) || fechaComienzo.getDayOfWeek().equals(DayOfWeek.SUNDAY)){
            return "findesemana";
        }

        if(fechaComienzo.getHour() >= 20 && fechaComienzo.getHour()<8){
            return "horario_nocturno";
        }

        return "horario_diurno";
    }

    private void lanzaExcepcionSiPrestamoNoEsPosible(Member socio, Copy copia, LocalDateTime fechaComienzo) {
        DayOfWeek dia = fechaComienzo.getDayOfWeek();
        boolean esFinDeSemana = dia.equals(DayOfWeek.SATURDAY) || dia.equals(DayOfWeek.SUNDAY);
        boolean esFinDeSemanaYNoProfesor = esFinDeSemana && !socio.isProfesor();
        
        if (esFinDeSemanaYNoProfesor) {
            throw new RuntimeException(FECHA_FIN_SEMANA);
        }

        if (socio.tienePrestamoVencido()) {
            throw new RuntimeException(SOCIO_PRESTAMO_VENCIDO);
        }

        if (socio.haSuperadoElLimiteDePrestamos()) {
            throw new RuntimeException(SOCIO_LIMITE_PRESTAMO);
        }

        if (copia.estaEnPrestamo()) {

            throw new RuntimeException(COPIA_PRESTADA);
        }
    }

    // if (socio.isVisitante()) {
    //         diasPrestamo = 7;
    //     } else if (socio.isEstudiante()) {
    //         diasPrestamo = 15;
    //     } else if (socio.isProfesor()) {
    //         diasPrestamo = 30;
    //     }
}
