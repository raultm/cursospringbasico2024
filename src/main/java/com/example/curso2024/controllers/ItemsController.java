package com.example.curso2024.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.curso2024.dto.ItemCreate;
import com.example.curso2024.models.Copy;
import com.example.curso2024.models.Item;
import com.example.curso2024.repositories.CopiesRepository;
import com.example.curso2024.repositories.ItemsRepository;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("items")
@Validated
public class ItemsController {
    
    @Autowired ItemsRepository repository;
    @Autowired CopiesRepository copiesRepository;

    
    @PostMapping @Operation(operationId = "crearItem",summary = "Crear nuevo Item", tags = { "items" })
    public Item save(@RequestBody @Valid ItemCreate item) { 
        return repository.save(item.toItem()); 
    }
    
    @GetMapping  @Operation(operationId = "listarItems",summary = "Listar todos los Items", tags = { "items" })
    public List<Item> get(){ 
        return repository.findAll(); 
    }
    
    @GetMapping("{peticionId}") @Operation(operationId = "encontrarItem",summary = "Obtener un Item por su Id", tags = { "items" })
    public Item findBy(@PathVariable("peticionId") Long peticionId) { 
            return repository.findById(peticionId).orElseThrow(); 
    }

    @PostMapping("{peticionId}/copies")
    @Operation(operationId = "crearCopia",summary = "Crear Copia de Item Id", tags = { "items" })
    public Copy crearCopia(@PathVariable("peticionId") Long peticionId) {
        return copiesRepository.save(Copy.builder()
            .item(repository.findById(peticionId).orElseThrow())
            .acquiredAt(new Date())
            .reservedBy("")
            .build()
        );
    }

    @GetMapping("{peticionId}/copies")
    @Operation(operationId = "listarCopiasDeItem",summary = "Listar las copias de un Item", tags = { "items" })
    public List<Copy> listarCopias(@PathVariable("peticionId") Long peticionId) {
        return repository.findById(peticionId).orElseThrow().getCopies();
    }
    
    
}
