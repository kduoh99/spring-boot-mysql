package com.covenant.springbootmysql.dto;

import com.covenant.springbootmysql.model.Author;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthorResponseDto {
    private final Long id;
    private final String firstName;
    private final String lastName;


    public static AuthorResponseDto fromEntity(Author author) {
        return new AuthorResponseDto(author.getId(), author.getFirstName(), author.getLastName());
    }
}
