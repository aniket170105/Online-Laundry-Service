package com.example.laundry.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "laundry")
@Getter
@Setter
public class Laundry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private LaundryStatus status;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @OneToMany(mappedBy = "laundry", cascade = CascadeType.ALL)
    private List<Message> messages;

    @OneToMany(mappedBy = "laundry", cascade = CascadeType.ALL)
    private List<Pants> pants;

    @OneToMany(mappedBy = "laundry", cascade = CascadeType.ALL)
    private List<Shirts> shirts;

    // Getters and Setters
}

