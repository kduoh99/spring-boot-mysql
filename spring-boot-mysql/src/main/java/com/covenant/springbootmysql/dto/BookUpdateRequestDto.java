package com.covenant.springbootmysql.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookUpdateRequestDto {
    private String name;
    private String isbn;
    private Long authorId;
}
