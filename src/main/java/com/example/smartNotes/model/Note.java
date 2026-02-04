package com.example.smartNotes.model;
//This class defines the data shape simply.
//I did not use JPA or Hibernate here yet cause I did not cover them yet. So here it is not yet an entity.

public class Note {
    private Long id;
    private String title, content;

    public Note(){

    }
    public Note(Long id, String title, String content){ //Edeeni fel dependency injection
        this.id = id;
        this.title = title;
        this.content = content;
    }

    //Nesht8al 3alla nedeef ba2a w nedeeha getters w setters


    public void setId(Long id) {
        this.id = id;

    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getcontent() {
        return content;
    }
    public String getTitle() {
        return title;
    }
}
