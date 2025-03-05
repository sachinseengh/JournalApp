package net.engineeringdigest.journalApp.entities;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class JournalEntry {

    private Long id;

    private String title;
    private String content;
}
