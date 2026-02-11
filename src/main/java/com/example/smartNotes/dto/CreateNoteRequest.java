package com.example.smartNotes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreateNoteRequest {
    @NotBlank(message = "title is required")
    @Size(max = 100, message = "Title should be less than 100 chars")
    String title;
    @NotBlank(message = "content is required")
    @Size(max = 5000, message = "content must be <= 5000 chars")
    @NotBlank(message="content is required")
    String content;
    @NotNull(message="userId is required")
    Long userId;
}
