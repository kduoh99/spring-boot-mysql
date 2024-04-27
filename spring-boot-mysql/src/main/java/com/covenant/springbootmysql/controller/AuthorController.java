package com.covenant.springbootmysql.controller;

import com.covenant.springbootmysql.dto.AuthorResponseDto;
import com.covenant.springbootmysql.dto.AuthorCreationRequestDto;
import com.covenant.springbootmysql.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/authors")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<AuthorResponseDto>> readAuthors() {
        List<AuthorResponseDto> responseDtos = authorService.readAuthors();
        return ResponseEntity.ok(responseDtos);
    }

    @PostMapping
    public ResponseEntity<AuthorResponseDto> createAuthor(@RequestBody AuthorCreationRequestDto request) {
        AuthorResponseDto responseDto = authorService.createAuthor(request);
        return ResponseEntity.ok(responseDto);
    }
}
