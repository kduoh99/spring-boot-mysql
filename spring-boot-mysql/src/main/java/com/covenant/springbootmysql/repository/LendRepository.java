package com.covenant.springbootmysql.repository;

import com.covenant.springbootmysql.model.Book;
import com.covenant.springbootmysql.model.Lend;
import com.covenant.springbootmysql.model.LendStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LendRepository extends JpaRepository<Lend, Long> {
    // 여러 필드를 검색하고 싶으면 And로 연결하면 된다.
    Optional<Lend> findByBookAndStatus(Book book, LendStatus status);
}
