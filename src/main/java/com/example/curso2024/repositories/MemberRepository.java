package com.example.curso2024.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.curso2024.models.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
    
}
