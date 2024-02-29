package com.example.curso2024.services.members;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.curso2024.dto.MemberCreate;
import com.example.curso2024.models.Member;
import com.example.curso2024.repositories.MemberRepository;

@Service
public class SaveMemberService {
    
    @Autowired MemberRepository repository;

    public Member execute(MemberCreate memberCreate){
        return repository.save(memberCreate.toMember());
    }

    public ResponseEntity<Member> response(MemberCreate memberCreate){
        return ResponseEntity.ok().body(execute(memberCreate));
    }
}
