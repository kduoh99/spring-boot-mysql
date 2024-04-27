package com.covenant.springbootmysql.service;

import com.covenant.springbootmysql.dto.BookLendRequestDto;
import com.covenant.springbootmysql.model.*;
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

@Service
@RequiredArgsConstructor
@Transactional
public class LendService {

    private final LendRepository lendRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    public List<String> lendABook(BookLendRequestDto request) {
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
}
