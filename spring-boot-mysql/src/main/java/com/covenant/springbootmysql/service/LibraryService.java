package com.covenant.springbootmysql.service;

import com.covenant.springbootmysql.dto.*;
import com.covenant.springbootmysql.model.*;
import com.covenant.springbootmysql.repository.AuthorRepository;
import com.covenant.springbootmysql.repository.BookRepository;
import com.covenant.springbootmysql.repository.LendRepository;
import com.covenant.springbootmysql.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LibraryService {

    private final AuthorRepository authorRepository;
    private final MemberRepository memberRepository;
    private final LendRepository lendRepository;
    private final BookRepository bookRepository;

    public BookResponseDto readBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cant find any book under given ID: " + id));
        return toBookResponseDto(book);
    }

    public List<BookResponseDto> readBooks() {
        return bookRepository.findAll().stream()
                .map(BookResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public BookResponseDto readBook(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new EntityNotFoundException("Cant find any book under given ISBN: " + isbn));
        return toBookResponseDto(book);
    }

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

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    private BookResponseDto toBookResponseDto(Book book) {
        String authorName = book.getAuthor().getFirstName() + " " + book.getAuthor().getLastName();
        return new BookResponseDto(book.getId(), book.getName(), book.getIsbn(), authorName);
    }

    public MemberResponseDto createMember(MemberCreationRequestDto request) {
        Member member = Member.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .status(MemberStatus.ACTIVE)
                .build();
        member = memberRepository.save(member);
        return MemberResponseDto.fromEntity(member);
    }

    public MemberResponseDto updateMember (Long id, MemberUpdateRequestDto request) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Member not present in the database"));
        member.updateName(request.getFirstName(), request.getLastName());
        member = memberRepository.save(member);
        return MemberResponseDto.fromEntity(member);
    }

    public AuthorResponseDto createAuthor (AuthorCreationRequestDto request) {
        Author author = Author.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
        author = authorRepository.save(author);
        return AuthorResponseDto.fromEntity(author);
    }

    public List<String> lendABook (BookLendRequestDto request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException("Member not present in the database"));
        if (member.getStatus() != MemberStatus.ACTIVE) {
            throw new RuntimeException("User is not active to proceed a lending.");
        }

        List<String> booksApprovedToBurrow = new ArrayList<>();
        request.getBookIds().forEach(bookId -> {
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new EntityNotFoundException("Cant find any book under given ID"));

            boolean isBurrowed = lendRepository.findByBookAndStatus(book, LendStatus.BURROWED).isPresent();
            if (!isBurrowed) {
                booksApprovedToBurrow.add(book.getName());
                Lend lend = Lend.builder()
                        .member(member)
                        .book(book)
                        .status(LendStatus.BURROWED)
                        .startOn(Instant.now())
                        .dueOn(Instant.now().plus(30, ChronoUnit.DAYS))
                        .build();
                lendRepository.save(lend);
            }
        });
        return booksApprovedToBurrow;
    }


    public List<AuthorResponseDto> readAuthors() {
        return authorRepository.findAll().stream()
                .map(AuthorResponseDto::fromEntity)
                .collect(Collectors.toList());
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

    public List<MemberResponseDto> readMembers() {
        return memberRepository.findAll().stream()
                .map(MemberResponseDto::fromEntity)
                .collect(Collectors.toList());
    }
}