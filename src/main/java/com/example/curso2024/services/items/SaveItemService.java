package com.example.curso2024.services.items;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.curso2024.dto.ItemCreate;
import com.example.curso2024.models.Item;
import com.example.curso2024.repositories.ItemsRepository;
import com.example.curso2024.services.copies.SaveCopyService;

@Service
public class SaveItemService {
    
    @Autowired ItemsRepository repository;
    @Autowired SaveCopyService saveCopyService;

    public Item execute(ItemCreate itemCreate){
        Item item = repository.save(itemCreate.toItem());
        for (int i = 0; i < itemCreate.getCopies(); i++) {
            saveCopyService.execute(item.getId());
        }
        return item;
    }

    public ResponseEntity<Item> response(ItemCreate itemCreate){
        return ResponseEntity.ok().body(execute(itemCreate));
    }
}
