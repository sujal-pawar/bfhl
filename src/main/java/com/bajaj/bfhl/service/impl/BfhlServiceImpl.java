package com.bajaj.bfhl.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.bajaj.bfhl.dto.BfhlRequest;
import com.bajaj.bfhl.dto.BfhlResponse;
import com.bajaj.bfhl.service.BfhlService;

@ Service 

    public class BfhlServiceImpl implements BfhlService {

        @Override
        public BfhlResponse process(BfhlRequest request, String requestId) {

            long startTime = System.currentTimeMillis();

            List<Object> rawData = request.getData();

            int totalReceived = rawData.size();

            List<String> cleaned = rawData.stream()
                    .filter(Objects::nonNull)
                    .map(Object::toString)
                    .filter(s -> !s.isBlank())
                    .toList();

            int invalidIgnored = totalReceived - cleaned.size();

            Set<String> seen = new HashSet<>();
            boolean containsDuplicates = cleaned.stream()
                    .anyMatch(s -> !seen.add(s));

            List<String> uniqueData = new ArrayList<>();

            for (String value : cleaned) {
                if (!uniqueData.contains(value)) {
                    uniqueData.add(value);
                }
            }

            List<String> oddNumbers = new ArrayList<>();
            List<String> evenNumbers = new ArrayList<>();
            List<String> alphabets = new ArrayList<>();
            List<String> specialCharacters = new ArrayList<>();

            return BfhlResponse.builder().build();
        }
        
    private boolean isPureNumber(String s) {
        return s.matches("-?\\d+(\\.\\d+)?");
    }

    private boolean isPureAlpha(String s) {
        return s.matches("[a-zA-Z]+");
    }

    private boolean isPureSpecial(String s) {
        return s.chars().noneMatch(Character::isLetterOrDigit);
    }
}
