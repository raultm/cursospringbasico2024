package com.example.curso2024.services.loans;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

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

    // TODO Logica de calcular el prestamo a guardar
    public Loan execute(Member socio, Copy copia, String fecha) {
        int diasPrestamo = 21;

        LocalDateTime fechaComienzo = LocalDateTime.parse(fecha, formatter);

        DayOfWeek dia = fechaComienzo.getDayOfWeek();
        boolean esFinDeSemana = dia.equals(DayOfWeek.SATURDAY) || dia.equals(DayOfWeek.SUNDAY);

        //if(esFinDeSemana && socio.isProfesor()){
            if(!socio.tienePrestamoVencido()){
                if (!socio.haSuperadoElLimiteDePrestamos()) {
                    if (!copia.estaEnPrestamo()) {

                        if (socio.isVisitante()) {
                            diasPrestamo = 7;
                        } else if (socio.isEstudiante()) {
                            diasPrestamo = 15;
                        } else if (socio.isProfesor()) {
                            diasPrestamo = 30;
                        }

                        return Loan.builder()
                                .member(socio)
                                .copy(copia)
                                .startedAt(fechaComienzo)
                                .expiredAt(fechaComienzo.plusDays(diasPrestamo))
                                .build();

                    } else {
                        throw new RuntimeException(COPIA_PRESTADA);
                    }
                } else {
                    throw new RuntimeException(SOCIO_LIMITE_PRESTAMO);
                }
            }else{
                throw new RuntimeException(SOCIO_PRESTAMO_VENCIDO);
            }
        //} else{
        //     throw new RuntimeException(FECHA_FIN_SEMANA);
        // }

    }

    // socio.isEstudiante() socio.getPerfil().equalsIgnoreCase("estudiante")

    // throw new RuntimeException(COPIA_PRESTADA);

}
