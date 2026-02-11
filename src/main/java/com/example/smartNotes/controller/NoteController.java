package com.example.smartNotes.controller;

import com.example.smartNotes.dto.CreateNoteRequest;
import com.example.smartNotes.dto.NoteResponse;
import com.example.smartNotes.dto.UpdateNoteRequest;
import com.example.smartNotes.model.Note;
import com.example.smartNotes.service.NoteService;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<NoteResponse>> getAllNotes() {
        List<NoteResponse> allNotes= noteService.getAllNotes();
        return ResponseEntity.ok(allNotes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponse> getNoteById(@PathVariable Long id) {
        NoteResponse temp = noteService.getNoteById(id);
        return ResponseEntity.ok(temp);
    }

    @PostMapping
    public ResponseEntity<NoteResponse> createNote(@Valid @RequestBody CreateNoteRequest note) {

        NoteResponse saved = noteService.createNote(note);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/error")
    public String throwError() {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request data");
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponse> updateNote(@PathVariable Long id, @Valid @RequestBody UpdateNoteRequest note) {
        return ResponseEntity.ok(noteService.updateNote(id, note));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build(); // 204
    }
    @GetMapping("/search")
    public ResponseEntity<?> searchTitle(@RequestParam String keyword) {
        return ResponseEntity.ok(noteService.searchByTitle(keyword));
    }

    // JPQL: title OR content, paginated (Step 3 style)
    @GetMapping("/search-all")
    public ResponseEntity<?> searchTitleOrContent(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        var pageable = org.springframework.data.domain.PageRequest.of(page, size);
        return ResponseEntity.ok(noteService.searchTitleOrContent(keyword,  pageable));
    }
    @GetMapping("/paged")
    public ResponseEntity<?> getAllPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        var sort = direction.equalsIgnoreCase("asc")
                ? org.springframework.data.domain.Sort.by(sortBy).ascending()
                : org.springframework.data.domain.Sort.by(sortBy).descending();

        var pageable = org.springframework.data.domain.PageRequest.of(page, size, sort);

        return ResponseEntity.ok(noteService.getAllNotes(pageable));
    }
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<?> getNotesByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        var pageable = org.springframework.data.domain.PageRequest.of(page, size);
        return ResponseEntity.ok(noteService.getNotesByUser(userId, pageable));
    }

}
