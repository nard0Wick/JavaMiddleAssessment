package com.example.assessment.services;

import com.example.assessment.dtos.MeetingDto;
import com.example.assessment.models.Meeting;
import com.example.assessment.models.User;
import com.example.assessment.repos.MeetingRepo;
import com.example.assessment.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MeetingService {
    @Autowired
    private MeetingRepo meetingRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserService userService;

    public MeetingDto getMeeting(String email, String title){
        User user = userRepo.findByEmail(email).orElseThrow(() ->
                new RuntimeException("Can't find any user related with the email" + email));
        Meeting meeting = user.getMeetingSet().stream().filter(m ->
                        m.getTitle().equals(title)).findFirst()
                .orElseThrow(()->new RuntimeException("The meeting "+title+" isn't related with the current user"));

        return new MeetingDto(meeting.getTitle(), meeting.getDate(), meeting.getResume(), meeting.getGuests());
    }
    public Meeting createMeeting(String email, MeetingDto meetingDto) {
        User user = userRepo.findByEmail(email).orElseThrow(() ->
                new RuntimeException("Can't find any user related with the email" + email));
        Meeting meeting = new Meeting(meetingDto.getTitle(),
                meetingDto.getDate(),
                meetingDto.getResume(),
                meetingDto.getGuests() +","+user.getEmail());

        meetingRepo.save(meeting);

        Set<User> users = checkForNoEquals.apply(getSet.apply(meeting.getGuests()),meeting);
        //adding a meeting for every user in the user set
        users.forEach(u -> u.getMeetingSet().add(meeting));

        userRepo.saveAll(users);

        return meeting; //possibly changed to void
    }


    public Meeting updateMeeting(String email, String title, MeetingDto meetingDto){
        User user = userRepo.findByEmail(email).orElseThrow(() ->
                new RuntimeException("Can't find any user related with the email" + email));
        Meeting meeting = user.getMeetingSet().stream().filter(m ->
                        m.getTitle().equals(title)).findFirst()
                .orElseThrow(()->new RuntimeException("The meeting "+title+" isn't related with the current user"));

        meeting.setTitle(meetingDto.getTitle());
        meeting.setDate(meetingDto.getDate());
        meeting.setResume(meetingDto.getResume());
        meeting.setGuests(meeting.getGuests() + "," +meetingDto.getGuests());

        meetingRepo.save(meeting);

        Set<User> users = checkForNoEquals.apply(getSet.apply(meetingDto.getGuests()),meeting);
        //adding a meeting for every user in the user set
        users.forEach(u -> u.getMeetingSet().add(meeting));

        userRepo.saveAll(users);

        return meeting;
    }

    public void deleteMeeting(String email, String title){
        User user = userRepo.findByEmail(email).orElseThrow(() ->
                new RuntimeException("Can't find any user related with " + email));
        Meeting meeting = user.getMeetingSet().stream().filter(m ->
                m.getTitle().equals(title)).findFirst()
                .orElseThrow(()->new RuntimeException("The meeting "+title+" isn't related with the current user"));

        user.getMeetingSet().remove(meeting);
        userRepo.save(user);
    }

    private final Function<String, Set<User>> getSet = (guests) -> {
        String[] arr = guests.split(",");
        return Arrays.stream(arr).map(e ->
                        userRepo.findByEmail(e).orElseThrow(() ->
                                new RuntimeException("The email " + e + " isn't related with any user")))
                .collect(Collectors.toSet());
    };

    private final BiFunction<Set<User>, Meeting, Set<User>> checkForNoEquals = (users, meeting) ->
            users.stream().filter(u -> u.getMeetingSet().stream().noneMatch(m ->
                    m.equals(meeting))).collect(Collectors.toSet());

}
