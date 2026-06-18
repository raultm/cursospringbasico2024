package com.example.curso2024.services.loans;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.curso2024.models.Copy;
import com.example.curso2024.models.Loan;
import com.example.curso2024.models.Member;
import com.example.curso2024.repositories.LoansRepository;

@Service
public class SavePrestamoService {
    
    @Autowired
    LoansRepository loansRepository;
    @Autowired
    CalcularPrestamoService calcularPrestamoService;


    // TODO Complejidad a la hora de definir la fecha de devolucion, usar servicio
    public Loan execute(Member socio, Copy libro, LocalDateTime localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return loansRepository.save(calcularPrestamoService.execute(socio, libro, localDate.format(formatter)));
    }
}
