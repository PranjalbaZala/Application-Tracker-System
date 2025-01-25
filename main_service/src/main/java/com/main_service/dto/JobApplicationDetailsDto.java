package com.main_service.dto;

import com.main_service.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobApplicationDetailsDto {

    private String stream;
    private String designation;
    private int totalExpYears;
    private int relevantExpYears;
    private Boolean isFresher;
    private Long oldCtc;
    private Long expectedCtc;
    private int noticePeriod;
    private String roleAppliedFor;
    private boolean willingToRelocate;
    private String jobDescription;
    private boolean anyCurrentOffer;

    private boolean upcomingInterview;
    private String reasonForChange;
    private String referralSource;
    private boolean acceptOffer;
    private User user;
}
