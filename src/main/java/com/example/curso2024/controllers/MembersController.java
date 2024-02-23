package com.example.curso2024.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.curso2024.models.Member;
import com.example.curso2024.repositories.MemberRepository;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("members")
public class MembersController {
    
    @Autowired MemberRepository repository;


    @GetMapping @Operation(operationId = "listMembers",summary = "Listar todos los miembros", tags = { "members" })
    public List<Member> findAll() {
        return repository.findAll();
    }
    
    @PostMapping @Operation(operationId = "createMember",summary = "Crear nuevo Miembro", tags = { "members" })
    public Member save(Member member) { 
        return repository.save(member); 
    }

    @GetMapping("{memberId}") @Operation(operationId = "listMembers",summary = "Listar todos los miembros", tags = { "members" })
    public Member findByid(@PathVariable("memberId")Long memberId) {
        return repository.findById(memberId).orElseThrow();
    }

}
