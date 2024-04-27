package com.covenant.springbootmysql.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @JsonBackReference
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Book> books;

    protected Author() {
    }

    @Builder
    public Author(Long id, String firstName, String lastName, List<Book> books) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.books = books;
    }
}
