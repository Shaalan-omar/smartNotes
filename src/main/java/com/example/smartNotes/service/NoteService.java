package com.example.smartNotes.service;

import com.example.smartNotes.dto.CreateNoteRequest;
import com.example.smartNotes.dto.NoteResponse;
import com.example.smartNotes.dto.UpdateNoteRequest;
import com.example.smartNotes.exception.NoteNotFoundException;
import com.example.smartNotes.exception.UserNotFoundException;
import com.example.smartNotes.model.Note;
import com.example.smartNotes.model.User;
import com.example.smartNotes.repository.NoteJpaRepository;
import com.example.smartNotes.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.PageRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteJpaRepository noteRepository;
    private final UserJpaRepository userRepository;

    private NoteResponse toResponse(Note note) {
        return NoteResponse.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .build();
    }
    public List<NoteResponse> getAllNotes() {
        System.out.println("GET ALL notes.count() = " + noteRepository.count());
        return noteRepository.findAll().stream().map(this::toResponse).collect(java.util.stream.Collectors.toList());
    }
    // Pagination version (Step 3)
    public Page<NoteResponse> getAllNotes(Pageable pageable) {
        return noteRepository.findAll(pageable).map(this::toResponse);
    }
    public NoteResponse getNoteById(Long id) {
        return toResponse(noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id)));
    }
    // Step 2 search
    public List<NoteResponse> searchByTitle(String keyword) {
        return noteRepository.findByTitleContainingIgnoreCase(keyword)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }
    // Step 3 + Step 2 combined: paginated search derived
    public Page<NoteResponse> searchByTitle(String keyword, Pageable pageable) {
        return noteRepository.findByTitleContainingIgnoreCase(keyword, pageable)
                .map(this::toResponse);
    }

    // JPQL paginated search
    public Page<NoteResponse> searchTitleOrContent(String keyword, Pageable pageable) {
        return noteRepository.searchTitleOrContent(keyword, pageable)
                .map(this::toResponse);
    }
    public NoteResponse createNote(CreateNoteRequest dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException(dto.getUserId()));
        Note note = new Note();
        note.setTitle(dto.getTitle());
        note.setContent(dto.getContent());
        //note.setId(dto.getUserId());
        note.setUser(user);

        Note saved = noteRepository.save(note);
        System.out.println("AFTER SAVE notes.count() = " + noteRepository.count() + " savedId=" + saved.getId());
        return toResponse(saved);
    }

    public NoteResponse updateNote(Long id, UpdateNoteRequest dto) {
        Note existing = noteRepository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));

        existing.setTitle(dto.getTitle());
        existing.setContent(dto.getContent());

        Note saved = noteRepository.save(existing);
        return toResponse(saved);
    }

    public void deleteNote(Long id) {
        if (!noteRepository.existsById(id)) {
            throw new NoteNotFoundException(id);
        }
        noteRepository.deleteById(id);
    }

    // Notes by user
    public Page<NoteResponse> getNotesByUser(Long userId, Pageable pageable) {
        // optional: verify user exists for better 404
        if (!userRepository.existsById(userId)) throw new UserNotFoundException(userId);

        return noteRepository.findByUserId(userId, pageable)
                .map(this::toResponse);
    }
}


//In the code below I implemented a direct way of exception handling for every exception using "ResponseStatusException" which let us specify the status code and the error message.
//I wanted to handle it globally, so I am using a custom exception in the code above that is handled globally.
//package com.example.smartNotes.service;
//
//import com.example.smartNotes.model.Note;
//import com.example.smartNotes.repository.NoteRepository;
//import lombok.AllArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class NoteService {
//
//    private final NoteRepository noteRepository;
//
//    // Constructor Injection (DI) which will find the InMemoryNoteRepository because of @Repository and component scanning and type matching.
//    //public NoteService(NoteRepository noteRepository) {
//    //    this.noteRepository = noteRepository;
//    //}
//
//    public List<Note> getAllNotes() {
//        return noteRepository.findAll();
//    }
//
//    public Note getNoteById(Long id) {
//        return noteRepository.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note of id: " + id +" cannot be found"));
//    }
//
//    public Note createNote(Note note) {
//        // optional later: validate title/content
//        return noteRepository.save(note);
//    }
//    public Note updateNote(Long id, Note updatedNote){
//        Note existing = noteRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found with id: " + id));
//
//        existing.setTitle(updatedNote.getTitle());
//        existing.setContent(updatedNote.getContent());
//
//        return noteRepository.save(existing);
//    }
//    public void deleteNote(Long id){
//        if (!noteRepository.existsById(id)) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found: " + id); //This is like having a turnary operator in c++
//        }
//        noteRepository.deleteById(id);
//    }
//}
