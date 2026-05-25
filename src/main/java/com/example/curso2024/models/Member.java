package com.example.curso2024.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "members")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    
    private static final int MAX_LOANS_FOR_MEMBER = 4;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;
    private String email;
    private String perfil;
    private boolean premium;
    private LocalDate birthDate;
    
    // TODO Cuando se haga relacion con Loans devolver el listado de prestamos
    public List<Loan> prestamos(){
        return new ArrayList<>();
    }

    public int prestamosSinDevolver() {
        return (int) prestamos().stream().filter(prestamo -> prestamo.getReturnedAt() == null).count();
    }

    public boolean haSuperadoElLimiteDePrestamos() {
        return prestamosSinDevolver() >= MAX_LOANS_FOR_MEMBER;
    }

    public boolean tienePrestamoVencido(){
        LocalDateTime today = LocalDateTime.now();
        return prestamos().stream().anyMatch(prestamo -> prestamo.getReturnedAt() == null && prestamo.getExpiredAt().isBefore(today));
    }

    public boolean isVisitante(){ return perfil.equals("visitante"); }

    public boolean isEstudiante(){ return perfil.equals("estudiante"); }
    
    public boolean isProfesor(){ return perfil.equals("profesor"); }

    public Integer getAge() {
         if (getBirthDate() == null) {
            return 0;
        }
        return Period.between(getBirthDate(), LocalDate.now()).getYears();
    }
}
