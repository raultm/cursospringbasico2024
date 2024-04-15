package com.example.curso2024.services.loans;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.example.curso2024.models.Copy;
import com.example.curso2024.models.Item;
import com.example.curso2024.models.Loan;
import com.example.curso2024.models.Member;

@Service
public class CalculateNewLoanService {

    public Loan execute(Member member, Copy copy, String dateString) {
        int loanDays = 21;
        if(!copy.isBorrowed()){
            if(!member.isLoanLimitReached()){
                if(member.getAge() >= copy.getRecommendedAge()){
                    if(    copy.getReservedBy() == null 
                        || copy.getReservedBy().equals("")
                        || copy.getReservedBy().equals(member.getUsername())){
                        if(copy.isNew()){
                            loanDays = 14;
                            if(copy.getType() != null && copy.getType().equals(Item.LIBRO) && copy.getDuration()>900){
                                loanDays+= 7;
                            }
                        }else{
                            if(copy.getType() != null && copy.getType().equals(Item.DISCO)){
                                loanDays = 7;
                                if(member.isPremium()){
                                    loanDays = 14;
                                }
                            }else{
                                if(member.isPremium()){
                                    loanDays = 35;
                                }
                            }
                        }
                        return Loan.builder()
                                    .member(member)
                                    .copy(copy)
                                    .startedAt(LocalDate.parse(dateString))
                                    .expiredAt(LocalDate.parse(dateString).plusDays(loanDays))
                                    .build();
                    }else{
                        throw new RuntimeException("La copia ya está reservada");
                    }
                }else{
                    throw new RuntimeException("La Copia no se puede prestar a ese Socio por la edad");
                }
            }else{
                throw new RuntimeException("El Socio ha alcanzado el límite de préstamos abiertos");
            }
        }else{
            throw new RuntimeException("La copia ya está prestada");
        }
    }
    
}
