package com.example.curso2024.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.curso2024.models.Member;
import com.example.curso2024.repositories.MemberRepository;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Slf4j
@RestController
@RequestMapping("members")
public class MembersController {
    
    @Autowired MemberRepository repository;


    @GetMapping @Operation(operationId = "listMembers",summary = "Listar todos los Socios", tags = { "members" })
    public List<Member> findAll() {
        log.trace("Este es un mensaje de trace");
        log.debug("Este es un mensaje de debug");
        log.info("Este es un mensaje de info");
        log.warn("Este es un mensaje de warn");
        log.error("Este es un mensaje de error");
        return repository.findAll();
    }
    
    @PostMapping @Operation(operationId = "createMember",summary = "Crear nuevo Socio", tags = { "members" })
    public Member save(Member member) { 
        return repository.save(member); 
    }

    @DeleteMapping("{memberId}") @Operation(operationId = "deleteMember",summary = "Borra un socio", tags = { "members" })
    public void deleteById(@PathVariable("memberId")Long memberId) {
        repository.deleteById(memberId);
    }

    // TODO Crear endpoint para obtener datos de un socio por su id
    @GetMapping("{memberId}") @Operation(operationId = "findMember",summary = "Obtener Detalles de un Socio", tags = { "members" })
    public Member findById(@PathVariable("memberId")Long memberId) {
        return repository.findById(memberId).orElseThrow();
    }

}
