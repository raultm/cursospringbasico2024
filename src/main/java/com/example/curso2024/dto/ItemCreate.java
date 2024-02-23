package com.example.curso2024.dto;

import com.example.curso2024.models.Item;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemCreate {
    
    @NotNull
    private String title;
    private String type;

    public Item toItem(){
        return Item.builder().title(title).type(type).build();
    }
}
