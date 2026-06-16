package com.example.curso2024.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.curso2024.api.BooksApi;
import com.example.curso2024.dto.BookSummary;

public class LibrosController implements BooksApi{

    @Override
    public ResponseEntity<List<BookSummary>> booksGet() {
        return ResponseEntity.ok(null);
    }
    
}
