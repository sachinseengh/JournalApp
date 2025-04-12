package net.engineeringdigest.journalApp.service;


import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entities.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServices {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;



//    private static final Logger logger = LoggerFactory.getLogger(UserServices.class);
    //    or just by using annotation

    public User findByUsername(String username){
       return userRepository.findByUserName(username);
    }

    public ResponseEntity<User> saveUser(@RequestBody User user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
        }catch(Exception e){
            System.out.println(e);
//            logger.info("Error occured");
//            annotation le log
            log.error("error aayo hai for {}",user.getUserName());
            log.debug("error aayo hai for {}",user.getUserName());
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<User> updateUser(@RequestBody User user){
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
    }


    public ResponseEntity<User> deleteUserByUsername(String username){

        User user = userRepository.findByUserName(username);
        if(user != null){
            userRepository.deleteByUserName(user.getUserName());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
}
