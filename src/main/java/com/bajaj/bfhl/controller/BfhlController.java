package com.bajaj.bfhl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.bajaj.bfhl.dto.BfhlRequest;
import com.bajaj.bfhl.dto.BfhlResponse;
import com.bajaj.bfhl.service.BfhlService;

import jakarta.validation.Valid;

@RestController
public class BfhlController {

    @Autowired
    private BfhlService bfhlService;

    @PostMapping("/bfhl")
    public BfhlResponse process(
            @Valid @RequestBody BfhlRequest request,
            @RequestHeader(value = "X-Request-Id", required = false) String requestId) {

        return bfhlService.process(request, requestId);
    }
}