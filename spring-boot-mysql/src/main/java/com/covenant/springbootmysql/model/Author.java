package com.covenant.springbootmysql.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @JsonBackReference
    // fetch는 EAGER(즉시로딩)와 LAZY(지연로딩)로 나뉘며, LAZY는 조인이 필요한 경우에 Join을 한다.
    // CascadeType.ALL이면 부모와 자식의 상태가 동시에 변하게 된다.
    @OneToMany(mappedBy = "author",
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Book> books;
}
