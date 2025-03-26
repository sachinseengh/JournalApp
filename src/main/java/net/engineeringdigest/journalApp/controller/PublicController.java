package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.entities.User;
import net.engineeringdigest.journalApp.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserServices userServices;

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user){
        return userServices.saveUser(user);
    }



}
