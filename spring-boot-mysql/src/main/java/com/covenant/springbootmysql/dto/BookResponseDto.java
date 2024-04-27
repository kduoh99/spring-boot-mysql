package com.covenant.springbootmysql.dto;

import com.covenant.springbootmysql.model.Book;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BookResponseDto {
    private final Long id;
    private final String name;
    private final String isbn;
    private final String authorName;

    public static BookResponseDto fromEntity(Book book) {
        String authorFullName = book.getAuthor().getFirstName() + " " + book.getAuthor().getLastName();
        return new BookResponseDto(book.getId(), book.getName(), book.getIsbn(), authorFullName);
    }
}
