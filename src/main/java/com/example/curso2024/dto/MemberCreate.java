package com.example.curso2024.dto;

import com.example.curso2024.models.Member;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberCreate {
    
    private String username;
    private String email;
    
    
    public Member toMember() {
        return Member.builder().username(username).email(email).build();
    }
}
