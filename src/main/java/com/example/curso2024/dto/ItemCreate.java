package com.example.curso2024.dto;

import java.util.Date;

import com.example.curso2024.models.Item;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemCreate {
    
    @NotNull
    private String title;
    private String author;
    private String type;
    private String image;
    private float duration;
    private int minimumAge;
    private Date releasedAt;

    public Item toItem(){
        return Item.builder()
            .title(title)
            .author(author)
            .type(type)
            .image(image)
            .duration(duration)
            .minimumAge(minimumAge)
            .releasedAt(releasedAt)
            .build();
    }
}
