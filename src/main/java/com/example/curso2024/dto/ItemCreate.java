package com.example.curso2024.dto;

import java.time.LocalDate;
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
    private int duration;
    private int minimumAge;
    private LocalDate releasedAt;
    @Builder.Default
    private int copies = 1;

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
