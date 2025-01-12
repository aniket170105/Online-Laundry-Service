package com.example.laundry.controller;


import com.example.laundry.entities.Laundry;
import com.example.laundry.entities.User;
import com.example.laundry.request.LaundrySubmitDTO;
import com.example.laundry.request.UpdateProfileDTO;
import com.example.laundry.request.laundry.LaundryDTO;
import com.example.laundry.services.JwtService;
import com.example.laundry.services.LaundryService;
import com.example.laundry.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    JwtService jwtService;
    @Autowired
    UserService userService;
    @Autowired
    LaundryService laundryService;

    @GetMapping("user/v1/profile")
    public ResponseEntity<User> userProfile(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = true) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        }

        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);
        Optional<User> user = userService.userProfile(username);
        if (user.isPresent()) {
            // Convert the user object to a JSON string
//            String userJson = new Gson().toJson(user.get());
            return ResponseEntity.status(HttpStatus.OK).body(user.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }


    @PostMapping("user/v1/update")
    public ResponseEntity<String> updateProfile(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = true) String authHeader,
            @RequestBody UpdateProfileDTO updateProfileDTO
            ){
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Missing or invalid Authorization header");
        }
        String token = authHeader.substring(7);
        String userId = jwtService.extractUsername(token);

        try{
            userService.updateUser(userId, updateProfileDTO);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("User Data Succesfully Updated");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error while Updating User");
        }
    }

    @GetMapping("user/v1/laundry")
    public ResponseEntity<List<LaundryDTO>> getAllLaundry(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = true) String authHeader
    ){
        String token = authHeader.substring(7);
        String userId = jwtService.extractUsername(token);

        List<Laundry> result = laundryService.getLaundryForUser(userService.userProfile(userId).get());

        List <LaundryDTO> answer = new ArrayList<>();
        for(Laundry laundry : result){
            answer.add(new LaundryDTO(laundry));
        }

        return ResponseEntity.status(HttpStatus.OK).body(answer);
    }

    @PostMapping("user/v1/submit")
    public ResponseEntity<String> submitLaundry(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = true) String authHeader,
            @RequestBody LaundrySubmitDTO laundrySubmitDTO
            ){
        String token = authHeader.substring(7);
        String userId = jwtService.extractUsername(token);
        try{
            laundryService.submitLaundry(userId, laundrySubmitDTO);
            return ResponseEntity.status(HttpStatus.OK).body("Succesfully Submitted Laundry");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error while Submitting");
        }
    }

}
