package com.example.laundry.repository;


import com.example.laundry.entities.Laundry;
import com.example.laundry.entities.LaundryStatus;
import com.example.laundry.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaundryRepository extends JpaRepository<Laundry, Integer> {
    List<Laundry> findByUser(User user);

    List<Laundry> findByStatusNot(LaundryStatus status);

}
