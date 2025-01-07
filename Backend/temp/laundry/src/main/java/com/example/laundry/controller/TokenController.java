package com.example.laundry.controller;

import com.example.laundry.entities.SessionToken;
import com.example.laundry.entities.User;
import com.example.laundry.request.AuthRequestDTO;
import com.example.laundry.request.RefreshTokenRequestDTO;
import com.example.laundry.response.JwtResponseDTO;
import com.example.laundry.services.JwtService;
import com.example.laundry.services.SessionTokenService;
import com.example.laundry.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class TokenController {

    @Autowired
    UserService userService;
    @Autowired
    SessionTokenService sessionTokenService;

    @Autowired
    JwtService jwtService;

    @PostMapping("auth/v1/login")
    public ResponseEntity<JwtResponseDTO> AuthenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO){
        Optional<User> user = userService.getByEmailAndPassword(authRequestDTO.getEmail(), authRequestDTO.getPassword());

        if(user.isPresent()){
            User currUser = user.get();
            SessionToken sessionToken = sessionTokenService.createSessionToken(currUser.getUserId());
            return ResponseEntity.status(HttpStatus.OK).body(new JwtResponseDTO(
                    jwtService.GenerateToken(currUser.getUserId()),
                    sessionToken.getToken()
            ));
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
//            return new ResponseEntity<>(JwtResponseDTO.builder()
//                    .accessToken(jwtService.GenerateToken(authRequestDTO.getUsername()))
//                    .token(refreshToken.getToken())
//                    .build(), HttpStatus.OK);

    }

    @PostMapping("auth/v1/refreshToken")
    public ResponseEntity<JwtResponseDTO> refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){

        Optional<SessionToken> sessionToken = sessionTokenService.findByToken(refreshTokenRequestDTO.getToken());
        if(sessionToken.isPresent()){
            if(sessionTokenService.verifyExpiration(sessionToken.get())){
                return ResponseEntity.status(HttpStatus.OK).body(
                        new JwtResponseDTO(jwtService.GenerateToken(sessionToken.get().getUser().getUserId()),
                                sessionToken.get().getToken())
                );
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
//        return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
//                .map(refreshTokenService::verifyExpiration)
//                .map(RefreshToken::getUserInfo)
//                .map(userInfo -> {
//                    String accessToken = jwtService.GenerateToken(userInfo.getUsername());
//                    return JwtResponseDTO.builder()
//                            .accessToken(accessToken)
//                            .token(refreshTokenRequestDTO.getToken()).build();
//                }).orElseThrow(() ->new RuntimeException("Refresh Token is not in DB..!!"));
    }
}
