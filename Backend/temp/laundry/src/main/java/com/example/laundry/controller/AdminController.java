package com.example.laundry.controller;


import com.example.laundry.entities.Laundry;
import com.example.laundry.entities.LaundryStatus;
import com.example.laundry.request.LaundryStatusChangeDTO;
import com.example.laundry.services.JwtService;
import com.example.laundry.services.LaundryService;
import com.example.laundry.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {

    @Autowired
    LaundryService laundryService;

    @Autowired
    JwtService jwtService;

    @Autowired
    UserService userService;

    @GetMapping("admin/v1/laundry")
    public ResponseEntity<List<Laundry>> getAllLaundry(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = true) String authHeader
    ){
        String token = authHeader.substring(7);
        String userId = jwtService.extractUsername(token);

        if(userService.userProfile(userId).get().getIsAdmin().equals(Boolean.FALSE)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        List<Laundry> result = laundryService.getUndeliveredLaundry();

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("admin/v1/changeStatus")
    public ResponseEntity<String> changeStatus(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = true) String authHeader,
            @RequestBody LaundryStatusChangeDTO laundryStatusChangeDTO
            ){
        String token = authHeader.substring(7);
        String userId = jwtService.extractUsername(token);

        if(userService.userProfile(userId).get().getIsAdmin().equals(Boolean.FALSE)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not Admin user");
        }

        try{
            laundryService.changeStatusOfALaundry(laundryService.getLaundryById(laundryStatusChangeDTO.getId()).get(),
                    LaundryStatus.valueOf(laundryStatusChangeDTO.getStatus()));
            return ResponseEntity.status(HttpStatus.OK).body("Successfully changed Status");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Server Error");
        }

    }
}
