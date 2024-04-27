package com.covenant.springbootmysql.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberCreationRequestDto {
    private String firstName;
    private String lastName;
}
