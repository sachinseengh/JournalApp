package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.entities.JournalEntry;
import net.engineeringdigest.journalApp.entities.User;
import net.engineeringdigest.journalApp.service.JournalEntryServices;
import net.engineeringdigest.journalApp.service.UserServices;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    private JournalEntryServices journalEntryServices;

    @Autowired
    private UserServices userServices;

    @GetMapping()
    public List<JournalEntry> getJournalEntriesByUsername(){
        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();
        User user = userServices.findByUsername(authentication.getName());
        return user.getEntries();
    }

    @PostMapping("/create")
    public ResponseEntity addJournalEntries(@RequestBody JournalEntry entry){

        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();
        return journalEntryServices.saveEntity(entry,authentication.getName());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getJournalById(@PathVariable ObjectId id){
        return new ResponseEntity<>(journalEntryServices.findById(id), HttpStatus.OK);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<JournalEntry> updateJournal(@PathVariable ObjectId id,@RequestBody JournalEntry entry){

        Optional<JournalEntry> updated = journalEntryServices.updateById(id,entry);
        if(!(updated == null)){
            return new ResponseEntity<>( updated.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/id/{id}")
    public boolean deletebyId(@PathVariable ObjectId id){
        return journalEntryServices.deleteById(id);
    }



}
