package net.engineeringdigest.journalApp.service;


import net.engineeringdigest.journalApp.entities.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public User findByUsername(String username){
       return userRepository.findByUserName(username);
    }

    public ResponseEntity<User> saveUser(@RequestBody User user){
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
    }

    public ResponseEntity<User> getUserById(@PathVariable Long id){
        User user = userRepository.findById(id).orElse(null);
        if(user !=null){
            return new ResponseEntity<>(user,HttpStatus.OK);
        }
        return new ResponseEntity<>(user,HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<User> deleteUserById(@PathVariable Long id){
        User user = userRepository.findById(id).orElse(null);
        if(user !=null){
            userRepository.deleteById(id);
            return new ResponseEntity<>(user,HttpStatus.OK);
        }
        return new ResponseEntity<>(user,HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<User> updateById(@PathVariable Long id,@RequestBody User user){
        User old = userRepository.findById(id).orElse(null);

        if(user !=null){
            old.setUserName(user.getUserName()!=null && !user.getUserName().equals("") ? user.getUserName(): old.getUserName());
            old.setPassword(user.getPassword()!=null && !user.getPassword().equals("") ? user.getPassword(): old.getPassword());
            userRepository.save(old);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(user,HttpStatus.NOT_FOUND);
    }
}
