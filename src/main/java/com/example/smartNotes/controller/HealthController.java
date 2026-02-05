package com.example.smartNotes.controller;


import com.example.smartNotes.model.Note;
import com.example.smartNotes.service.NoteService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

//This works, and this means that the embedded tomcat server works as I can run it on the localhost
@RestController //That was a bonus for me tbh, but I want to understand the difference between it and controller, is it only for Restful endpoints?
@RequiredArgsConstructor
public class HealthController {

    private final NoteService noteService;

    @GetMapping("/notehealth") //Normal mapping for defining the mapping of the endpoint I believe, but not yet covered in the 2 sections I studied
    public String health(@RequestParam String title){
        Note note = Note.builder()
                .title(title)
                .content("Created from notehealth")
                .build();
        noteService.createNote(note);
        return "This is a check for note" + note.getTitle();
    }
    @GetMapping("error")
    public String throwError() {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request data");
    }
}
