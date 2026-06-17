package com.bajaj.bfhl.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.bajaj.bfhl.dto.BfhlRequest;
import com.bajaj.bfhl.dto.BfhlResponse;
import com.bajaj.bfhl.service.BfhlService;

@Service

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

        double sum = 0;
        Double largest = null;
        Double smallest = null;

        Map<String, Integer> alphabetFrequency = new HashMap<>();

        int vowelCount = 0;

        for (String value : uniqueData) {
            // Pure Number
            if (isPureNumber(value)) {
                double number = Double.parseDouble(value);
                sum += number;
                if (largest == null || number > largest) {
                    largest = number;
                }
                if (smallest == null || number < smallest) {
                    smallest = number;
                }
                // Only integers can be odd/even
                if (number % 1 == 0) {
                    if (((long) number) % 2 == 0) {
                        evenNumbers.add(value);
                    } else {
                        oddNumbers.add(value);
                    }
                }
            } // Pure Alphabet
            else if (isPureAlpha(value)) {
                String upper = value.toUpperCase();
                alphabets.add(upper);
                for (char c : upper.toCharArray()) {
                    alphabetFrequency.put(
                            String.valueOf(c),
                            alphabetFrequency.getOrDefault(String.valueOf(c), 0) + 1);
                    if ("AEIOU".indexOf(c) != -1) {
                        vowelCount++;
                    }
                }
            } // Pure Special Character
            else if (isPureSpecial(value)) {
                specialCharacters.add(value);
            } // Alphanumeric
            else {

                StringBuilder numberBuilder = new StringBuilder();

                for (char c : value.toCharArray()) {

                    if (Character.isLetter(c)) {

                        char upper = Character.toUpperCase(c);

                        alphabets.add(String.valueOf(upper));

                        alphabetFrequency.put(
                                String.valueOf(upper),
                                alphabetFrequency.getOrDefault(String.valueOf(upper), 0) + 1);

                        if ("AEIOU".indexOf(upper) != -1) {
                            vowelCount++;
                        }

                    } else if (Character.isDigit(c) || c == '.' || c == '-') {

                        numberBuilder.append(c);
                    }
                }

                if (!numberBuilder.isEmpty()) {

                    double number = Double.parseDouble(numberBuilder.toString());

                    sum += number;

                    if (largest == null || number > largest) {
                        largest = number;
                    }

                    if (smallest == null || number < smallest) {
                        smallest = number;
                    }

                    if (number % 1 == 0) {

                        if (((long) number) % 2 == 0) {
                            evenNumbers.add(numberBuilder.toString()); 
                        }else {
                            oddNumbers.add(numberBuilder.toString());
                        }
                    }
                }
            }
        }

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
