package com.main_service.dto;

import com.main_service.model.Address;
import com.main_service.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class updateAddressDto {
    private String street;
    private String country;
    private String state;
    private String city;
    private int pincode;
    private String addressType;
    private User user;

    private Address address;

}
