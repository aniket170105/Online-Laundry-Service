package com.example.laundry.services;


import com.example.laundry.entities.*;
import com.example.laundry.repository.*;
import com.example.laundry.request.LaundrySubmitDTO;
import com.example.laundry.request.PantsDTO;
import com.example.laundry.request.ShirtsDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        return laundryRepository.findByStatusNot(LaundryStatus.DELIVERED);
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
            Message message = new Message();
            message.setMessage(laundrySubmitDTO.getMessage().message);

            message.setLaundry(laundry);
            messageRepository.save(message);
        }

        if (laundrySubmitDTO.getPants() != null && !laundrySubmitDTO.getPants().isEmpty()) {
            for (PantsDTO pants : laundrySubmitDTO.getPants()) {
                Pants res = new Pants();

                byte[] imageBytes = Base64.getDecoder().decode(pants.image);
                res.setImage(imageBytes);
                res.setLaundry(laundry);
                pantsRepository.save(res);
            }
        }

        if (laundrySubmitDTO.getShirts() != null && !laundrySubmitDTO.getShirts().isEmpty()) {
            for (ShirtsDTO shirts : laundrySubmitDTO.getShirts()) {
                Shirts res = new Shirts();

                byte[] imageBytes = Base64.getDecoder().decode(shirts.image);
                res.setImage(imageBytes);
                res.setLaundry(laundry);
                shirtsRepository.save(res);
            }
        }
    }

}
