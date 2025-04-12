package net.engineeringdigest.journalApp.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;


    @Test
    public void testSendingMail(){
        emailService.sendMail("curiousjunker@gmail.com","java code","Java code bata message");
        emailService.sendMail("helloashishthapa@gmail.com","java code","Java code bata message");
    }
}
