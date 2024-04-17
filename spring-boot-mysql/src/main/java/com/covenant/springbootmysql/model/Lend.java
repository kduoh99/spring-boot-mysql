package com.covenant.springbootmysql.model;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "lend")
public class Lend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ORDINAL은 enum 순서 값을, STRING은 enum 이름을 DB에 저장
    @Enumerated(EnumType.ORDINAL)
    private LendStatus status;

    private Instant startOn;

    private Instant dueOn;

    // 다대일 관계 매핑 어노테이션
    @ManyToOne
    // 외래키를 매핑할 때 사용한다. name에는 참조하는 테이블의 기본키 칼럼명이 들어간다.
    @JoinColumn(name = "book_id")
    // 순환참조를 막기 위한 어노테이션으로, 부모 클래스에 @JsonManagedReference, 자식 클래스에 @JsonBackReference 어노테이션을 추가한다.
    @JsonManagedReference
    private Book book;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @JsonManagedReference
    private Member member;
}
