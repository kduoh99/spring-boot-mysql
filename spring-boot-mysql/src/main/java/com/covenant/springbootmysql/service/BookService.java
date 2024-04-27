package com.covenant.springbootmysql.service;

import com.covenant.springbootmysql.dto.BookCreationRequestDto;
import com.covenant.springbootmysql.dto.BookResponseDto;
import com.covenant.springbootmysql.dto.BookUpdateRequestDto;
import com.covenant.springbootmysql.model.*;
import com.covenant.springbootmysql.repository.AuthorRepository;
import com.covenant.springbootmysql.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookResponseDto createBook(BookCreationRequestDto request) {
        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException("Author Not Found"));
        Book book = Book.builder()
                .name(request.getName())
                .isbn(request.getIsbn())
                .author(author)
                .build();
        book = bookRepository.save(book);
        return BookResponseDto.fromEntity(book);
    }

    public BookResponseDto readBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cant find any book under given ID: " + id));
        return toBookResponseDto(book);
    }

    public BookResponseDto readBook(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new EntityNotFoundException("Cant find any book under given ISBN: " + isbn));
        return toBookResponseDto(book);
    }

    public List<BookResponseDto> readBooks() {
        return bookRepository.findAll().stream()
                .map(BookResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public BookResponseDto updateBook(Long bookId, BookUpdateRequestDto request) {
        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException("Author Not Found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book Not Found"));
        book.update(request.getName(), request.getIsbn(), author);
        book = bookRepository.save(book);
        return BookResponseDto.fromEntity(book);
    }

    private BookResponseDto toBookResponseDto(Book book) {
        String authorName = book.getAuthor().getFirstName() + " " + book.getAuthor().getLastName();
        return new BookResponseDto(book.getId(), book.getName(), book.getIsbn(), authorName);
    }
}
