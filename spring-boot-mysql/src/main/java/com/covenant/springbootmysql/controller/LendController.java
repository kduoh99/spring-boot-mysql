package com.covenant.springbootmysql.controller;

import com.covenant.springbootmysql.dto.BookLendRequestDto;
import com.covenant.springbootmysql.service.LendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/lend")
@RequiredArgsConstructor
@CrossOrigin("*")
public class LendController {

    private final LendService lendService;

    @PostMapping("/lend")
    public ResponseEntity<List<String>> lendABook(@RequestBody BookLendRequestDto bookLendRequestsDto) {
        List<String> approvedBooks = lendService.lendABook(bookLendRequestsDto);
        return ResponseEntity.ok(approvedBooks);
    }
}
