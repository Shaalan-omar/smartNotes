package com.example.smartNotes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ApiError {
    private int status;
    private String message;
    private Map<String, String> fieldErrors; // null for non-validation errors
}
