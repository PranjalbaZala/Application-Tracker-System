package com.main_service.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
public class UserDto implements Serializable {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private Long phoneNumber;
    private String stream;
    private String roleAppliedFor;


    public UserDto(Long id, String firstname, String lastname, String email, Long phoneNumber, String stream, String roleAppliedFor) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.stream = stream;
        this.roleAppliedFor = roleAppliedFor;
    }

    public UserDto(Long id,String firstname, String email, Long phoneNumber) {
        this.id = id;
        this.firstname = firstname;
        this.email = email;
        this.phoneNumber = phoneNumber;

    }
}
