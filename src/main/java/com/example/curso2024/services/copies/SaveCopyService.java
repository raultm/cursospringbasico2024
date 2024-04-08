package com.example.curso2024.services.copies;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.curso2024.models.Copy;
import com.example.curso2024.repositories.CopiesRepository;
import com.example.curso2024.repositories.ItemsRepository;

@Service
public class SaveCopyService {
 
    @Autowired CopiesRepository copiesRepository;
    @Autowired ItemsRepository itemsRepository;
    
    public Copy execute(Long itemId){
        return copiesRepository.save(Copy.builder()
            .item(itemsRepository.findById(itemId).orElseThrow())
            .acquiredAt(LocalDate.now())
            .reservedBy("")
            .build()
        );
    }

    public ResponseEntity<Copy> response(Long itemId){
        return ResponseEntity.ok().body(execute(itemId));
    }
}
