package com.covenant.springbootmysql.controller;

import com.covenant.springbootmysql.dto.MemberCreationRequestDto;
import com.covenant.springbootmysql.dto.MemberResponseDto;
import com.covenant.springbootmysql.dto.MemberUpdateRequestDto;
import com.covenant.springbootmysql.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/members")
@RequiredArgsConstructor
@CrossOrigin("*")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<List<MemberResponseDto>> readMembers() {
        List<MemberResponseDto> responseDtos = memberService.readMembers();
        return ResponseEntity.ok(responseDtos);
    }

    @PostMapping
    public ResponseEntity<MemberResponseDto> createMember(@RequestBody MemberCreationRequestDto request) {
        MemberResponseDto responseDto = memberService.createMember(request);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{memberId}")
    public ResponseEntity<MemberResponseDto> updateMember(@RequestBody MemberUpdateRequestDto request, @PathVariable Long memberId) {
        MemberResponseDto responseDto = memberService.updateMember(memberId, request);
        return ResponseEntity.ok(responseDto);
    }
}
