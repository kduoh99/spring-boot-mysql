package com.covenant.springbootmysql.service;

import com.covenant.springbootmysql.dto.AuthorCreationRequestDto;
import com.covenant.springbootmysql.dto.AuthorResponseDto;
import com.covenant.springbootmysql.model.Author;
import com.covenant.springbootmysql.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorResponseDto createAuthor (AuthorCreationRequestDto request) {
        Author author = Author.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
        author = authorRepository.save(author);
        return AuthorResponseDto.fromEntity(author);
    }

    public List<AuthorResponseDto> readAuthors() {
        return authorRepository.findAll().stream()
                .map(AuthorResponseDto::fromEntity)
                .collect(Collectors.toList());
    }
}
