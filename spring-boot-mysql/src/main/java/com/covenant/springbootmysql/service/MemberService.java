package com.covenant.springbootmysql.service;

import com.covenant.springbootmysql.dto.MemberCreationRequestDto;
import com.covenant.springbootmysql.dto.MemberResponseDto;
import com.covenant.springbootmysql.dto.MemberUpdateRequestDto;
import com.covenant.springbootmysql.model.Member;
import com.covenant.springbootmysql.model.MemberStatus;
import com.covenant.springbootmysql.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponseDto createMember(MemberCreationRequestDto request) {
        Member member = Member.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .status(MemberStatus.ACTIVE)
                .build();
        member = memberRepository.save(member);
        return MemberResponseDto.fromEntity(member);
    }

    public MemberResponseDto updateMember(Long id, MemberUpdateRequestDto request) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Member not present in the database"));
        member.updateName(request.getFirstName(), request.getLastName());
        member = memberRepository.save(member);
        return MemberResponseDto.fromEntity(member);
    }

    public List<MemberResponseDto> readMembers() {
        return memberRepository.findAll().stream()
                .map(MemberResponseDto::fromEntity)
                .collect(Collectors.toList());
    }
}
