package com.example.assessment.services;

import com.example.assessment.dtos.UserDto;
import com.example.assessment.models.User;
import com.example.assessment.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public List<User> getAll(){
        return userRepo.findAll();
    }

    public UserDto getUser(String email){
        User user = userRepo.findByEmail(email).orElseThrow(()->
                new RuntimeException("The email "+ email +" isn't related with any user"));

        return new UserDto(user.getName(), user.getLastName(), user.getEmail(), user.getPassword());
    }
    public User createUser(UserDto userDto){
        if(userRepo.existsByEmail(userDto.getEmail()))
        {throw new RuntimeException("The email "+ userDto.getEmail()+" is already in use");}

        return userRepo.save(new User(
                userDto.getName(),
                userDto.getLastName(),
                userDto.getEmail(),
                userDto.getPassword()
        ));
    }
    public User updateUser(String email, UserDto userDto){
        User user = userRepo.findByEmail(email).orElseThrow(()->
                new RuntimeException("The email "+ userDto.getEmail()+" isn't related with any user"));

        user.setName(userDto.getName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());

        return userRepo.save(user);

    }

    public void deleteUser(String email){
        User user = userRepo.findByEmail(email).orElseThrow(()->
                new RuntimeException("The email "+email+" isn't related with any user"));

        userRepo.delete(user);
    }

}
