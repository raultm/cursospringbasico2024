package com.example.curso2024.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.curso2024.models.Copy;

@Repository
public interface CopiesRepository extends JpaRepository<Copy, Long>{
    
}
