package net.engineeringdigest.journalApp.service;


import ch.qos.logback.classic.joran.JoranConfigurator;
import net.engineeringdigest.journalApp.entities.JournalEntry;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryServices {

@Autowired
private JournalEntryRepository journalEntryRepository;


public JournalEntry saveEntity(JournalEntry entry){
    entry.setDate(LocalDateTime.now());
    return journalEntryRepository.save(entry);
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
