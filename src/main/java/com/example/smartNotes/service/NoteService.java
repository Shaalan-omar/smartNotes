package com.example.smartNotes.service;

import com.example.smartNotes.model.Note;
import com.example.smartNotes.repository.NoteRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    // Constructor Injection (DI) which will find the InMemoryNoteRepository because of @Repository and component scanning and type matching.
    //public NoteService(NoteRepository noteRepository) {
    //    this.noteRepository = noteRepository;
    //}

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public Note getNoteById(Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note of id: " + id +" cannot be found"));
    }

    public Note createNote(Note note) {
        // optional later: validate title/content
        return noteRepository.save(note);
    }
    public Note updateNote(Long id, Note updatedNote){
        Note existing = noteRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found with id: " + id));

        existing.setTitle(updatedNote.getTitle());
        existing.setContent(updatedNote.getContent());

        return noteRepository.save(existing);
    }
    public void deleteNote(Long id){
        if (!noteRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found: " + id); //This is like having a turnary operator in c++
        }
        noteRepository.deleteById(id);
    }
}
