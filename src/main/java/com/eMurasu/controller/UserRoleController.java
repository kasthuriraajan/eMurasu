package com.eMurasu.controller;

import com.eMurasu.Objects.ReturnMessage;
import com.eMurasu.enums.BasicDataState;
import com.eMurasu.model.UserRole;
import com.eMurasu.repo.UserRoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRoleController {
    @Autowired
    private UserRoleRepo userRoleRepo;
    @PostMapping(path = "/addUserRole", produces = "application/json")
    public ReturnMessage addUserRole(@RequestBody UserRole userRole){
        try {
            userRoleRepo.save(userRole);
            return new ReturnMessage("You have added a User Role successfully!");
        }
        catch (Exception e){
            return new ReturnMessage(e.getMessage());
        }
    }
    @PostMapping(path = "/deactivateUserRole", produces = "application/json")
    public ReturnMessage deactivateUserRole(@RequestParam int userRoleId){
        try {
            UserRole userRole = userRoleRepo.findById(userRoleId);
            userRole.setStatus(BasicDataState.OBSOLETE);
            userRoleRepo.save(userRole);
            return new ReturnMessage("You have deactivated the UserRole - "+userRole.getTitle() +" successfully!");
        }
        catch (Exception e){
            return new ReturnMessage(e.getMessage());
        }
    }

    @GetMapping(path = "/userRoles",produces = "application/json")
    public Iterable<UserRole> userRoles (){
        return userRoleRepo.findAll();
    }


    @GetMapping(path = "/getUserRole",produces = "application/json")
    public UserRole getUserRole(@RequestParam(value = "userRoleId") int userRoleId){
        return userRoleRepo.findById(userRoleId);
    }
}
