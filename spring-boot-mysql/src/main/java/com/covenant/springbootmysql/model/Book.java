package com.covenant.springbootmysql.model;

import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String isbn;


    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Lend> lends;

    protected Book() {
    }

    @Builder
    public Book(String name, String isbn, Author author, List<Lend> lends) {
        this.name = name;
        this.isbn = isbn;
        this.author = author;
        this.lends = lends;
    }

    public void update(String name, String isbn, Author author) {
        this.name = name;
        this.isbn = isbn;
        this.author = author;
    }
}
