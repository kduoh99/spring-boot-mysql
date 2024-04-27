package com.covenant.springbootmysql.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberUpdateRequestDto {
    private String lastName;
    private String firstName;
}
