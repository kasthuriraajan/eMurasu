package com.eMurasu.controller;

import com.eMurasu.Objects.ReturnMessage;
import com.eMurasu.Objects.UserDetails;
import com.eMurasu.enums.UserState;
import com.eMurasu.model.User;
import com.eMurasu.model.UserCredential;
import com.eMurasu.repo.UserCredentialRepo;
import com.eMurasu.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Date;

@RestController
public class UserController {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserCredentialRepo userCredentialRepo;

    @PostMapping (path = "/addUser", produces = "application/json")
    public ReturnMessage addUser(@RequestBody UserDetails userDetails){
        try {
            User user = new User();
            Date currentDate = new Date();
            user.setUsername(userDetails.getUsername());
            user.setFirstName(userDetails.getFirstName());
            user.setLastName(userDetails.getLastName());
            user.setEmail(userDetails.getEmail());
            user.setDateOfBirth(userDetails.getDateOfBirth());
            user.setAddress(userDetails.getAddress());
            user.setContactNo(userDetails.getContactNo());
            user.setJoinedAt(userDetails.getJoinedAt());
            user.setCreatedAt(new Timestamp(currentDate.getTime()));
            user.setAddedBy(0);
            user.setLastUpdated(new Timestamp(currentDate.getTime()));


            UserCredential userCredential = new UserCredential();
            userCredential.setUsername(userDetails.getUsername());
            userCredential.setPassword(new BCryptPasswordEncoder().encode("SamplePassword"));
            userCredential.setRoleId(userDetails.getUserRoleId());
            userCredential.setStatus(UserState.NEW);

            userRepo.save(user);
            userCredentialRepo.save(userCredential);
            return new ReturnMessage("You have added user - "+userDetails.getUsername()+ "-" + "successfully");
        }
        catch (Exception e){
            return new ReturnMessage(e.getMessage());
        }
    }

    @GetMapping(path = "/showUserCredential")
    @PreAuthorize("hasAuthority('Admin') or hasAuthority('Editor')")
    public @ResponseBody
    Iterable<UserCredential> showUserCredential()
    {
        return userCredentialRepo.findAll();
    }
    @GetMapping(path = "/Users")
    public @ResponseBody
    Iterable<User> showUsers()
    {
        return userRepo.findAll();
    }

    @GetMapping(path = "/getUser",produces = "application/json")
    public User getUser(@RequestParam(value = "username") String username){
        return userRepo.findByUsername(username);
    }

    @GetMapping(path = "/getUserCredential",produces = "application/json")
    public UserCredential getUserCredential(@RequestParam(value = "username") String username){
        return userCredentialRepo.findByUsername(username);
    }

}
