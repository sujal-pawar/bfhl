package com.bajaj.bfhl.dto;

import java.util.List;

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

    private boolean contains_duplicates;

    private int vowel_count;
    private int consonant_count;

    private long processing_time_ms;
}