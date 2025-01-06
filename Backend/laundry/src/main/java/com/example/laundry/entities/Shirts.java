package com.example.laundry.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "shirts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Shirts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "image")
    @Lob
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "laundry_id", referencedColumnName = "id")
    private Laundry laundry;

    // Getters and Setters
}

