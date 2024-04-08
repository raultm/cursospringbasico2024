package com.example.curso2024.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.curso2024.dto.LoanCreate;
import com.example.curso2024.models.Loan;
import com.example.curso2024.repositories.CopiesRepository;
import com.example.curso2024.repositories.LoansRepository;
import com.example.curso2024.repositories.MemberRepository;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("loans")
public class LoansController {
    
    @Autowired LoansRepository repository;
    @Autowired CopiesRepository copiesRepository;
    @Autowired MemberRepository memberRepository;

    @GetMapping @Operation(operationId = "listarPrestamos",summary = "Listado de Préstamos", tags = { "loans" })
    public List<Loan> listarPrestamos(){
        return repository.findAll();
    }

    @PostMapping @Operation(operationId = "prestarCopia",summary = "Crear préstamo", tags = { "loans" })
    public Loan prestarCopia(@RequestBody LoanCreate loanCreate) { 
        return repository.save(
            Loan.builder()
                .copy(copiesRepository.findById(loanCreate.getCopyId()).get())
                .member(memberRepository.findById(loanCreate.getMemberId()).get())
                .startedAt(LocalDate.now())
                .expiredAt(LocalDate.now().plusDays(21))
                .build()
        );
    }

    @DeleteMapping("/{id}") @Operation(operationId = "terminarPrestamo",summary = "Terminar préstamo", tags = { "loans" })
    public ResponseEntity<String> terminarPrestamo(@PathVariable Long id) {
        Optional<Loan> optionalLoan = repository.findById(id);
        if (optionalLoan.isPresent()) {
            Loan loan = optionalLoan.get();
            // Verificar si el préstamo ya ha sido devuelto
            if (loan.getReturnedAt() != null) {
                return ResponseEntity.badRequest().body("El préstamo ya ha sido devuelto anteriormente.");
            }
            // Actualizar la fecha de devolución y guardar el préstamo
            loan.setReturnedAt(LocalDate.now());
            repository.save(loan);
            return ResponseEntity.ok("Préstamo terminado exitosamente.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    
}
