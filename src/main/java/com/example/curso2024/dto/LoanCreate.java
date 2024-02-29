package com.example.curso2024.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LoanCreate {
    
    @JsonProperty("copy_id")
    private Long copyId = 24L;
    
    @JsonProperty("member_id")
    private Long memberId;
}
