package com.covenant.springbootmysql.controller;

import com.covenant.springbootmysql.dto.BookCreationRequestDto;
import com.covenant.springbootmysql.dto.BookResponseDto;
import com.covenant.springbootmysql.dto.BookLendRequestDto;
import com.covenant.springbootmysql.dto.BookUpdateRequestDto;
import com.covenant.springbootmysql.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/books")
@RequiredArgsConstructor
@CrossOrigin("*")
public class BookController {

    private final LibraryService libraryService;

    @GetMapping
    public ResponseEntity<List<BookResponseDto>> readBooks(@RequestParam(required = false) String isbn) {
        if (isbn == null) {
            return ResponseEntity.ok(libraryService.readBooks());
        } else {
            return ResponseEntity.ok(List.of(libraryService.readBook(isbn)));
        }
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookResponseDto> readBook(@PathVariable Long bookId) {
        BookResponseDto responseDto = libraryService.readBook(bookId);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping
    public ResponseEntity<BookResponseDto> createBook(@RequestBody BookCreationRequestDto request) {
        BookResponseDto responseDto = libraryService.createBook(request);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{bookId}")
    public ResponseEntity<BookResponseDto> updateBook(@PathVariable("bookId") Long bookId, @RequestBody BookUpdateRequestDto request) {
        BookResponseDto responseDto = libraryService.updateBook(bookId, request);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        libraryService.deleteBook(bookId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/lend")
    public ResponseEntity<List<String>> lendABook(@RequestBody BookLendRequestDto bookLendRequestsDto) {
        List<String> approvedBooks = libraryService.lendABook(bookLendRequestsDto);
        return ResponseEntity.ok(approvedBooks);
    }
}
