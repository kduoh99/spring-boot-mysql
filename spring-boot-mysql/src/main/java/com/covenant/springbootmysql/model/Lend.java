package com.covenant.springbootmysql.model;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Table(name = "lend")
public class Lend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private LendStatus status;

    private Instant startOn;
    private Instant dueOn;

    @ManyToOne
    @JoinColumn(name = "book_id")
    @JsonManagedReference
    private Book book;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @JsonManagedReference
    private Member member;

    protected Lend() {
    }

    @Builder
    public Lend(Member member, Book book, LendStatus status, Instant startOn, Instant dueOn) {
        this.member = member;
        this.book = book;
        this.status = status;
        this.startOn = startOn;
        this.dueOn = dueOn;
    }
}
