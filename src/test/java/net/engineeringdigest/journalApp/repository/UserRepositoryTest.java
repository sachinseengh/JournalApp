package net.engineeringdigest.journalApp.repository;


import net.engineeringdigest.journalApp.service.UserRepositoryImpl;
import org.bson.assertions.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Test
    public void check(){
        userRepositoryImpl.searchForSA();

    }
}
