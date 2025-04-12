package net.engineeringdigest.journalApp.service;


import net.engineeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.ParameterizedType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;


    @Disabled
    @Test
    public void findByUserNameTest(){

        assertNotNull(userRepository.findByUserName("not sachin"));
    }


    @ParameterizedTest
    @CsvSource({
            "1,2,3",
            "5,2,3"
    })

    public void test(int a,int b,int expected){
        assertEquals(expected,a+b);
    }
}
