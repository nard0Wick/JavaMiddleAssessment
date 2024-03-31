package com.example.assessment.controllers;

import com.example.assessment.dtos.UserDto;
import com.example.assessment.models.User;
import com.example.assessment.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user/")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    public ResponseEntity<Object> getAll(){
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<Object> getUserMeetings(@RequestParam String email){
        return new ResponseEntity<>(userService.getUser(email), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> postUser(@RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> putUser(@RequestParam String email, @RequestBody UserDto userDto){
        return new ResponseEntity<>(userService.updateUser(email, userDto), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteUser(@RequestParam String email){
        userService.deleteUser(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/checkPaiload")
    public ResponseEntity<Object> check(@RequestBody User user){
        return new ResponseEntity<>(HttpStatus.OK);
    }
 }
