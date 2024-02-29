package com.example.curso2024.services.items;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.curso2024.dto.ItemCreate;
import com.example.curso2024.models.Item;
import com.example.curso2024.repositories.ItemsRepository;

@Service
public class SaveItemService {
    
    @Autowired ItemsRepository repository;

    public Item execute(ItemCreate itemCreate){
        return repository.save(itemCreate.toItem());
    }

    public ResponseEntity<Item> response(ItemCreate itemCreate){
        return ResponseEntity.ok().body(execute(itemCreate));
    }
}
