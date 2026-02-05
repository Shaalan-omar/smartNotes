package com.example.smartNotes.repository;

import com.example.smartNotes.model.Note;

import java.util.List;
import java.util.Optional;

//I created this as an interface for abstraction and DI. Now I will not use a real DB, I will just mock it.
//In the future I will implement a local, fast DB, like H2. Or maybe a MySQL DB.
public interface NoteRepository {
    List<Note> findAll();
    Optional<Note> findById(Long id);
    Note save(Note note);
    void deleteById(Long id);
    boolean existsById(Long id);
}
