package com.main_service.dto;

import com.main_service.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrackingDto implements Serializable {
    private  Integer id;
    private User recruiter;
    private User user;
}
