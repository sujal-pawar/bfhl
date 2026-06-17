package com.bajaj.bfhl.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BfhlResponse {

    private boolean is_success;
    private String request_id;

    private List<String> odd_numbers;
    private List<String> even_numbers;
    private List<String> alphabets;
    private List<String> special_characters;

    private String sum;

    private String largest_number;
    private String smallest_number;

    private int alphabet_count;
    private int number_count;
    private int special_character_count;

    private int vowel_count;

    private Integer unique_element_count;

    private boolean contains_duplicates;

    private List<String> sorted_numbers;

    private Map<String, Integer> alphabet_frequency;

    private String longest_alphabetic_value;
    private String shortest_alphabetic_value;

    private long processing_time_ms;

    private Summary summary;

    @Data
    @Builder
    public static class Summary {

        private int total_elements_received;
        private int valid_elements_processed;
        private int invalid_elements_ignored;

    }
}