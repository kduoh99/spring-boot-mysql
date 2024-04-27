package com.covenant.springbootmysql.service;

import com.covenant.springbootmysql.model.*;
import com.covenant.springbootmysql.model.request.AuthorCreationRequest;
import com.covenant.springbootmysql.model.request.BookCreationRequest;
import com.covenant.springbootmysql.model.request.BookLendRequest;
import com.covenant.springbootmysql.model.request.MemberCreationRequest;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LibraryService {

    private final AuthorRepository authorRepository;
    private final MemberRepository memberRepository;
    private final LendRepository lendRepository;
    private final BookRepository bookRepository;

    public Book readBook(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return book.get();
        }
        throw new EntityNotFoundException("Cant find any book under given ID");
    }

    public List<Book> readBooks() {
        return bookRepository.findAll();
    }

    public Book readBook(String isbn) {
        Optional<Book> book = bookRepository.findByIsbn(isbn);
        if (book.isPresent()) {
            return book.get();
        }
        throw new EntityNotFoundException("Cant find any book under given ISBN");
    }

    public Book createBook(BookCreationRequest request) {
        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException("Author Not Found"));
        Book book = Book.builder()
                .name(request.getName())
                .isbn(request.getIsbn())
                .author(author)
                .build();
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public Member createMember(MemberCreationRequest request) {
        Member member = Member.builder()
                .lastName(request.getLastName())
                .firstName(request.getFirstName())
                .status(MemberStatus.ACTIVE)
                .build();
        return memberRepository.save(member);
    }

    public Member updateMember (Long id, MemberCreationRequest request) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Member not present in the database"));
        member.updateName(request.getFirstName(), request.getLastName());
        return memberRepository.save(member);
    }

    public Author createAuthor (AuthorCreationRequest request) {
        Author author = Author.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
        return authorRepository.save(author);
    }

    public List<String> lendABook (BookLendRequest request) {
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


    public List<Author> readAuthors() {
        return authorRepository.findAll();
    }

    public Book updateBook(Long bookId, BookCreationRequest request) {
        Optional<Author> author = authorRepository.findById(request.getAuthorId());
        if (!author.isPresent()) {
            throw new EntityNotFoundException("Author Not Found");
        }
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (!optionalBook.isPresent()) {
            throw new EntityNotFoundException("Book Not Found");
        }
        Book book = optionalBook.get();
        book.update(request.getName(), request.getIsbn(), author.get());
        return bookRepository.save(book);
    }

    public List<Member> readMembers() {
        return memberRepository.findAll();
    }
}