package com.covenant.springbootmysql.repository;

import com.covenant.springbootmysql.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    // save(): 레코드 저장
    // findOne(): PK로 레코드 한 건 찾기
    // findAll(): 전체 레코드 불러오기
    // count(): 레코드 갯수
    // delete(): 레코드 삭제
}
