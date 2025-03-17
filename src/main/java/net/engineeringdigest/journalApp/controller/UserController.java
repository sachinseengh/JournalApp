package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.entities.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServices userServices;

    @GetMapping
    public List<User> getAllUsers(){
        return userServices.getAllUser();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        return userServices.saveUser(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        return userServices.getUserById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable Long id,@RequestBody User user){
        return userServices.updateById(id,user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable Long id){
        return userServices.deleteUserById(id);
    }
}
