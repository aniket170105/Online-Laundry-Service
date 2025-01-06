package com.example.laundry.services;


import com.example.laundry.entities.*;
import com.example.laundry.repository.*;
import com.example.laundry.request.LaundrySubmitDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class LaundryService {
    @Autowired
    LaundryRepository laundryRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    PantsRepository pantsRepository;
    @Autowired
    ShirtsRepository shirtsRepository;

    public Optional<Laundry> getLaundryById(Integer id){
        return laundryRepository.findById(id);
    }

    public List<Laundry> getLaundryForUser(User user){
        List<Laundry> result = laundryRepository.findByUser(user);
        result.sort(Comparator.comparing(Laundry::getDate).reversed());
        return result;
    }

    public List<Laundry> getUndeliveredLaundry(){
        return laundryRepository.findByStatusNot(LaundryStatus.DELEVIERED);
    }

    public void changeStatusOfALaundry(Laundry laundry, LaundryStatus status){
        laundry.setStatus(status);
        laundryRepository.save(laundry);
    }

    @Transactional
    public void submitLaundry(String userId, LaundrySubmitDTO laundrySubmitDTO){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        Laundry laundry = new Laundry();
        laundry.setUser(user);
        laundry.setStatus(LaundryStatus.PENDING);
        laundry.setDate(LocalDateTime.now());

        laundry = laundryRepository.save(laundry);

        if (laundrySubmitDTO.getMessage() != null) {
            Message message = laundrySubmitDTO.getMessage();
            message.setLaundry(laundry);
            messageRepository.save(message);
        }

        if (laundrySubmitDTO.getPants() != null && !laundrySubmitDTO.getPants().isEmpty()) {
            for (Pants pants : laundrySubmitDTO.getPants()) {
                pants.setLaundry(laundry);
                pantsRepository.save(pants);
            }
        }

        if (laundrySubmitDTO.getShirts() != null && !laundrySubmitDTO.getShirts().isEmpty()) {
            for (Shirts shirts : laundrySubmitDTO.getShirts()) {
                shirts.setLaundry(laundry);
                shirtsRepository.save(shirts);
            }
        }
    }

}
