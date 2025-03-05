package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.entities.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalController {

    Map<Long, JournalEntry> journalEntries = new HashMap<Long, JournalEntry>();


    @GetMapping
    public List<JournalEntry> getJournalEntries() {
        return new ArrayList<JournalEntry>(journalEntries.values());
    }

    @PostMapping
    public boolean addJournalEntries(@RequestBody JournalEntry entry){
        journalEntries.put(entry.getId(),entry);
        return true;
    }

    @GetMapping("/id/{id}")
    public JournalEntry getJournalById(@PathVariable Long id){
        return journalEntries.get(id);
    }

    @PutMapping("/id/{id}")
    public boolean updateJournal(@PathVariable Long id,@RequestBody JournalEntry entry){
        journalEntries.put(id,entry);
        return true;
    }

    @DeleteMapping("/id/{id}")
    public boolean deletebyId(@PathVariable Long id){
        journalEntries.remove(id);
        return true;
    }



}
