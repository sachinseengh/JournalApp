package net.engineeringdigest.journalApp.service;


import ch.qos.logback.classic.joran.JoranConfigurator;
import net.engineeringdigest.journalApp.entities.JournalEntry;
import net.engineeringdigest.journalApp.entities.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryServices {

@Autowired
private JournalEntryRepository journalEntryRepository;

@Autowired
private UserServices userServices;


@Transactional
public ResponseEntity saveEntity(JournalEntry entry, String username){

    try {
        User user = userServices.findByUsername(username);
        entry.setDate(LocalDateTime.now());
        JournalEntry saved= journalEntryRepository.save(entry);
        user.getEntries().add(saved);
//        user.setUserName(null);
        userServices.saveUser(user);
        return new ResponseEntity(saved, HttpStatus.CREATED);

    }catch(Exception e){
        System.out.println(e);
        throw new RuntimeException("Username is null");
    }

}

public List<JournalEntry> getAllEntries(){
   return journalEntryRepository.findAll();
}

public JournalEntry findById(Long id){
    JournalEntry entry = journalEntryRepository.findById(id).orElse(null);
    return entry;
}

public JournalEntry updateById(Long id,JournalEntry newEntry){

    JournalEntry entry = journalEntryRepository.findById(id).orElse(null);

    if(newEntry !=null){
        entry.setTitle(newEntry.getTitle() !=null && !newEntry.getTitle().equals("")?newEntry.getTitle():entry.getTitle());

        entry.setContent(newEntry.getContent() !=null && !newEntry.getContent().equals("")?newEntry.getContent():entry.getContent());
    }

    journalEntryRepository.save(entry);
    return entry;

}

public boolean deleteById(Long id){
    JournalEntry entry = journalEntryRepository.findById(id).orElse(null);

    if(entry != null){
        journalEntryRepository.deleteById(id);
    }
    return true;
}

}
