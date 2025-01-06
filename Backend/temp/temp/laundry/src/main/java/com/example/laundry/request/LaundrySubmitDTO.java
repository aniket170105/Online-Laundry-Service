package com.example.laundry.request;


import com.example.laundry.entities.Message;
import com.example.laundry.entities.Pants;
import com.example.laundry.entities.Shirts;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LaundrySubmitDTO {

    public MessageDTO message;
    public List<PantsDTO> pants;
    public List<ShirtsDTO> shirts;

}

