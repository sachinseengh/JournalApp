package net.engineeringdigest.journalApp.repository;

import lombok.NonNull;
import net.engineeringdigest.journalApp.entities.JournalEntry;
import net.engineeringdigest.journalApp.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,Long> {


    public User findByUserName(String username);

    void deleteByUserName(@NonNull String userName);
}
