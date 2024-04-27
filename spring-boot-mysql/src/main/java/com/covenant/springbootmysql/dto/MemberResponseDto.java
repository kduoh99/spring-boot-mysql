package com.covenant.springbootmysql.dto;

import com.covenant.springbootmysql.model.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberResponseDto {
    private final Long id;
    private final String lastName;
    private final String firstName;

    public static MemberResponseDto fromEntity(Member member) {
        return new MemberResponseDto(member.getId(), member.getFirstName(), member.getLastName());
    }

}
