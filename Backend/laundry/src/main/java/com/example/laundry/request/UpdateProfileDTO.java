package com.example.laundry.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileDTO {
    private String username;
    private String email;
    private String password;
    private String hostel;
}
