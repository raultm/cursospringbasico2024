package com.example.curso2024.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.curso2024.models.Item;

//@RepositoryRestResource(exported=true)
@Repository
public interface ItemsRepository extends JpaRepository<Item, Long>{
    
}
