package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entities.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface JournalEntryRepository extends MongoRepository<JournalEntry,Long> {

    Optional<JournalEntry> findById(ObjectId id);

    void deleteById(ObjectId id);
}
