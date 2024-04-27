package com.covenant.springbootmysql.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @JsonBackReference
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Lend> lends;

    protected Member() {
    }

    @Builder
    public Member(Long id, String firstName, String lastName, MemberStatus status, List<Lend> lends) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.lends = lends;
    }

    public void updateName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
