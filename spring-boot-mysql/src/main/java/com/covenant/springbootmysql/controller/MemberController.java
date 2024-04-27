package com.covenant.springbootmysql.controller;

import com.covenant.springbootmysql.dto.MemberCreationRequestDto;
import com.covenant.springbootmysql.dto.MemberResponseDto;
import com.covenant.springbootmysql.dto.MemberUpdateRequestDto;
import com.covenant.springbootmysql.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/members")
@RequiredArgsConstructor
@CrossOrigin("*")
public class MemberController {

    private final LibraryService libraryService;

    @GetMapping
    public ResponseEntity<List<MemberResponseDto>> readMembers() {
        List<MemberResponseDto> responseDtos = libraryService.readMembers();
        return ResponseEntity.ok(responseDtos);
    }

    @PostMapping
    public ResponseEntity<MemberResponseDto> createMember(@RequestBody MemberCreationRequestDto request) {
        MemberResponseDto responseDto = libraryService.createMember(request);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{memberId}")
    public ResponseEntity<MemberResponseDto> updateMember(@RequestBody MemberUpdateRequestDto request, @PathVariable Long memberId) {
        MemberResponseDto responseDto = libraryService.updateMember(memberId, request);
        return ResponseEntity.ok(responseDto);
    }
}
