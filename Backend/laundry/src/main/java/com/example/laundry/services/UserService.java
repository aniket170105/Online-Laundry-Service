package com.example.laundry.services;


import com.example.laundry.entities.User;
import com.example.laundry.repository.UserRepository;
import com.example.laundry.request.UpdateProfileDTO;
import com.example.laundry.request.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<User> userProfile(String userId){
        return userRepository.findById(userId);
    }

    public void updateUser(String userId, UpdateProfileDTO updatedUser) {
        Optional<User> existingUserOptional = userRepository.findById(userId);

//        updatedUser.getUsername()
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            if (updatedUser.getUsername() != null) {
                existingUser.setUsername(updatedUser.getUsername());
            }
            if (updatedUser.getEmail() != null) {
                existingUser.setEmail(updatedUser.getEmail());
            }
            if (updatedUser.getPassword() != null) {
                existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }
            if (updatedUser.getHostel() != null) {
                existingUser.setHostel(updatedUser.getHostel());
            }
            userRepository.save(existingUser);
        }
    }

    public Boolean isSignUp(UserDTO userDTO){
        return userRepository.findByEmail(userDTO.getEmail()).isEmpty();
    }

//    public Optional<User> getByEmailAndPassword(String email, String password){
//        System.out.println(passwordEncoder.encode(password));
//        return userRepository.findByEmailAndPassword(email, passwordEncoder.encode(password));
//    }

    public Optional<User> getByEmailAndPassword(String email, String rawPassword) {
        List<User> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isEmpty()) {
            User user = userOptional.get(0);
            if (passwordEncoder.matches(rawPassword, user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public Optional<User> getByUsername(String username){
        return userRepository.findById(username);
    }

    public void saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

}
