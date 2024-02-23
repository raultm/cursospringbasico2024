package com.example.curso2024.dto;

import com.example.curso2024.models.Copy;
import lombok.Data;

@Data
public class CopyCreate {
    
    
    public Copy toCopy(){
        return Copy.builder().build();
    }
}
