package com.example.smartNotes.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserRequest {
    @NotBlank @Size(min = 4, max = 50) private String username;
    @NotBlank @Email private String email;
}
