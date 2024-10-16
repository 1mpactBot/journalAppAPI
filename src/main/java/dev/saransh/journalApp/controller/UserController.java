package dev.saransh.journalApp.controller;

import dev.saransh.journalApp.api.response.WeatherResponse;
import dev.saransh.journalApp.entity.User;
import dev.saransh.journalApp.repository.UserRepo;
import dev.saransh.journalApp.service.QuotesService;
import dev.saransh.journalApp.service.UserService;
import dev.saransh.journalApp.service.WeatherService;
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
    UserService service;

    @Autowired
    WeatherService weatherService;

    @Autowired
    QuotesService quotesService;

    @Autowired
    private UserRepo userRepo;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            User userInDb = service.findByUserName(username);
            if (userInDb != null) {
                userInDb.setUserName(user.getUserName());
                userInDb.setPassword(user.getPassword());
                service.saveNewUser(userInDb);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            userRepo.deleteByUserName(username);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> greeting() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeather("Kota,Rajasthan");
        String username = authentication.getName();
        String greeting = "";
        if (weatherResponse != null) {
            greeting = "\nWeather feels like " + weatherResponse.getCurrent().getFeelslike();
        }
        String quote = quotesService.getQuote();
        return new ResponseEntity<>("Hi " + username + greeting + "\nHere's a randon Kanye quote for you '" + quote + "'.", HttpStatus.OK);
    }
}