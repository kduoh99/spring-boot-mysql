package com.covenant.springbootmysql.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BookLendRequestDto {
    private List<Long> bookIds;
    private Long memberId;
}
