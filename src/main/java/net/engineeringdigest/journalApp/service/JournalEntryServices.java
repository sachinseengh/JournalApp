package net.engineeringdigest.journalApp.service;


import net.engineeringdigest.journalApp.entities.JournalEntry;
import net.engineeringdigest.journalApp.entities.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JournalEntryServices {

@Autowired
private JournalEntryRepository journalEntryRepository;

@Autowired
private UserServices userServices;

@Autowired
private PasswordEncoder passwordEncoder;

@Transactional
public ResponseEntity saveEntity(JournalEntry entry, String username){

    try {
        User user = userServices.findByUsername(username);
        entry.setDate(LocalDateTime.now());
        JournalEntry saved= journalEntryRepository.save(entry);
        user.getEntries().add(saved);
//        user.setUserName(null);
        //This method again encode the password so we have to decode because currently it has the encrypted one and if i send this it will encrypt it again so decrypt it and send for encryption again in the userservice save method

        userServices.updateUser(user);
        return new ResponseEntity(saved, HttpStatus.CREATED);

    }catch(Exception e){
        System.out.println(e);
        throw new RuntimeException("Username is null");
    }

}


public JournalEntry findById(ObjectId id){

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User user = userServices.findByUsername(authentication.getName());
    List<JournalEntry> collect = user.getEntries().stream().filter(x->x.getId().equals(id)).collect(Collectors.toList());

    if(!collect.isEmpty()){
        Optional<JournalEntry> journalEntries = journalEntryRepository.findById(id);
        if(journalEntries.isPresent()){
            return  journalEntries.get();
        }
    }
    return null;
}

public Optional<JournalEntry> updateById(ObjectId id,JournalEntry newEntry) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User user = userServices.findByUsername(authentication.getName());
    List<JournalEntry> collect = user.getEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());

    Optional<JournalEntry> entry = null;
    if (!collect.isEmpty()) {

        entry = Optional.ofNullable(findById(id));

        if (!(entry==null)) {
            JournalEntry old = entry.get();
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());

            old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());

            journalEntryRepository.save(old);
        }
    }

    return entry;
}

public boolean deleteById(ObjectId id){
    JournalEntry entry = journalEntryRepository.findById(id).orElse(null);

    if(entry != null){
        journalEntryRepository.deleteById(id);
    }
    return true;
}

}
