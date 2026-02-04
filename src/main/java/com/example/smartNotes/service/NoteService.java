package com.example.smartNotes.service;

import com.example.smartNotes.model.Note;
import com.example.smartNotes.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    // Constructor Injection (DI) which will find the InMemoryNoteRepository because of @Repository and component scanning and type matching.
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public Note getNoteById(Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found: " + id));
    }

    public Note createNote(Note note) {
        // optional later: validate title/content
        return noteRepository.save(note);
    }
}
