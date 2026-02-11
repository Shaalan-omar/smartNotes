package com.example.smartNotes.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateNoteRequest {
    @NotBlank
    String title;
    @NotBlank String content;
}
