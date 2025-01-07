package com.example.laundry.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pants")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Pants {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "laundry_id", referencedColumnName = "id")
    private Laundry laundry;

    // Getters and Setters
}
