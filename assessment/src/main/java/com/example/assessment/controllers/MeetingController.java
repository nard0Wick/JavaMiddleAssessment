package com.example.assessment.controllers;

import com.example.assessment.dtos.MeetingDto;
import com.example.assessment.dtos.UserDto;
import com.example.assessment.services.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("meetings/")
public class MeetingController {
    @Autowired
    private MeetingService meetingService;

    @GetMapping("/")
    public ResponseEntity<Object> getMeeting(@RequestParam String email, @RequestParam String title){
        return new ResponseEntity<>(meetingService.getMeeting(email, title), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> postMeeting(@RequestParam String email, @RequestBody MeetingDto meetingDto ){
        return new ResponseEntity<>(meetingService.createMeeting(email, meetingDto), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> putMeeting(@RequestParam String email, @RequestParam String title, @RequestBody MeetingDto meetingDto){
        return new ResponseEntity<>(meetingService.updateMeeting(email, title, meetingDto), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteMeeting(@RequestParam String email, @RequestParam String title){
        meetingService.deleteMeeting(email, title);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
