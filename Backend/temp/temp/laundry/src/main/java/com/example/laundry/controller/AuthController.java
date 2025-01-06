package com.example.laundry.controller;

import com.example.laundry.entities.SessionToken;
import com.example.laundry.entities.User;
import com.example.laundry.request.UserDTO;
import com.example.laundry.services.SessionTokenService;
import com.example.laundry.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.UUID;

@RestController
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    SessionTokenService sessionTokenService;

    @GetMapping("auth")
    public void test(){
        System.out.println("Hello API :)");
    }

    @PostMapping("auth/v1/signup")
    public ResponseEntity<String> SignUp(@RequestBody UserDTO userDTO){
        try{
            Boolean isSignUped = userService.isSignUp(userDTO);
            if(Boolean.FALSE.equals(isSignUped)){
                return new ResponseEntity<>("Already Exist", HttpStatus.BAD_REQUEST);
            }

            User currUser = new User(UUID.randomUUID().toString(), userDTO.getUsername(),
                    userDTO.getEmail(), userDTO.getPassword(), UUID.randomUUID().toString(),
                    userDTO.getHostel(), Boolean.FALSE);
            userService.saveUser(currUser);

            return ResponseEntity.status(HttpStatus.OK).body("Successfully Account Created");
        }catch (Exception ex){
            return new ResponseEntity<>("Exception in User Service", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
