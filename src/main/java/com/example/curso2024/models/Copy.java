package com.example.curso2024.models;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="copies")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Copy {
    
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    @JsonIgnore
    private Item item;

    @Column(name="acquired_at")
    @Temporal(TemporalType.DATE)
    private LocalDate acquiredAt;

    @Column(name="reserved_by")
    private String reservedBy;

    @OneToMany(mappedBy = "copy")
    private List<Loan> loans;

    public boolean isNew() {
        return acquiredAt.isAfter(LocalDate.now().minusDays(15));
    }

    public boolean isBorrowed() {
        return false;
    }

    public int getRecommendedAge() {
        return getItem().getMinimumAge();
    }

    public int getDuration(){
        return getItem().getDuration();
    }

    public String getType(){
        return getItem().getType();
    }
}
