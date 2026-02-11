package com.example.smartNotes.repository;

import com.example.smartNotes.model.Note;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import org.springframework.data.domain.PageRequest;
import java.util.List;
import java.util.Optional;

//I created this as an interface for abstraction and DI. Now I will not use a real DB, I will just mock it.
//In the future I will implement a local, fast DB, like H2. Or maybe a MySQL DB.
public interface NoteJpaRepository extends JpaRepository<Note, Long> {
    // Derived query methods (Spring generates JPQL under the hood)
    Optional<Note> findByTitle(String title);
    List<Note> findByTitleContainingIgnoreCase(String keyword);
    Page<Note> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);
    @Query("select n from Note n where lower(n.title) like lower(concat('%', :kw, '%')) " +
            "or lower(n.content) like lower(concat('%', :kw, '%'))")
    Page<Note> searchTitleOrContent(@Param("kw") String keyword, Pageable pageable);

    // Notes for a user (pagination)
    Page<Note> findByUserId(Long userId, Pageable pageable);

//Old CRUD methods delcerations as they are offered by JPA that extends the PagingAndSorting interface which uses the CRUD Repo interface
//  No need for the InMemoryNoteRepository anymore as we are using H2, a reL Database.
//    List<Note> findAll();
//    Optional<Note> findById(Long id);
//    Note save(Note note);
//    void deleteById(Long id);
//    boolean existsById(Long id);


}
