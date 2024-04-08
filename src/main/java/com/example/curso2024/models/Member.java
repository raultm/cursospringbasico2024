package com.example.curso2024.models;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "members")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    
    private static final int MAX_LOANS_FOR_MEMBER = 4;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;
    private String email;
    private boolean premium;
    private LocalDate birthDate;
    @OneToMany(mappedBy = "member")
    private List<Loan> loans;
    
    public boolean isLoanLimitReached() {
        return getLoans().size() > MAX_LOANS_FOR_MEMBER;
    }


    public int getAge() {
        return Period.between(getBirthDate(), LocalDate.now()).getYears();
    }
}
