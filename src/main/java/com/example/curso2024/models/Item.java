package com.example.curso2024.models;


import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    
    public static final String LIBRO = "libro";
    public static final String DISCO = "disco";
    public static final String JUEGO = "juego";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String type;
    private String title;
    private String author;
    private String image;
    private int duration;
    @Column(name="minimum_age")
    private int minimumAge;
    @Column(name="released_at")
    @Temporal(TemporalType.DATE)
    private LocalDate releasedAt;

    @OneToMany(mappedBy = "item", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Copy> copies;
}
