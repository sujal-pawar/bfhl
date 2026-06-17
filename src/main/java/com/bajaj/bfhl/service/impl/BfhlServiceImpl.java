package com.bajaj.bfhl.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        int vowelCount = 0;
        int consonantCount = 0;
        List<Double> allNumbers = new ArrayList<>();

        for (String value : uniqueData) {
            // Pure Number
            if (isPureNumber(value)) {
                double number = Double.parseDouble(value);
                allNumbers.add(number);
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
                    if ("AEIOU".indexOf(c) != -1) {
                        vowelCount++;
                    } else {
                        consonantCount++;
                    }
                }
            } // Pure Special Character
            else if (isPureSpecial(value)) {
                specialCharacters.add(value);
            } // Alphanumeric
            else {

                // Extract numeric tokens (allowing integers and decimals, with optional leading -)
                Pattern numberPattern = Pattern.compile("-?\\d+(\\.\\d+)?");
                Matcher numberMatcher = numberPattern.matcher(value);
                while (numberMatcher.find()) {
                    String numStr = numberMatcher.group();
                    double number = Double.parseDouble(numStr);
                    allNumbers.add(number);
                    sum += number;
                    if (largest == null || number > largest) {
                        largest = number;
                    }
                    if (smallest == null || number < smallest) {
                        smallest = number;
                    }
                    if (number % 1 == 0) {
                        if (((long) number) % 2 == 0) {
                            evenNumbers.add(numStr);
                        } else {
                            oddNumbers.add(numStr);
                        }
                    }
                }

                // Extract contiguous alphabetic tokens and update vowel/consonant counts
                Pattern alphaPattern = Pattern.compile("[A-Za-z]+");
                Matcher alphaMatcher = alphaPattern.matcher(value);
                while (alphaMatcher.find()) {
                    String alphaToken = alphaMatcher.group().toUpperCase();
                    alphabets.add(alphaToken);
                    for (char c : alphaToken.toCharArray()) {
                        if ("AEIOU".indexOf(c) != -1) {
                            vowelCount++;
                        } else {
                            consonantCount++;
                        }
                    }
                }
            }
        }

        long processingTime = System.currentTimeMillis() - startTime;

        return BfhlResponse.builder()
                .is_success(true)
                .request_id(requestId)
                .odd_numbers(oddNumbers)
                .even_numbers(evenNumbers)
                .alphabets(alphabets)
                .special_characters(specialCharacters)
                .sum(String.valueOf(sum))
                .largest_number(largest == null ? null : String.valueOf(largest))
                .smallest_number(smallest == null ? null : String.valueOf(smallest))
                .alphabet_count(alphabets.size())
                .number_count(allNumbers.size())
                .special_character_count(specialCharacters.size())
                .contains_duplicates(containsDuplicates)
                .vowel_count(vowelCount)
                .consonant_count(consonantCount)
                .processing_time_ms(processingTime)
                .build();
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
