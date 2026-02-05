package com.example.smartNotes.repository;

import com.example.smartNotes.model.Note;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Profile("dev")
//This is in memory DB is for development purposes only, so I want to used it only with the dev profile
public class InMemoryNoteRepository implements NoteRepository {
    private Map<Long, Note> storage = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    public InMemoryNoteRepository() {

        save(new Note(null, "First Note", "This is the content of the first note"));
        save(new Note(null, "Second Note", "This is the second note's content"));
    }

    @Override
    public List<Note> findAll() {
        return new ArrayList<>(storage.values());
    }
    @Override
    public Optional<Note> findById(Long id) {
        return Optional.ofNullable(storage.get(id)); //Returns null or the whole note
    }

    @Override
    public Note save(Note note) {
        if (note.getId() == null) {
            note.setId(idGenerator.incrementAndGet());
        }
        storage.put(note.getId(), note);
        return note;
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return storage.containsKey(id);
    }
}
