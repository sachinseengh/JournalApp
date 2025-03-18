package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.entities.JournalEntry;
import net.engineeringdigest.journalApp.entities.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.service.JournalEntryServices;
import net.engineeringdigest.journalApp.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.util.*;


@RestController
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    private JournalEntryServices journalEntryServices;

    @Autowired
    private UserServices userServices;

    @GetMapping
    public List<JournalEntry> getJournalEntries() {
        return journalEntryServices.getAllEntries();
    }

    @GetMapping("/{username}")
    public List<JournalEntry> getJournalEntriesByUsername(@PathVariable String username){
        User user = userServices.findByUsername(username);
        return user.getEntries();
    }

    @PostMapping("/{username}")
    public ResponseEntity addJournalEntries(@RequestBody JournalEntry entry, @PathVariable String username){
        return journalEntryServices.saveEntity(entry,username);
    }

    @GetMapping("/id/{id}")
    public JournalEntry getJournalById(@PathVariable Long id){
        return journalEntryServices.findById(id);
    }

    @PutMapping("/id/{id}")
    public JournalEntry updateJournal(@PathVariable Long id,@RequestBody JournalEntry entry){

        JournalEntry updated = journalEntryServices.updateById(id,entry);
        return updated;
    }

    @DeleteMapping("/id/{id}")
    public boolean deletebyId(@PathVariable Long id){
        return journalEntryServices.deleteById(id);
    }



}
