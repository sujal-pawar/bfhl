package com.bajaj.bfhl.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BfhlRequest {

    @NotNull(message = "Data cannot be null")
    private List<Object> data;
}