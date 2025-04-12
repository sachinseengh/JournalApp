package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.entities.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.service.UserServices;
import net.engineeringdigest.journalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private WeatherService weatherService;

    @PutMapping()
    public ResponseEntity<?> updateUserByUsername(@RequestBody User user){

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User userIndb = userServices.findByUsername(username);
        if(userIndb != null){
            userIndb.setUserName(user.getUserName());
            userIndb.setPassword(user.getPassword());
            userServices.saveUser(userIndb);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping()
    public ResponseEntity<User> deleteUserByUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userServices.deleteUserByUsername(username);
    }

    @GetMapping()
    public ResponseEntity<?> greeting(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        WeatherResponse weather = weatherService.getWeather("Mumbai");

        if(weather !=null){
            String message = weather.getCurrent().getWeather_descriptions().get(0);
            return new ResponseEntity<>("Hi " + authentication.getName() +",  it feels like "+ message,HttpStatus.OK);
        }

        return new ResponseEntity<>("Hi " + authentication.getName(),HttpStatus.OK);
    }
}
