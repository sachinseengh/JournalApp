package net.engineeringdigest.journalApp.schedular;


import net.engineeringdigest.journalApp.Schedular.UserEmailSchedular;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailSchedularTest {

    @Autowired
    private UserEmailSchedular userEmailSchedular;

    @Test
    public void testEmail(){
        userEmailSchedular.fetchUserAndSendEmail();
    }
}
