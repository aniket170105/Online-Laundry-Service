package com.example.laundry.request;


import com.example.laundry.entities.Message;
import com.example.laundry.entities.Pants;
import com.example.laundry.entities.Shirts;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LaundrySubmitDTO {

    private Message message;
    private List<Pants> pants;
    private List<Shirts> shirts;

}
