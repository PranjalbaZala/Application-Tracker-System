package com.main_service.dto;

import com.main_service.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto implements Serializable {
//    private int addressId;
    private String street;
    private String country;
    private String state;
    private String city;
    private int pincode;
    private String addressType;
    private User user;

}
