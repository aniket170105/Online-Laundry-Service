package com.example.laundry.services;


import com.example.laundry.entities.User;
import com.example.laundry.repository.UserRepository;
import com.example.laundry.request.UpdateProfileDTO;
import com.example.laundry.request.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

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
                existingUser.setPassword(updatedUser.getPassword());
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

    public Optional<User> getByEmailAndPassword(String email, String password){
        return userRepository.findByEmailAndPassword(email, password);
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

}
