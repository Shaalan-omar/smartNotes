package com.example.smartNotes.controller;

import com.example.smartNotes.model.Note;
import com.example.smartNotes.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes() {
        List<Note> allNotes= noteService.getAllNotes();
        return ResponseEntity.ok(allNotes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        Note temp = noteService.getNoteById(id);
        return ResponseEntity.ok(temp);
    }

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note) {

        Note saved = noteService.createNote(note);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/error")
    public String throwError() {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request data");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody Note note) {
        return ResponseEntity.ok(noteService.updateNote(id, note));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build(); // 204
    }

}
