package com.covenant.springbootmysql.controller;

import com.covenant.springbootmysql.model.Author;
import com.covenant.springbootmysql.model.request.AuthorCreationRequest;
import com.covenant.springbootmysql.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/authors")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthorController {

    private final LibraryService libraryService;

    @GetMapping
    public ResponseEntity<List<Author>> readAuthors() {
        return ResponseEntity.ok(libraryService.readAuthors());
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody AuthorCreationRequest request) {
        return ResponseEntity.ok(libraryService.createAuthor(request));
    }


}
