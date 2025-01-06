package com.example.laundry.services;


import com.example.laundry.entities.Laundry;
import com.example.laundry.entities.LaundryStatus;
import com.example.laundry.entities.User;
import com.example.laundry.repository.LaundryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class LaundryService {
    @Autowired
    LaundryRepository laundryRepository;

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

}
